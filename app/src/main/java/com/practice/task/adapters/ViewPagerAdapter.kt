package com.practice.task.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.practice.task.ui.fragments.OnBoardingFragment1
import com.practice.task.ui.fragments.OnBoardingFragment2
import com.practice.task.ui.fragments.OnBoardingFragment3

class ViewPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    private val list = listOf(OnBoardingFragment1(),OnBoardingFragment2(),OnBoardingFragment3())
    override fun getItemCount(): Int {
        return list.size
    }

    override fun createFragment(position: Int): Fragment {
        return list[position]
    }

}