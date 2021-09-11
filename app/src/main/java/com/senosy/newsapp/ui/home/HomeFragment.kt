package com.senosy.newsapp.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.senosy.newsapp.R
import com.senosy.newsapp.data.models.ArticleModel
import com.senosy.newsapp.databinding.HomeFragmentBinding
import com.senosy.newsapp.ui.adapter.ArticleAdapter
import com.senosy.newsapp.ui.interfaces.OnRecyclerItemClickListener
import com.senosy.newsapp.utils.*
import com.senosy.newsapp.utils.providers.ResourceProvider
import java.lang.reflect.Type


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
    private var categories = mutableListOf<String>()
    private lateinit var adapter: ArticleAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = HomeFragmentBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        getDataFromPrefs()
        initializeView()
        setListener()
        return binding.root
    }

    private fun getDataFromPrefs(){
        val catString = PreferenceHelper.getPrefs(requireActivity().applicationContext).getString(
            FAVOURITE_TAGS,"")
        val listType: Type = object : TypeToken<List<String?>?>() {}.type

        categories = Gson().fromJson(catString,listType)
    }

    private fun initializeView() {
        binding.viewModel?.oncreate()
        binding.country = PreferenceHelper.getPrefs(requireContext()).getString(FAVOURITE_COUNTRY,"EG")
        binding.spinCategories.adapter = ArrayAdapter(requireContext(),android.R.layout.simple_dropdown_item_1line,categories)
        binding.viewModel?.getArticles(binding.country ?: "",categories[0])
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

        binding.spinCategories.onItemSelectedListener = object: OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                binding.viewModel?.getArticles(binding.country ?: "",categories[p2])
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                binding.viewModel?.getArticles(binding.country ?: "",categories[0])
            }

        }
    }

    private fun openArticleDetails(articleData: ArticleModel) {
//        val action = HomeFragmentDirections.actionHomeFragmentToArticleDetailsFragment(articleData)
//        findNavController().navigate(action)

    }

    override fun onRecyclerItemClickListener(articleData: ArticleModel) {

    }
}