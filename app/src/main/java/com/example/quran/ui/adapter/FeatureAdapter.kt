package com.example.quran.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.quran.databinding.MainHomeLayoutBinding
import com.example.quran.model.FeatureModel
import com.example.quran.ui.fragment.HomeFragmentDirections


class FeatureAdapter :
    ListAdapter<FeatureModel, FeatureAdapter.FeatureViewHolder>(FeatureComparator()) {

    class FeatureViewHolder(val binding: MainHomeLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {


        fun bind(item: FeatureModel) {


            binding.apply {
                imgItem.setImageResource(item.imageId)
                tvItem.text = item.name
            }
            binding.root.setOnClickListener {

                if (item.id == 1) {

                    var action = HomeFragmentDirections.actionHomeFragmentToListFragment()
                    Navigation.findNavController(itemView).navigate(action)
                }

                if (item.id == 2) {
                    var action = HomeFragmentDirections.actionHomeFragmentToHadithFragment()
                    Navigation.findNavController(itemView).navigate(action)
                }
                if (item.id == 3) {
                    var action = HomeFragmentDirections.actionHomeFragmentToAzkarFragment()
                    Navigation.findNavController(itemView).navigate(action)
                }
                if (item.id == 4) {
                    var action = HomeFragmentDirections.actionHomeFragmentToCompassFragment()
                    Navigation.findNavController(itemView).navigate(action)
                }
                if(item.id == 5){
                    var action = HomeFragmentDirections.actionHomeFragmentToSab7aFragment()
                    Navigation.findNavController(itemView).navigate(action)
                }

                if(item.id == 6){
                    var action = HomeFragmentDirections.actionHomeFragmentToNamesOfAllahFragment()
                    Navigation.findNavController(itemView).navigate(action)
                }
                if(item.id == 7) {
                    var action = HomeFragmentDirections.actionHomeFragmentToAlsalahFragment()
                    Navigation.findNavController(itemView).navigate(action)
                }

                    if(item.id == 8){
                    var action = HomeFragmentDirections.actionHomeFragmentToPrayTimeFragment()
                    Navigation.findNavController(itemView).navigate(action)
                }

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeatureViewHolder {
        return FeatureViewHolder(
            MainHomeLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: FeatureViewHolder, position: Int) {
        var currentItem = getItem(position)
        if (currentItem != null) {
            holder.bind(currentItem)
        }
    }

    class FeatureComparator : DiffUtil.ItemCallback<FeatureModel>() {
        override fun areItemsTheSame(oldItem: FeatureModel, newItem: FeatureModel) =
            oldItem.name == newItem.name

        override fun areContentsTheSame(oldItem: FeatureModel, newItem: FeatureModel) =
            oldItem == newItem
    }
}