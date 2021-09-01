package com.senosy.newsapp.ui.obBoarding.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerAdapter(
    private val list: ArrayList<Fragment>,
    private val fm: FragmentManager,
    private val lifeCycle: Lifecycle
) :
    FragmentStateAdapter(fm, lifeCycle) {
    override fun getItemCount(): Int = list.size

    override fun createFragment(position: Int): Fragment {
        return list[position]
    }
}