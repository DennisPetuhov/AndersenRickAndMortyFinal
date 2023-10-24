package com.example.andersenrickandmortyfinal.presenter.ui.episodes

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.andersenrickandmortyfinal.R
import com.example.andersenrickandmortyfinal.data.base.BaseFragment
import com.example.andersenrickandmortyfinal.data.model.character.TypeOfRequest
import com.example.andersenrickandmortyfinal.data.model.episode.Episode
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

    @Inject
    lateinit var episodesAdapter: EpisodesAdapter
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentEpisodesBinding
        get() = FragmentEpisodesBinding::inflate


    override fun setupViews() {
        val swipeToRefresh = binding.swiperefresh
        initRecycler(binding.recyclerview, episodesAdapter)
        swipeToRefresh(swipeToRefresh) { episodesAdapter.refresh() }
        setupEditTextSearch(binding.search){
            viewModel.onQueryChanged(it)
        }
        setupRadioButton()




        fromAdapterToDetailsFragment()
    }

    override fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.episodesFlow.collectLatest {

                    episodesAdapter.submitData(it)
                }


            }
        }

        observeNavigation(viewModel)
        listenToInternet()
    }

    override fun backPressed() {

    }


    private fun setupRadioButton() {

        binding.idRadioGroup.setOnCheckedChangeListener { radioGroup, checkedId ->
            when (checkedId) {
                R.id.noneEpisodeRadio -> viewModel.onRadioButtonChanged(TypeOfRequest.None)
                R.id.episodeEpisodeRadio -> viewModel.onRadioButtonChanged(TypeOfRequest.Episode)
                R.id.nameEpisodeRadio-> viewModel.onRadioButtonChanged(TypeOfRequest.Name)
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


        viewModel.navigate(direction)
    }





}