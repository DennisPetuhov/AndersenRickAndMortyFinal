package com.example.andersenrickandmortyfinal.presenter.ui.characters.recycler

import androidx.recyclerview.widget.RecyclerView
import com.example.andersenrickandmortyfinal.data.model.character.CharacterRickAndMorty
import com.example.andersenrickandmortyfinal.databinding.CharacterItemBinding

class CharacterViewHolder (private val binding: CharacterItemBinding): RecyclerView.ViewHolder(binding.root) {
    fun bind(item: CharacterRickAndMorty) {
        item?.let{
            binding.name.text = it.name
            binding.gender.text = it.gender
            binding.species.text= it.species
            binding.status.text= it.status

        }

    }


}