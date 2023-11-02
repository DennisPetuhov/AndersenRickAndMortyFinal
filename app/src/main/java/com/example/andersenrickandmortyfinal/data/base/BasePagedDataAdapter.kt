package com.example.andersenrickandmortyfinal.data.base

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.viewbinding.ViewBinding
import com.example.andersenrickandmortyfinal.data.model.character.Character
import com.example.andersenrickandmortyfinal.presenter.ui.characters.OnClick

abstract class BasePagedDataAdapter<T : Any, VB : ViewBinding>(
    private val diffCallback: DiffUtil.ItemCallback<T>
) : PagingDataAdapter<T, BasePagedDataAdapter.BaseViewHolder<VB>>(diffCallback) {

    private var onClick: OnClick? = null
    fun bind(onClick: OnClick) {
        this.onClick = onClick
    }

    abstract fun createViewBinding(parent: ViewGroup): VB

    abstract fun bind(binding: VB, item: T)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<VB> {
        val binding = createViewBinding(parent)
        return BaseViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BaseViewHolder<VB>, position: Int) {
        val item = getItem(position)
        if (item != null) {
            bind(holder.binding, item)

        }
        holder.binding.root.setOnClickListener {
            onClick?.onClick(item)
        }
    }

    class BaseViewHolder<VB : ViewBinding>(val binding: VB) : ViewHolder(binding.root)


}