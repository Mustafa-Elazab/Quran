package com.example.quran.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.quran.databinding.ParyTimeLayoutBinding
import com.example.quran.model.PrayTimeModel

class PrayTimeAdapter :
    ListAdapter<PrayTimeModel, PrayTimeAdapter.PrayTimeViewHolder>(PrayTimeComparator()) {

    class PrayTimeViewHolder(val binding: ParyTimeLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: PrayTimeModel) {

            binding.apply {
                tvItem.text = item.name
                tvTime.text= item.time
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PrayTimeViewHolder {
        return PrayTimeViewHolder(
            ParyTimeLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: PrayTimeViewHolder, position: Int) {
        var currentItem = getItem(position)
        if (currentItem != null) {
            holder.bind(currentItem)
        }
    }

    class PrayTimeComparator : DiffUtil.ItemCallback<PrayTimeModel>() {
        override fun areItemsTheSame(oldItem: PrayTimeModel, newItem: PrayTimeModel) =
            oldItem.name == newItem.name

        override fun areContentsTheSame(oldItem: PrayTimeModel, newItem: PrayTimeModel) =
            oldItem == newItem
    }
}