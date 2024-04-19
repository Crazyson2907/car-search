package com.example.carsearch.presentation.types.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.carsearch.R
import com.example.carsearch.domain.core.model.main.Model

class ModelAdapter(private val onClick: (Model) -> Unit) :
    ListAdapter<Model,ModelAdapter.ModelViewHolder>(
        ModelDiffCallback()
    ) {

    class ModelViewHolder(itemView: View, val onClick: (Model) -> Unit) :
        RecyclerView.ViewHolder(itemView) {
        private val nameTextView: TextView = itemView.findViewById(R.id.universalNameTextView)

        private var currentModel: Model? = null

        init {
            itemView.setOnClickListener {
                currentModel?.let(onClick)
            }
        }

        fun bind(model: Model) {
            currentModel = model
            nameTextView.text = model.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ModelViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return ModelViewHolder(view, onClick)
    }

    override fun onBindViewHolder(holder: ModelViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ModelDiffCallback : DiffUtil.ItemCallback<Model>() {
        override fun areItemsTheSame(oldItem: Model, newItem: Model): Boolean {
           return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Model, newItem: Model): Boolean {
            return oldItem == newItem
        }

    }
}