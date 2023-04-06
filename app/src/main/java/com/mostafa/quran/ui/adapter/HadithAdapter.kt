package com.mostafa.quran.ui.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mostafa.quran.databinding.HadithItemLayoutBinding
import com.mostafa.quran.domain.model.hadith.HadithResponseItem
import com.mostafa.quran.ui.fragment.HadithFragmentDirections


class HadithAdapter :
    ListAdapter<HadithResponseItem, HadithAdapter.HadithViewHolder>(HadithListComparator()) {

    class HadithViewHolder(val binding: HadithItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: HadithResponseItem) {

            binding.apply {

                tvHadithName.text = item.name
               tvHadithNum.text = item.id

            }
            binding.root.setOnClickListener {
                var action =
                    HadithFragmentDirections.actionHadithFragmentToHadithContentFragment(item)
                Navigation.findNavController(itemView).navigate(action)
            }


        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HadithViewHolder {
        return HadithViewHolder(
            HadithItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: HadithViewHolder, position: Int) {
        var currentItem = getItem(position)
        if (currentItem != null) {
            holder.bind(currentItem)

        }
    }

    class HadithListComparator : DiffUtil.ItemCallback<HadithResponseItem>() {
        override fun areItemsTheSame(oldItem: HadithResponseItem, newItem: HadithResponseItem) =
            oldItem.hadith == newItem.hadith

        override fun areContentsTheSame(oldItem: HadithResponseItem, newItem: HadithResponseItem) =
            oldItem == newItem

    }
}