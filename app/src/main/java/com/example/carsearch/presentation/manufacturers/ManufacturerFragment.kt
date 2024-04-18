package com.example.carsearch.presentation.manufacturers

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.carsearch.R
import com.example.carsearch.databinding.FragmentManufacturerBinding
import com.example.carsearch.domain.core.model.main.Manufacturer
import com.example.carsearch.presentation.manufacturers.adapter.ManufacturerListAdapter
import com.example.carsearch.presentation.manufacturers.state.ManufacturersListUiState
import com.example.carsearch.presentation.viewmodel.SharedViewModel
import com.example.carsearch.utils.onHide
import com.example.carsearch.utils.onShow
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ManufacturersFragment : Fragment(R.layout.fragment_manufacturer) {

    private var _binding: FragmentManufacturerBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ManufacturerViewModel by viewModels()
    private val sharedViewModel: SharedViewModel by activityViewModels()

    private val adapter = ManufacturerListAdapter {
        navigateToManufacturerDetails(it)
    }
    private var isLoading = false
    private val scrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            val layoutManager = recyclerView.layoutManager as LinearLayoutManager
            val totalItemCount = layoutManager.itemCount
            val lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()

            if (!isLoading && lastVisibleItemPosition == totalItemCount - 1) {
                isLoading = true
                viewModel.loadManufacturers()
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentManufacturerBinding.bind(view)

        setupRecyclerView()
        observeViewModelState()
    }

    private fun setupRecyclerView() {
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = this@ManufacturersFragment.adapter
            addOnScrollListener(scrollListener)
        }
    }

    private fun observeViewModelState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.uiState.flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                .collect { state ->
                    when (state) {
                        is ManufacturersListUiState.ErrorOccurred -> {
                            binding.errorViewGroup.onShow()
                            binding.loadingViewGroup.onHide()
                            binding.successfullContentViewGroup.onHide()
                        }

                        is ManufacturersListUiState.Loading -> {
                            binding.errorViewGroup.onHide()
                            binding.loadingViewGroup.onShow()
                            binding.successfullContentViewGroup.onHide()
                        }

                        is ManufacturersListUiState.ListSuccessfullyFetched -> {
                            binding.errorViewGroup.onHide()
                            binding.loadingViewGroup.onHide()
                            binding.successfullContentViewGroup.onShow()
                            adapter.submitList(state.list)
                        }
                    }
                }
        }
    }

    private fun navigateToManufacturerDetails(manufacturer: Manufacturer) {
        val action = ManufacturersFragmentDirections
            .actionCarManufacturersFragmentToModelFragment()
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}