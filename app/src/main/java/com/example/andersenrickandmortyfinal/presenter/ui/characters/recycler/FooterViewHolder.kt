package com.example.andersenrickandmortyfinal.presenter.ui.characters.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.example.andersenrickandmortyfinal.R
import com.example.andersenrickandmortyfinal.databinding.FooterBinding

class FooterViewHolder(
        private val binding: FooterBinding,
        retry: () -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    init {
        binding.retryButton.setOnClickListener { retry.invoke() }
    }

    fun bind(loadState: LoadState) {
        if (loadState is LoadState.Error) {
            binding.errorMsg.text = loadState.error.localizedMessage
        }
        binding.progressBar.isVisible = loadState is LoadState.Loading
        binding.retryButton.isVisible = loadState is LoadState.Error
        binding.errorMsg.isVisible = loadState is LoadState.Error
    }

    companion object {
        fun create(parent: ViewGroup, retry: () -> Unit): FooterViewHolder {
            val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.footer, parent, false)
            val binding = FooterBinding.bind(view)
            return FooterViewHolder(binding, retry)
        }
    }
}