package com.example.carsearch.presentation.manufacturers.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.carsearch.R
import com.example.carsearch.domain.core.model.main.Manufacturer

class ManufacturerListAdapter(private val onClick: (Manufacturer) -> Unit) :
    ListAdapter<Manufacturer, ManufacturerListAdapter.ManufacturerViewHolder>(
        ManufacturerDiffCallback
    ) {

    class ManufacturerViewHolder(itemView: View, val onClick: (Manufacturer) -> Unit) :
        RecyclerView.ViewHolder(itemView) {
        private val nameTextView: TextView = itemView.findViewById(R.id.universalNameTextView)

        private var currentManufacturer: Manufacturer? = null

        init {
            itemView.setOnClickListener {
                currentManufacturer?.let(onClick)
            }
        }

        fun bind(manufacturer: Manufacturer) {
            currentManufacturer = manufacturer
            nameTextView.text = manufacturer.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ManufacturerViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item, parent, false)
        return ManufacturerViewHolder(view, onClick)
    }

    override fun onBindViewHolder(holder: ManufacturerViewHolder, position: Int) {
        val manufacturer = getItem(position)
        holder.bind(manufacturer)
    }
}

object ManufacturerDiffCallback : DiffUtil.ItemCallback<Manufacturer>() {
    override fun areItemsTheSame(oldItem: Manufacturer, newItem: Manufacturer): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Manufacturer, newItem: Manufacturer): Boolean {
        return oldItem.name == newItem.name
    }
}