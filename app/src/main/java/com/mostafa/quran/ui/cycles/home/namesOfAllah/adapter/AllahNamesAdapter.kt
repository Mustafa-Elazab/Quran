package com.mostafa.quran.ui.cycles.home.namesOfAllah.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mostafa.quran.R
import com.mostafa.quran.databinding.AllahNamesItemLayoutBinding
import com.mostafa.quran.domain.model.names_allah.NamesOfAllah

class AllahNamesAdapter :
    ListAdapter<NamesOfAllah, AllahNamesAdapter.ItemViewHolder>(NamesOfAllahDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.allah_names_item_layout,
                parent,
                false
            )
        )
    }


    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.view.model = getItem(position)
    }

    inner class ItemViewHolder(val view: AllahNamesItemLayoutBinding) :
        RecyclerView.ViewHolder(view.root) {

    }

    class NamesOfAllahDiffCallback : DiffUtil.ItemCallback<NamesOfAllah>() {
        override fun areItemsTheSame(oldItem: NamesOfAllah, newItem: NamesOfAllah): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: NamesOfAllah, newItem: NamesOfAllah): Boolean {
            return oldItem == newItem
        }
    }


}