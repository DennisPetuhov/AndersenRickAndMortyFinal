package com.example.andersenrickandmortyfinal.presenter.ui.episodes.recycler

import androidx.recyclerview.widget.RecyclerView
import com.example.andersenrickandmortyfinal.data.model.episode.Episode
import com.example.andersenrickandmortyfinal.databinding.CharacterItemBinding

class EpisodeViewHolder(val binding: CharacterItemBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: Episode) {
        item?.let {
            binding.name.text = it.name
            binding.gender.text = it.episode
            binding.species.text = it.airDate

//            Glide.with(binding.root.context).load(it.image).into(binding.imageView)

        }

    }


}