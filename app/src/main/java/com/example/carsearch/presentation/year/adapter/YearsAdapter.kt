package com.example.carsearch.presentation.year.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.carsearch.R
import com.example.carsearch.domain.core.model.main.Year

class YearsAdapter(private val onClick: (Year) -> Unit) :
    ListAdapter<Year, YearsAdapter.YearViewHolder>(
        YearDiffCallback()
    ) {

    class YearViewHolder(itemView: View, val onClick: (Year) -> Unit) :
        RecyclerView.ViewHolder(itemView) {
        private val nameTextView: TextView = itemView.findViewById(R.id.universalNameTextView)

        private var currentYear: Year? = null

        init {
            itemView.setOnClickListener {
                currentYear?.let(onClick)
            }
        }

        fun bind(year: Year) {
            currentYear = year
            nameTextView.text = year.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): YearViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return YearViewHolder(view, onClick)
    }

    override fun onBindViewHolder(holder: YearViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class YearDiffCallback : DiffUtil.ItemCallback<Year>() {

        override fun areItemsTheSame(oldItem: Year, newItem: Year): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Year, newItem: Year): Boolean {
            return oldItem == newItem
        }

    }
}