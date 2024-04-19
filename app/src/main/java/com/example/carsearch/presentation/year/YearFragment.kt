package com.example.carsearch.presentation.year

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.carsearch.R
import com.example.carsearch.databinding.FragmentYearBinding
import com.example.carsearch.domain.core.model.CarSummary
import com.example.carsearch.domain.core.model.main.Year
import com.example.carsearch.presentation.year.adapter.YearsAdapter
import com.example.carsearch.presentation.year.state.YearsListUiState
import com.example.carsearch.utils.displaySelection
import com.example.carsearch.utils.onHide
import com.example.carsearch.utils.onShow
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class YearFragment : Fragment(R.layout.fragment_year) {

    private var _binding: FragmentYearBinding? = null
    private val binding get() = _binding!!

    private val viewModel: YearViewModel by viewModels()

    private val args: YearFragmentArgs by navArgs()

    private val adapter = YearsAdapter {
        navigateToSummary(it)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentYearBinding.bind(view)
        setupRecyclerView()
        viewModel.loadYears(args.carSummary)
        observeViewModelState()
    }

    private fun setupRecyclerView() {
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = this@YearFragment.adapter
        }
    }

    private fun observeViewModelState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.uiState.collect { state ->
                handleState(state)
            }
        }
    }

    private fun handleState(state: YearsListUiState) {
        when (state) {
            is YearsListUiState.Loading -> showLoading()
            is YearsListUiState.ErrorOcurred -> showError()
            is YearsListUiState.ListSuccessfullyFetched -> showContent(state.list)
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

    private fun showContent(list: List<Year>) {
        binding.successfullContentViewGroup.onShow()
        binding.loadingViewGroup.onHide()
        binding.errorViewGroup.onHide()
        binding.selectionTextView.text = args.carSummary.displaySelection()
        adapter.submitList(list)
    }

    private fun navigateToSummary(year: Year) {
        val args = args.carSummary
        val action = YearFragmentDirections.actionCarYearFragmentToSummaryFragment(
            CarSummary(
                manufacturer = args.manufacturer,
                model = args.model,
                year = year
            )
        )
        findNavController().navigate(action)
    }
}