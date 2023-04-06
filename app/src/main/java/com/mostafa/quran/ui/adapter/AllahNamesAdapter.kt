package com.mostafa.quran.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mostafa.quran.databinding.AllahNamesItemLayoutBinding
import com.mostafa.quran.domain.model.names_allah.NamesOfAllah


class AllahNamesAdapter (private var listener : OnItemClickListener):
    ListAdapter<NamesOfAllah, AllahNamesAdapter.AllahNamesViewHolder>(QuranListComparator()) {


    interface OnItemClickListener {
        fun alertDialog(position : Int)
    }

    class AllahNamesViewHolder(val binding: AllahNamesItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {



        fun bind(item: NamesOfAllah, listener: OnItemClickListener) {

            binding.apply {
                tvAllahName.text = item.name
            }

          binding.root.setOnClickListener {
              listener.alertDialog(adapterPosition)
          }
        }




    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllahNamesViewHolder {

        return AllahNamesViewHolder(
            AllahNamesItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: AllahNamesViewHolder, position: Int) {

        var currentItem = getItem(position)
        if (currentItem != null) {
            holder.bind(currentItem,listener)
        }
    }

    class QuranListComparator : DiffUtil.ItemCallback<NamesOfAllah>() {
        override fun areItemsTheSame(oldItem: NamesOfAllah, newItem: NamesOfAllah) =
            oldItem.name == newItem.name

        override fun areContentsTheSame(oldItem: NamesOfAllah, newItem: NamesOfAllah) =
            oldItem == newItem

    }
}

