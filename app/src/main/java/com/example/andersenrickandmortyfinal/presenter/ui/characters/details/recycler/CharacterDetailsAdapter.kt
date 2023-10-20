package com.example.andersenrickandmortyfinal.presenter.ui.characters.details.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.andersenrickandmortyfinal.data.model.character.Character
import com.example.andersenrickandmortyfinal.databinding.CharacterDetailItemBinding

class CharacterDetailsAdapter(
    private var detailsList: MutableList<Character>,
//    private var clickFirstFragment: ClickFirstFragment
) :
    RecyclerView.Adapter<CharacterDetailsAdapter.DetailsViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailsViewHolder {
        val binding = CharacterDetailItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DetailsViewHolder(binding)
    }


    override fun getItemCount(): Int {
        return detailsList.size
    }

    override fun onBindViewHolder(holder: DetailsViewHolder, position: Int) {
        val item = detailsList[position]
        holder.bind(detailsList[position])

//        holder.ItemBinding.catItemSingle.setOnClickListener {
////            clickFirstFragment.onClick(item)
//        }
    }

    fun addData(newCatList: List<Character>) {

        val diffCallBack = MovieDiffUtil(detailsList, newCatList)
        val difference = DiffUtil.calculateDiff(diffCallBack, true)
        detailsList = newCatList.toMutableList()
        difference.dispatchUpdatesTo(this)

    }

    class DetailsViewHolder(
        val ItemBinding:CharacterDetailItemBinding,
//        private var clickFirstFragment: ClickFirstFragment?
    ) :
        RecyclerView.ViewHolder(ItemBinding.root) {
        fun bind(itemDetail: Character) {
//            ItemBinding.id.text = cat.id.toString()
//            ItemBinding.name.text = cat.name
//            ItemBinding.surname.text = cat.surname
//            ItemBinding.phone.text = cat.phone.toString()
//
//            Glide.with(catItemBinding.root.context)
//                .load("${cat.url}${Math.random()}")
//                .into(catItemBinding.imageView)


        }


    }

    class MovieDiffUtil(private val oldList: List<Character>, private val newList: List<Character>) :
        DiffUtil.Callback() {
        override fun getOldListSize(): Int {
            return oldList.size
        }

        override fun getNewListSize(): Int {
            return newList.size
        }

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].id == newList[newItemPosition].id
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition] == newList[newItemPosition]
        }
    }
}