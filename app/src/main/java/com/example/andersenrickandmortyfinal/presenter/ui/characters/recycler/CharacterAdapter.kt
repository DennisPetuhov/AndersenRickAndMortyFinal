package com.example.andersenrickandmortyfinal.presenter.ui.characters.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.bumptech.glide.Glide
import com.example.andersenrickandmortyfinal.data.base.BasePagedDataAdapter
import com.example.andersenrickandmortyfinal.data.model.character.Character
import com.example.andersenrickandmortyfinal.databinding.CharacterItemBinding
import javax.inject.Inject

class CharacterAdapter @Inject constructor() :
    BasePagedDataAdapter<Character, CharacterItemBinding>(
        MyUtil()
    ) {
    override fun createViewBinding(parent: ViewGroup): CharacterItemBinding {
        return CharacterItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

    }

    override fun bind(binding: CharacterItemBinding, item: Character) {

        item.let {
            binding.name.text = it.name
            binding.gender.text = it.gender
            binding.species.text = it.species
            binding.status.text = it.status

            Glide.with(binding.root.context).load(it.image).into(binding.imageView)

        }


    }


    class MyUtil : DiffUtil.ItemCallback<Character>() {
        override fun areItemsTheSame(
            oldItem: Character,
            newItem: Character
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: Character,
            newItem: Character
        ): Boolean {
            return oldItem == newItem
        }
    }
}