package com.example.carsearch.presentation.types

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.carsearch.R
import com.example.carsearch.databinding.FragmentModelBinding
import com.example.carsearch.domain.core.model.CarSummary
import com.example.carsearch.domain.core.model.main.Model
import com.example.carsearch.presentation.types.adapter.ModelAdapter
import com.example.carsearch.presentation.types.state.ModelsListUiState
import com.example.carsearch.utils.onHide
import com.example.carsearch.utils.onShow
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ModelFragment : Fragment(R.layout.fragment_model) {

    private var _binding: FragmentModelBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ModelViewModel by viewModels()

    private val args: ModelFragmentArgs by navArgs()

    private val adapter = ModelAdapter {
        navigateToYearSelection(it)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentModelBinding.bind(view)
        initUi()
    }

    private fun initUi() {
        setupRecyclerView()
        setupSearchView()
        viewModel.loadModels(args.carSummary)
        observeViewModelState()
        backNavigation()
    }

    private fun observeViewModelState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.uiState.collect { state ->
                handleState(state)
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.filteredModels.collect { filteredModels ->
                adapter.submitList(filteredModels)
            }
        }
    }

    private fun setupRecyclerView() {
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = this@ModelFragment.adapter
        }
    }

    private fun setupSearchView() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean = true

            override fun onQueryTextChange(newText: String): Boolean {
                viewModel.filterModels(newText)
                return true
            }
        })
    }

    private fun handleState(state: ModelsListUiState) {
        when (state) {
            is ModelsListUiState.Loading -> showLoading()
            is ModelsListUiState.ErrorOccurred -> showError()
            is ModelsListUiState.ListSuccessfullyFetched -> showContent(state.list)
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

    private fun showContent(list: List<Model>) {
        binding.successfullContentViewGroup.onShow()
        binding.loadingViewGroup.onHide()
        binding.errorViewGroup.onHide()
        adapter.submitList(list)
    }

    private fun navigateToYearSelection(model: Model) {
        val args = args.carSummary
        val action = ModelFragmentDirections
            .actionModelFragmentToCarYearFragment(
                CarSummary(manufacturer = args.manufacturer, model = model)
            )
        findNavController().navigate(action)
    }

    private fun backNavigation() {
        binding.back.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.recyclerView.adapter = null // Prevent memory leaks
        _binding = null
    }
}