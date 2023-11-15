package com.example.andersenrickandmortyfinal.presenter.ui.episodes

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.andersenrickandmortyfinal.R
import com.example.andersenrickandmortyfinal.data.base.BaseFragment
import com.example.andersenrickandmortyfinal.data.model.episode.Episode
import com.example.andersenrickandmortyfinal.data.model.main.TypeOfRequest
import com.example.andersenrickandmortyfinal.databinding.FragmentEpisodesBinding
import com.example.andersenrickandmortyfinal.presenter.ui.episodes.recycler.EpisodesAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class EpisodesFragment @Inject constructor() :
    BaseFragment<FragmentEpisodesBinding, EpisodesViewModel>() {


    private val vM by viewModels<EpisodesViewModel>()

    override val viewModel: EpisodesViewModel
        get() = vM
    private lateinit var swipeToRefresh: SwipeRefreshLayout

    @Inject
    lateinit var episodesAdapter: EpisodesAdapter
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentEpisodesBinding
        get() = FragmentEpisodesBinding::inflate


    override fun setupViews() {
        swipeToRefresh = binding.swiperefresh
        initRecycler(binding.recyclerview, episodesAdapter)
        swipeToRefresh(swipeToRefresh) { episodesAdapter.refresh() }
        setupEditTextSearch(binding.search) {
            viewModel.onQueryChanged(it)
        }
        setupRadioButton()
        setupVisibility()
        fromAdapterToDetailsFragment()
    }

    override fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.pagingDataFlow.collectLatest {

                    episodesAdapter.submitData(it)
                }


            }
        }


        listenToInternet()
    }

    override fun backPressed() {

    }


    private fun setupRadioButton() {

        binding.idRadioGroup.setOnCheckedChangeListener { radioGroup, checkedId ->
            when (checkedId) {
                R.id.noneEpisodeRadio -> viewModel.onRadioButtonChanged(TypeOfRequest.None)
                R.id.episodeEpisodeRadio -> viewModel.onRadioButtonChanged(TypeOfRequest.Episode)
                R.id.nameEpisodeRadio -> viewModel.onRadioButtonChanged(TypeOfRequest.Name)
            }
        }
    }


    private fun fromAdapterToDetailsFragment() {
        episodesAdapter.bind {
            println(it)
            navigateToDetailsFragment(it as Episode)


        }

    }

    private fun navigateToDetailsFragment(item: Episode) {
        val direction =
            EpisodesFragmentDirections.actionEpisodeFragmentToEpisodeDetailsFragment(item)

        findNavController().navigate(direction)
    }

    private fun setupVisibility() {

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                episodesAdapter.loadStateFlow.collectLatest { loadState ->
                    swipeToRefresh.isRefreshing = loadState.refresh is LoadState.Loading

                    val isListEmpty =
                        loadState.refresh is LoadState.NotLoading && episodesAdapter.itemCount == 0

                    binding.emptyList.isVisible = episodesAdapter.itemCount == 0

                    binding.recyclerview.isVisible = !isListEmpty

                    binding.retryButton.isVisible = loadState.source.refresh is LoadState.Error
                    val errorState = loadState.source.append as? LoadState.Error
                        ?: loadState.source.prepend as? LoadState.Error
                        ?: loadState.append as? LoadState.Error
                        ?: loadState.prepend as? LoadState.Error
                    errorState?.let {
                        Toast.makeText(
                            requireContext(),
                            " ${it.error}",
                            Toast.LENGTH_LONG
                        ).show()

                    }
                }
            }
        }
    }

}