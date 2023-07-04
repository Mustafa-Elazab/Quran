package com.mostafa.quran.ui.cycles.home.azkar.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mostafa.quran.R
import com.mostafa.quran.databinding.AzkarItemLayoutBinding
import com.mostafa.quran.domain.model.azkar.AzkarResponseItem
import com.mostafa.quran.ui.cycles.home.azkar.fragment.list.AzkarFragmentDirections

class AzkarListAdapter :
    ListAdapter<AzkarResponseItem, AzkarListAdapter.ItemViewHolder>(AzkarResponseItemDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.azkar_item_layout,
                parent,
                false
            )
        )
    }


    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {

            val item = getItem(position)
            holder.view.model = item
            holder.itemView.setOnClickListener {
                var action = AzkarFragmentDirections.actionAzkarFragmentToAzkarContentFragment(item)
                Navigation.findNavController(it).navigate(action)
            }


    }

    inner class ItemViewHolder(val view: AzkarItemLayoutBinding) :
        RecyclerView.ViewHolder(view.root) {

    }

    class AzkarResponseItemDiffCallback : DiffUtil.ItemCallback<AzkarResponseItem>() {
        override fun areItemsTheSame(
            oldItem: AzkarResponseItem,
            newItem: AzkarResponseItem
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: AzkarResponseItem,
            newItem: AzkarResponseItem
        ): Boolean {
            return oldItem == newItem
        }
    }


}