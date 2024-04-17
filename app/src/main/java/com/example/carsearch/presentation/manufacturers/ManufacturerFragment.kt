package com.example.carsearch.presentation.manufacturers

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.carsearch.R
import com.example.carsearch.databinding.FragmentManufacturerBinding
import com.example.carsearch.domain.core.model.main.Manufacturer
import com.example.carsearch.presentation.manufacturers.adapter.ManufacturerListAdapter
import com.example.carsearch.presentation.manufacturers.state.ManufacturersListUiState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ManufacturersFragment : Fragment(R.layout.fragment_manufacturer) {

    private var _binding: FragmentManufacturerBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ManufacturerViewModel by viewModels()

    private val adapter = ManufacturerListAdapter {
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
        }
    }

    private fun observeViewModelState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.uiState.flowWithLifecycle(lifecycle, Lifecycle.State.STARTED).collect { state ->
                when (state) {
                    is ManufacturersListUiState.ErrorOccurred -> {
                        // Handle error state
                    }
                    is ManufacturersListUiState.Loading -> {
                        // Handle loading state
                    }
                    is ManufacturersListUiState.ListSuccessfullyFetched -> {
                        adapter.submitList(state.list)
                        // Handle successfully fetched state
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