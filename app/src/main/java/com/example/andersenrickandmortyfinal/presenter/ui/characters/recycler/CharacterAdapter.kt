package com.example.andersenrickandmortyfinal.presenter.ui.characters.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.example.andersenrickandmortyfinal.data.model.character.ResultRickAndMorty
import com.example.andersenrickandmortyfinal.databinding.CharacterItemBinding
import javax.inject.Inject

class CharacterAdapter @Inject constructor() : PagingDataAdapter<ResultRickAndMorty, CharacterViewHolder>(
    MyUtil()
) {


    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        val item = getItem(position)
        item?.let {
            holder.bind(it)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val binding =
            CharacterItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CharacterViewHolder(binding)
    }


    class MyUtil : DiffUtil.ItemCallback<ResultRickAndMorty>() {
        override fun areItemsTheSame(
            oldItem: ResultRickAndMorty,
            newItem: ResultRickAndMorty
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: ResultRickAndMorty,
            newItem: ResultRickAndMorty
        ): Boolean {
            return oldItem == newItem
        }
    }
}