package com.senosy.newsapp.ui.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.senosy.newsapp.R
import com.senosy.newsapp.data.models.ArticleModel
import com.senosy.newsapp.data.remote.ApiClient
import com.senosy.newsapp.data.repository.ArticleRepositoryImpl
import com.senosy.newsapp.databinding.HomeFragmentBinding
import com.senosy.newsapp.ui.adapter.ArticleAdapter
import com.senosy.newsapp.ui.interfaces.OnRecyclerItemClickListener
import com.senosy.newsapp.utils.FAVOURITE_COUNTRY
import com.senosy.newsapp.utils.NetworkModule
import com.senosy.newsapp.utils.PreferenceHelper
import com.senosy.newsapp.utils.Utilities
import com.senosy.newsapp.utils.providers.ResourceProvider

class HomeFragment : Fragment() ,OnRecyclerItemClickListener{

    companion object {
        fun newInstance() = HomeFragment()
    }

    private lateinit var binding: HomeFragmentBinding
    private val viewModel: HomeViewModel by viewModels<HomeViewModel>{
        val gson = NetworkModule.providesJson()
        val retrofit = NetworkModule.providesRetrofit(gson)
        val apiClient = NetworkModule.providesApiClient(retrofit)
        val repo = NetworkModule.provideRemoteRepository(apiClient)
        HomeViewModelProvider(ResourceProvider(requireContext()),repo)
    }
    private lateinit var adapter: ArticleAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = HomeFragmentBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        initializeView()
        setListener()
        return binding.root
    }

    private fun initializeView() {
        binding.viewModel?.oncreate()
        binding.country = PreferenceHelper.getPrefs(requireContext()).getString(FAVOURITE_COUNTRY,"EG")
        binding.viewModel?.getArticles(binding.country ?: "")
        Utilities.setSwipeRefreshLayoutColor(
            requireActivity(),
            binding.swipeRefreshHomeFragment
        )
        (requireActivity()).title = getString(R.string.article)

        adapter = ArticleAdapter()
        adapter.addListener(this)
        binding.recyclerView.adapter = adapter
    }

    private fun setListener() {
        binding.viewModel?.loading?.observe(viewLifecycleOwner, {
            if (it!!) binding.shimmerFrameLayout.startShimmerAnimation()
            else binding.shimmerFrameLayout.stopShimmerAnimation()
        })
        binding.viewModel?.popularArticles?.observe(viewLifecycleOwner, {
            adapter.addData(it);
        })

    }

    private fun openArticleDetails(articleData: ArticleModel) {
//        val action = HomeFragmentDirections.actionHomeFragmentToArticleDetailsFragment(articleData)
//        findNavController().navigate(action)

    }

    override fun onRecyclerItemClickListener(articleData: ArticleModel) {

    }
}