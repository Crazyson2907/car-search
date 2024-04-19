package com.example.carsearch.presentation.summary

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.carsearch.R
import com.example.carsearch.databinding.FragmentSummaryBinding
import com.example.carsearch.domain.core.model.CarSummary
import com.example.carsearch.utils.displayName

class SummaryFragment : Fragment(R.layout.fragment_summary) {

    private var _binding: FragmentSummaryBinding? = null
    private val binding get() = _binding!!

    private val args: SummaryFragmentArgs by navArgs()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentSummaryBinding.bind(view)
        setText(args.carSummary)
        buttonHandle()
    }

    private fun setText(summary: CarSummary) {
        binding.manufacturerTextView.text = summary.manufacturer.displayName()
        binding.carTypeTextView.text = summary.model.displayName()
        binding.yearTextView.text = summary.year.displayName()
    }

    private fun buttonHandle() {
        binding.button.setOnClickListener {
            val action = SummaryFragmentDirections.actionSummaryFragmentToManufacturersFragment()
            findNavController().navigate(action)
        }
    }
}