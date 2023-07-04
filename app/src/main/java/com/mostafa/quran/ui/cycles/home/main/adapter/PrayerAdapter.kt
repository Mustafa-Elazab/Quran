package com.mostafa.quran.ui.cycles.home.main.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mostafa.quran.R
import com.mostafa.quran.databinding.ItemScheduleBinding
import com.mostafa.quran.domain.model.PrayerTime

class PrayerAdapter : ListAdapter<PrayerTime, PrayerAdapter.ItemViewHolder>(DataDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_schedule,
                parent,
                false
            )
        )
    }


    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.view.item = getItem(position)
    }

    inner class ItemViewHolder(val view: ItemScheduleBinding) :
        RecyclerView.ViewHolder(view.root) {

    }

    class DataDiffCallback : DiffUtil.ItemCallback<PrayerTime>() {
        override fun areItemsTheSame(oldItem: PrayerTime, newItem: PrayerTime): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: PrayerTime, newItem: PrayerTime): Boolean {
            return oldItem == newItem
        }
    }


}