package com.example.andersenrickandmortyfinal.presenter.ui.episodes.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.example.andersenrickandmortyfinal.data.model.episode.Episode
import com.example.andersenrickandmortyfinal.databinding.CharacterItemBinding
import com.example.andersenrickandmortyfinal.presenter.ui.characters.OnClick
import javax.inject.Inject

class EpisodesAdapter @Inject constructor() :
    PagingDataAdapter<Episode, EpisodeViewHolder>(
        EpisodeUtil()
    ) {
    private var onClick: OnClick? = null
    fun bind(onClick: OnClick) {
        this.onClick = onClick
    }

    override fun onBindViewHolder(holder: EpisodeViewHolder, position: Int) {
        val item = getItem(position)
        item?.let {
            holder.bind(it)

        }
        holder.binding.characterItem.setOnClickListener {

            item?.let { item -> onClick?.onClick(item) }

            println("WOWOWOWOWO")


        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EpisodeViewHolder {
        val binding =
            CharacterItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return EpisodeViewHolder(binding)
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