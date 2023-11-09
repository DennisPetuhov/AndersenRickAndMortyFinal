package com.example.andersenrickandmortyfinal.presenter.ui.locations.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.example.andersenrickandmortyfinal.data.base.BasePagedDataAdapter
import com.example.andersenrickandmortyfinal.data.model.location.LocationRick
import com.example.andersenrickandmortyfinal.databinding.CharacterItemBinding
import javax.inject.Inject

class LocationAdapter @Inject constructor() :
    BasePagedDataAdapter<LocationRick, CharacterItemBinding>(
        LocationUtil()
    ) {
    override fun createViewBinding(parent: ViewGroup): CharacterItemBinding {
        return CharacterItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

    }

    override fun bind(binding: CharacterItemBinding, item: LocationRick) {

        item.let {
            binding.name.text = it.name
            binding.gender.text = it.dimension
            binding.species.text = it.type

        }


    }


    class LocationUtil : DiffUtil.ItemCallback<LocationRick>() {
        override fun areItemsTheSame(
            oldItem: LocationRick,
            newItem: LocationRick
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: LocationRick,
            newItem: LocationRick
        ): Boolean {
            return oldItem == newItem
        }
    }
}