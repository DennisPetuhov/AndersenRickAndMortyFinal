package com.example.andersenrickandmortyfinal.presenter.ui.episodes.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.example.andersenrickandmortyfinal.data.base.BasePagedDataAdapter
import com.example.andersenrickandmortyfinal.data.model.episode.Episode
import com.example.andersenrickandmortyfinal.databinding.CharacterItemBinding
import com.example.andersenrickandmortyfinal.presenter.ui.characters.OnClick
import javax.inject.Inject

class EpisodesAdapter@Inject constructor() : BasePagedDataAdapter<Episode, CharacterItemBinding>(
   EpisodeUtil()
) {
    override fun createViewBinding(parent: ViewGroup): CharacterItemBinding {
        return CharacterItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

    }

    override fun bind(binding: CharacterItemBinding, item: Episode) {

        item.let {
            binding.name.text = it.name
            binding.gender.text = it.episode
            binding.species.text = it.airDate


        }


    }


    class EpisodeUtil : DiffUtil.ItemCallback<Episode>() {
        override fun areItemsTheSame(
            oldItem: Episode,
            newItem: Episode
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: Episode,
            newItem: Episode
        ): Boolean {
            return oldItem == newItem
        }
    }
}
