package com.mostafa.quran.ui.adapter


import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mostafa.quran.databinding.AzkarItemLayoutBinding
import com.mostafa.quran.domain.model.azkar.AzkarResponseItem
import com.mostafa.quran.ui.fragment.AzkarFragmentDirections


class AzkarAdapter :
    ListAdapter<AzkarResponseItem, AzkarAdapter.AzkarViewHolder>(AzkarListComparator()) {

    class AzkarViewHolder(val binding: AzkarItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: AzkarResponseItem) {

            binding.apply {

                tvZkarName.text = item.category

            }

            binding.root.setOnClickListener {

                Log.i("TAG", "bind: ${item.array.size}")

                var action =
                    AzkarFragmentDirections.actionAzkarFragmentToAzkarContentFragment(item)
                Navigation.findNavController(itemView).navigate(action)

            }
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AzkarViewHolder {
        return AzkarViewHolder(
            AzkarItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: AzkarViewHolder, position: Int) {
        var currentItem = getItem(position)
        if (currentItem != null) {
            holder.bind(currentItem)

        }
    }

    class AzkarListComparator : DiffUtil.ItemCallback<AzkarResponseItem>() {
        override fun areItemsTheSame(oldItem: AzkarResponseItem, newItem: AzkarResponseItem) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: AzkarResponseItem, newItem: AzkarResponseItem) =
            oldItem == newItem

    }
}