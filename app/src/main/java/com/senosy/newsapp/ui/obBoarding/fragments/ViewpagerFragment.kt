package com.senosy.newsapp.ui.obBoarding.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.senosy.newsapp.R
import com.senosy.newsapp.databinding.FragmentViewpagerBinding
import com.senosy.newsapp.ui.obBoarding.adapter.ViewPagerAdapter
import com.senosy.newsapp.ui.obBoarding.fragments.ChooseCountryFragment
import com.senosy.newsapp.ui.obBoarding.fragments.ChooseTagFragment


class ViewpagerFragment : Fragment() {
    private lateinit var binding: FragmentViewpagerBinding
    private val fragments = arrayListOf(
        ChooseCountryFragment(),
        ChooseTagFragment()
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentViewpagerBinding.inflate(inflater, container, false)
        val adapter = ViewPagerAdapter(fragments, requireActivity().supportFragmentManager, lifecycle)
        binding.vpOnboarding.adapter = adapter
        binding.vpOnboarding.isUserInputEnabled = false
        return binding.root
    }

}