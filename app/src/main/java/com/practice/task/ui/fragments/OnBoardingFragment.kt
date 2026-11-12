package com.practice.task.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.practice.task.R
import com.practice.task.adapters.ViewPagerAdapter
import com.practice.task.databinding.FragmentOnBoardingBinding
import com.practice.task.sharedprefs.SharedPrefManager


class OnBoardingFragment : Fragment() {
    private lateinit var binding : FragmentOnBoardingBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOnBoardingBinding.inflate(inflater, container, false)

        if (SharedPrefManager.getToken(requireContext())!="token"){
            findNavController().navigate(R.id.action_onBoardingFragment_to_homeFragment)
        }

        binding.viewPagerOnBoarding.adapter = ViewPagerAdapter(this)

        binding.dotsIndicator.attachTo(binding.viewPagerOnBoarding)

        binding.buttonOnBoardingNext.setOnClickListener {
            binding.viewPagerOnBoarding.currentItem = binding.viewPagerOnBoarding.currentItem+1
            if (binding.viewPagerOnBoarding.currentItem == 2)
            {
                binding.buttonOnBoardingNext.visibility = View.INVISIBLE
                binding.dotsIndicator.visibility = View.INVISIBLE
                binding.buttonOnBoardingStart.visibility = View.VISIBLE
            }
        }

        binding.buttonOnBoardingStart.setOnClickListener {
            findNavController().navigate(R.id.action_onBoardingFragment_to_signUpFragment)
        }


        binding.viewPagerOnBoarding.registerOnPageChangeCallback(object:OnPageChangeCallback(){
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                if (position == 2)
                {
                    binding.buttonOnBoardingNext.visibility = View.INVISIBLE
                    binding.dotsIndicator.visibility = View.INVISIBLE
                    binding.buttonOnBoardingStart.visibility = View.VISIBLE
                }
                if (position<2)
                {
                    binding.buttonOnBoardingNext.visibility = View.VISIBLE
                    binding.dotsIndicator.visibility = View.VISIBLE
                    binding.buttonOnBoardingStart.visibility = View.INVISIBLE
                }
            }
        })

        return binding.root
    }
}