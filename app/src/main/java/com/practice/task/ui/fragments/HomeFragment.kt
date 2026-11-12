package com.practice.task.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.practice.task.R
import com.practice.task.adapters.PopularResturantsAdapter
import com.practice.task.adapters.ResturantsAdapter
import com.practice.task.databinding.FragmentHomeBinding
import com.practice.task.models.RestaurantsResponse
import com.practice.task.models.UiState
import com.practice.task.sharedprefs.SharedPrefManager
import com.practice.task.viewmodels.HomeViewModel
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.viewmodel.ext.android.viewModel


class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private val homeViewModel by viewModel<HomeViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeViewModel.getData("Bearer ${SharedPrefManager.getToken(requireContext())}")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        initView()

        lifecycleScope.launchWhenCreated {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                homeViewModel.restaurants.collectLatest {
                    when (it) {
                        is UiState.Data->{
                            binding.progressLoading.visibility = View.GONE
                            setData(it.data as RestaurantsResponse)
                        }
                        is UiState.Error->{
                            binding.progressLoading.visibility = View.GONE
                            it.error?.let { it1 -> Snackbar.make(binding.root, it1,Snackbar.LENGTH_SHORT).show() }
                        }
                        else -> {
                            binding.progressLoading.visibility = View.VISIBLE
                        }
                    }
                }
            }
        }

        return binding.root
    }

    private fun initView() {
        val items =
            listOf("Delray Beach, FL", "Delray Beach, FL", "Delray Beach, FL", "Delray Beach, FL")
        val adapter = ArrayAdapter(requireContext(), R.layout.item_option, items)
        (binding.textFieldOptions.editText as? AutoCompleteTextView)?.setAdapter(adapter)

        val user = SharedPrefManager.getUser(requireContext())
        binding.textViewUserGreeting.text = "Hello ${user.name}"
        Glide.with(requireActivity()).load(user.photo).into(binding.imageViewUserProfile)
    }

    private fun setData(data: RestaurantsResponse)
    {
        binding.recyclerViewResturants.adapter = ResturantsAdapter(data.restaurants,requireActivity())
        binding.recyclerViewPopularResturants.adapter = PopularResturantsAdapter(data.popular_restaurants,requireActivity())
    }
}