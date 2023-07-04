package com.mostafa.quran.ui.cycles.home.azkar.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mostafa.quran.databinding.AzkarContentItemLayoutBinding
import com.mostafa.quran.domain.model.azkar.AzkarArray


class AzkarContentAdapter(private var listener: OnItemClickListener) :
    ListAdapter<AzkarArray, AzkarContentAdapter.AzkarContentViewHolder>(AzkarContentComparator()) {


    interface OnItemClickListener {
        fun azkarShare(position: Int)
    }

    class AzkarContentViewHolder(val binding: AzkarContentItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(listener: OnItemClickListener) {


            binding.btnShare.setOnClickListener {
                listener.azkarShare(adapterPosition)
            }


        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AzkarContentViewHolder {
        return AzkarContentViewHolder(
            AzkarContentItemLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: AzkarContentViewHolder, position: Int) {

        holder.binding.model = getItem(position)
        var currentItem = getItem(position)
        if (currentItem != null) {
            holder.bind(listener)

        }
    }

    class AzkarContentComparator : DiffUtil.ItemCallback<AzkarArray>() {
        override fun areItemsTheSame(oldItem: AzkarArray, newItem: AzkarArray) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: AzkarArray, newItem: AzkarArray) =
            oldItem == newItem

    }
}