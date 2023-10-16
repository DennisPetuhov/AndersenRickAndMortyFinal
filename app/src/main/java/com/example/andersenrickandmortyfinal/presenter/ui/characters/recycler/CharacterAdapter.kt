package com.example.andersenrickandmortyfinal.presenter.ui.characters.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.example.andersenrickandmortyfinal.data.model.character.CharacterRickAndMorty
import com.example.andersenrickandmortyfinal.databinding.CharacterItemBinding
import com.example.andersenrickandmortyfinal.presenter.ui.characters.OnClick
import javax.inject.Inject

class CharacterAdapter @Inject constructor() :
    PagingDataAdapter<CharacterRickAndMorty, CharacterViewHolder>(
        MyUtil()
    ) {
    private var onClick: OnClick? = null
    fun bind(onClick: OnClick) {
        this.onClick = onClick
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        val item = getItem(position)
        item?.let {
            holder.bind(it)

        }
        holder.binding.characterItem.setOnClickListener {

            item?.let { item -> onClick?.onClick(item) }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val binding =
            CharacterItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CharacterViewHolder(binding)
    }


    class MyUtil : DiffUtil.ItemCallback<CharacterRickAndMorty>() {
        override fun areItemsTheSame(
            oldItem: CharacterRickAndMorty,
            newItem: CharacterRickAndMorty
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: CharacterRickAndMorty,
            newItem: CharacterRickAndMorty
        ): Boolean {
            return oldItem == newItem
        }
    }
}