package com.example.andersenrickandmortyfinal.presenter.ui.characters.recycler

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.andersenrickandmortyfinal.data.model.character.Character
import com.example.andersenrickandmortyfinal.databinding.CharacterItemBinding

class CharacterViewHolder ( val binding: CharacterItemBinding): RecyclerView.ViewHolder(binding.root) {
    fun bind(item: Character) {
        item?.let {
            binding.name.text = it.name
            binding.gender.text = it.gender
            binding.species.text = it.species
            binding.status.text = it.status

            Glide.with(binding.root.context).load(it.image).into(binding.imageView)

        }

    }


}