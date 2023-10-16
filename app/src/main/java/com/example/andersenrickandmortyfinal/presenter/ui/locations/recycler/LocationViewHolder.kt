package com.example.andersenrickandmortyfinal.presenter.ui.locations.recycler

import androidx.recyclerview.widget.RecyclerView
import com.example.andersenrickandmortyfinal.data.model.location.LocationRick
import com.example.andersenrickandmortyfinal.databinding.CharacterItemBinding

class LocationViewHolder(val binding: CharacterItemBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(item: LocationRick) {
        item?.let {
            binding.name.text = it.name
            binding.gender.text = it.dimension
            binding.species.text = it.type
//            Glide.with(binding.root.context).load(it.image).into(binding.imageView)

        }

    }


}