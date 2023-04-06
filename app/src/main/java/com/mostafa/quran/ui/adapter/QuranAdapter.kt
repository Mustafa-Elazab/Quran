package com.mostafa.quran.ui.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mostafa.quran.databinding.QuranItemLayoutBinding
import com.mostafa.quran.domain.model.ChapterX
import com.mostafa.quran.ui.fragment.ListFragmentDirections


class QuranAdapter : ListAdapter<ChapterX, QuranAdapter.QuranViewHolder>(QuranListComparator()) {

    class QuranViewHolder(val binding: QuranItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ChapterX) {

            binding.apply {
                tvSurahNum.text = item.Number.toString()
                tvSurahName.text = item.name
                tvSurahType.text = item.Descent
                tvSurahVerseNum.text = item.Number_Verses.toString()
            }

            binding.root.setOnClickListener {

                var action= ListFragmentDirections.actionListFragmentToQuranContentFragment(item)
                Navigation.findNavController(it).navigate(action)
            }
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuranViewHolder {
        return QuranViewHolder(
            QuranItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: QuranViewHolder, position: Int) {
        var currentItem = getItem(position)
        if (currentItem != null) {
            holder.bind(currentItem)
        }
    }

    class QuranListComparator : DiffUtil.ItemCallback<ChapterX>() {
        override fun areItemsTheSame(oldItem: ChapterX, newItem: ChapterX) =
            oldItem.Number == newItem.Number

        override fun areContentsTheSame(oldItem: ChapterX, newItem: ChapterX) =
            oldItem == newItem

    }
}