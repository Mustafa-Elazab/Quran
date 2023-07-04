package com.mostafa.quran.ui.cycles.home.alsalah.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mostafa.quran.R
import com.mostafa.quran.databinding.AlsalahItemLayoutBinding

import com.mostafa.quran.domain.model.alsalah.Chapter
import com.mostafa.quran.ui.cycles.home.alsalah.fragment.AlsalahFragmentDirections
import com.mostafa.quran.ui.cycles.home.quran.fragment.list.ListFragmentDirections

class AlsalahAdapter : ListAdapter<Chapter, AlsalahAdapter.ItemViewHolder>(ChapterDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.alsalah_item_layout,
                parent,
                false
            )
        )
    }


    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = getItem(position)
        holder.view.model = item
        holder.itemView.setOnClickListener {
            var action = AlsalahFragmentDirections.actionAlsalahFragmentToAlsalahContentFragment(item)
            Navigation.findNavController(it).navigate(action)
        }
    }

    inner class ItemViewHolder(val view: AlsalahItemLayoutBinding) :
        RecyclerView.ViewHolder(view.root) {

    }

    class ChapterDiffCallback : DiffUtil.ItemCallback<Chapter>() {
        override fun areItemsTheSame(oldItem: Chapter, newItem: Chapter): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Chapter, newItem: Chapter): Boolean {
            return oldItem == newItem
        }
    }


}