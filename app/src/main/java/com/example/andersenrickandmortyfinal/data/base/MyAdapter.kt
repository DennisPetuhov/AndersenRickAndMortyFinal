package com.example.andersenrickandmortyfinal.data.base

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.andersenrickandmortyfinal.data.model.location.LocationRick
import com.example.andersenrickandmortyfinal.databinding.CharacterItemBinding
import com.example.andersenrickandmortyfinal.presenter.ui.locations.recycler.LocationAdapter
import javax.inject.Inject

class MyAdapter @Inject constructor() : BasePagedDataAdapter<LocationRick, CharacterItemBinding>(
    LocationAdapter.LocationUtil()
) {
    override fun createViewBinding(parent: ViewGroup): CharacterItemBinding {
        return CharacterItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

    }

    override fun bind(binding: CharacterItemBinding, item: LocationRick) {

        item?.let {
            binding.name.text = it.name
            binding.gender.text = it.dimension
            binding.species.text = it.type

        }


    }
}