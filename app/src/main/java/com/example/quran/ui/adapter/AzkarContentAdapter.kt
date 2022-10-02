package com.example.quran.ui.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.quran.databinding.AzkarContentItemLayoutBinding
import com.example.quran.model.azkar.Array


class AzkarContentAdapter(private var listener: AzkarContentAdapter.OnItemClickListener) :
    ListAdapter<Array, AzkarContentAdapter.AzkarContentViewHolder>(AzkarContentComparator()) {


    interface OnItemClickListener {
        fun azkarShare(position: Int)
    }

    class AzkarContentViewHolder(val binding: AzkarContentItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Array, listener: OnItemClickListener) {

            binding.apply {

                tvZakarContent.text = item.text
                tvZakarRepeat.text = item.count.toString()


            }

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
        var currentItem = getItem(position)
        if (currentItem != null) {
            holder.bind(currentItem, listener)

        }
    }

    class AzkarContentComparator : DiffUtil.ItemCallback<Array>() {
        override fun areItemsTheSame(oldItem: Array, newItem: Array) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Array, newItem: Array) =
            oldItem == newItem

    }
}