package com.mostafa.quran.ui.cycles.home.quran.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mostafa.quran.R
import com.mostafa.quran.databinding.QuranItemLayoutBinding
import com.mostafa.quran.domain.model.ChapterX
import com.mostafa.quran.ui.cycles.home.azkar.fragment.list.AzkarFragmentDirections
import com.mostafa.quran.ui.cycles.home.quran.fragment.list.ListFragmentDirections

class QuranAdapter : ListAdapter<ChapterX, QuranAdapter.ItemViewHolder>(ChapterXDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.quran_item_layout,
                parent,
                false
            )
        )
    }


    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {

        val item = getItem(position)
        holder.view.model = item
        holder.itemView.setOnClickListener {
            var action =ListFragmentDirections.actionListFragmentToQuranContentFragment(item)
            Navigation.findNavController(it).navigate(action)
        }


    }

    inner class ItemViewHolder(val view: QuranItemLayoutBinding) :
        RecyclerView.ViewHolder(view.root) {

    }

    class ChapterXDiffCallback : DiffUtil.ItemCallback<ChapterX>() {
        override fun areItemsTheSame(oldItem: ChapterX, newItem: ChapterX): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: ChapterX, newItem: ChapterX): Boolean {
            return oldItem == newItem
        }
    }


}