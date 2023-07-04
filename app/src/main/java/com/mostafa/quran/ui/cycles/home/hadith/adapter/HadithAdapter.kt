package com.mostafa.quran.ui.cycles.home.hadith.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mostafa.quran.R
import com.mostafa.quran.databinding.HadithItemLayoutBinding
import com.mostafa.quran.domain.model.hadith.HadithResponseItem
import com.mostafa.quran.ui.cycles.home.hadith.fragment.list.HadithFragmentDirections

class HadithAdapter :
    ListAdapter<HadithResponseItem, HadithAdapter.ItemViewHolder>(HadithResponseItemDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.hadith_item_layout,
                parent,
                false
            )
        )
    }


    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.view.model = getItem(position)
        holder.itemView.setOnClickListener {
            var action =
                HadithFragmentDirections.actionHadithFragmentToHadithContentFragment(
                    getItem(
                        position
                    )
                )
            Navigation.findNavController(it).navigate(action)
        }
    }

    inner class ItemViewHolder(val view: HadithItemLayoutBinding) :
        RecyclerView.ViewHolder(view.root) {

    }

    class HadithResponseItemDiffCallback : DiffUtil.ItemCallback<HadithResponseItem>() {
        override fun areItemsTheSame(
            oldItem: HadithResponseItem,
            newItem: HadithResponseItem
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: HadithResponseItem,
            newItem: HadithResponseItem
        ): Boolean {
            return oldItem == newItem
        }
    }


}