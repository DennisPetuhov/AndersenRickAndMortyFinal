package com.example.andersenrickandmortyfinal.data.base

import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.andersenrickandmortyfinal.data.model.character.Character
import com.example.andersenrickandmortyfinal.databinding.CharacterItemBinding
import com.example.andersenrickandmortyfinal.presenter.ui.characters.recycler.CharacterAdapter
import com.example.andersenrickandmortyfinal.presenter.ui.characters.recycler.MyUtil
import javax.inject.Inject

class MyAdapter @Inject constructor() : BasePagedDataAdapter<Character, CharacterItemBinding>(
    MyUtil()
) {
    override fun createViewBinding(parent: ViewGroup): CharacterItemBinding {
        val binding =
            CharacterItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return binding

    }

    override fun bind(binding: CharacterItemBinding, item: Character) {

            item?.let {
                binding.name.text = it.name
                binding.gender.text = it.gender
                binding.species.text = it.species
                binding.status.text = it.status

                Glide.with(binding.root.context).load(it.image).into(binding.imageView)

            }


    }
}