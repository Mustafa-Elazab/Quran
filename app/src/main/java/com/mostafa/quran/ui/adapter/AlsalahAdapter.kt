package com.mostafa.quran.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mostafa.quran.databinding.AlsalahItemLayoutBinding
import com.mostafa.quran.domain.model.alsalah.Chapter


class AlsalahAdapter :
    ListAdapter<Chapter, AlsalahAdapter.AlsalahViewHolder>(AlsalahListComparator()) {

    class AlsalahViewHolder(val binding: AlsalahItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Chapter) {

            binding.apply {

                tvAlsalahName.text = item.name
                tvAlsalahContent.text = item.content
            }

        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlsalahViewHolder {
        return AlsalahViewHolder(
            AlsalahItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: AlsalahViewHolder, position: Int) {
        var currentItem = getItem(position)
        if (currentItem != null) {
            holder.bind(currentItem)

        }
    }

    class AlsalahListComparator : DiffUtil.ItemCallback<Chapter>() {
        override fun areItemsTheSame(oldItem: Chapter, newItem: Chapter) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Chapter, newItem: Chapter) =
            oldItem == newItem

    }
}