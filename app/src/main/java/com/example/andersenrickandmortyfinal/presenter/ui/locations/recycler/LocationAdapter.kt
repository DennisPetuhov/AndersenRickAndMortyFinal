package com.example.andersenrickandmortyfinal.presenter.ui.locations.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.example.andersenrickandmortyfinal.data.model.location.LocationRick
import com.example.andersenrickandmortyfinal.databinding.CharacterItemBinding
import com.example.andersenrickandmortyfinal.presenter.ui.characters.OnClick
import javax.inject.Inject

class LocationAdapter @Inject constructor() :
    PagingDataAdapter<LocationRick, LocationViewHolder>(
        LocationUtil()
    ) {
    private var onClick: OnClick? = null
    fun bind(onClick: OnClick) {
        this.onClick = onClick
    }

    override fun onBindViewHolder(holder: LocationViewHolder, position: Int) {
        val item = getItem(position)
        item?.let {
            holder.bind(it)

        }
        holder.binding.characterItem.setOnClickListener {

            item?.let { item -> onClick?.onClick(item) }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationViewHolder {
        val binding =
            CharacterItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LocationViewHolder(binding)
    }


    class LocationUtil : DiffUtil.ItemCallback<LocationRick>() {
        override fun areItemsTheSame(
            oldItem: LocationRick,
            newItem: LocationRick
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: LocationRick,
            newItem: LocationRick
        ): Boolean {
            return oldItem == newItem
        }
    }
}