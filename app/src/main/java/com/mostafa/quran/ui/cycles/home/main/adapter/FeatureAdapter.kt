package com.mostafa.quran.ui.cycles.home.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mostafa.quran.R
import com.mostafa.quran.databinding.MainHomeLayoutBinding
import com.mostafa.quran.domain.model.FeatureModel
import com.mostafa.quran.ui.cycles.home.main.fragment.MainFragmentDirections


class FeatureAdapter() : ListAdapter<FeatureModel, FeatureAdapter.ViewHolder>(DIFF_CALLBACK) {
    inner class ViewHolder(val view: MainHomeLayoutBinding) : RecyclerView.ViewHolder(view.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.main_home_layout,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.view.item = getItem(position)
        var item = getItem(position)

        holder.view.root.setOnClickListener { itemView ->

            if (item.id == 1) {

                var action = MainFragmentDirections.actionMainFragmentToListFragment()
                Navigation.findNavController(itemView).navigate(action)
            }

            if (item.id == 2) {
                var action = MainFragmentDirections.actionMainFragmentToHadithFragment()
                Navigation.findNavController(itemView).navigate(action)
            }
            if (item.id == 3) {
                var action = MainFragmentDirections.actionMainFragmentToAzkarFragment()
                Navigation.findNavController(itemView).navigate(action)
            }
            if (item.id == 4) {
                var action = MainFragmentDirections.actionMainFragmentToCompassFragment()
                Navigation.findNavController(itemView).navigate(action)
            }
            if (item.id == 5) {
                var action = MainFragmentDirections.actionMainFragmentToSab7aFragment()
                Navigation.findNavController(itemView).navigate(action)
            }

            if (item.id == 6) {
                var action = MainFragmentDirections.actionMainFragmentToNamesOfAllahFragment()
                Navigation.findNavController(itemView).navigate(action)
            }
            if (item.id == 7) {
                var action = MainFragmentDirections.actionMainFragmentToAlsalahFragment()
                Navigation.findNavController(itemView).navigate(action)
            }
            if (item.id == 8) {
                var action = MainFragmentDirections.actionMainFragmentToPdfListFragment()
                Navigation.findNavController(itemView).navigate(action)
            }

        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<FeatureModel>() {
            override fun areItemsTheSame(oldItem: FeatureModel, newItem: FeatureModel): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: FeatureModel, newItem: FeatureModel): Boolean {
                return oldItem == newItem
            }
        }
    }
}