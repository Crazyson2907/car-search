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
        viewModel.selectAuto(it)
        navigateToManufacturerDetails(it)
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
            addOnScrollListener(createScrollListener())
        }
    }

    private fun createScrollListener() = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            val layoutManager = recyclerView.layoutManager as LinearLayoutManager
            val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
            val lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()
            val totalItemCount = layoutManager.itemCount

            if (firstVisibleItemPosition == 0) {
                // Logic to load previous items
//                viewModel.loadPreviousManufacturers()
            }
            if (lastVisibleItemPosition == totalItemCount - 1) {
                viewModel.loadManufacturers()
            }
        }
    }

    private fun observeViewModelState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.uiState.collect { state ->
                handleState(state)
            }
        }
    }

    private fun handleState(state: ManufacturersListUiState) {
        when (state) {
            is ManufacturersListUiState.ErrorOccurred -> showError()
            is ManufacturersListUiState.Loading -> showLoading()
            is ManufacturersListUiState.ListSuccessfullyFetched -> showContent(state.list)
        }
    }

    private fun showLoading() {
        binding.loadingViewGroup.onShow()
        binding.errorViewGroup.onHide()
        binding.successfullContentViewGroup.onHide()
    }

    private fun showError() {
        binding.errorViewGroup.onShow()
        binding.loadingViewGroup.onHide()
        binding.successfullContentViewGroup.onHide()
    }

    private fun showContent(list: List<Manufacturer>) {
        binding.successfullContentViewGroup.onShow()
        binding.loadingViewGroup.onHide()
        binding.errorViewGroup.onHide()
        adapter.submitList(list.toList())
    }

    private fun navigateToManufacturerDetails(manufacturer: Manufacturer) {
        val action = ManufacturersFragmentDirections
            .actionCarManufacturersFragmentToModelFragment()
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.recyclerView.removeOnScrollListener(createScrollListener())
        _binding = null
    }
}