package com.example.andersenrickandmortyfinal.presenter.ui.episodes.details

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.andersenrickandmortyfinal.data.base.BaseFragment
import com.example.andersenrickandmortyfinal.databinding.FragmentEpisodeDetailsBinding
import com.example.andersenrickandmortyfinal.domain.utils.NetworkUtils
import com.example.andersenrickandmortyfinal.presenter.ui.characters.recycler.CharacterAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class EpisodeDetailsFragment :
    BaseFragment<FragmentEpisodeDetailsBinding, EpisodeDetailsViewModel>() {

    private val vM: EpisodeDetailsViewModel by viewModels()


    override val viewModel: EpisodeDetailsViewModel
        get() = vM

    @Inject
    lateinit var characterAdapter: CharacterAdapter
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentEpisodeDetailsBinding
        get() = FragmentEpisodeDetailsBinding::inflate

    override fun setupViews() {
        viewModel.getCharacters(requireArguments())
        val swipeToRefresh = binding.swiperefresh
        initRecycler(binding.recyclerview, characterAdapter)

        swipeToRefresh(swipeToRefresh) { characterAdapter.refresh()}

        chooseSOT()


    }


    fun chooseSOT() {
        if (NetworkUtils.isNetworkAvailable(requireContext())) {
            viewModel.getSingleEpisodeByIdFromInternet(requireArguments())

        } else {
            viewModel.getSingleEpisodeFromDb(requireArguments())
        }
    }

    override fun observeViewModel() {
        observeNavigation(viewModel)
        collectCharacters()
        collectEpisode()

    }

    override fun backPressed() {
        val direction =
            EpisodeDetailsFragmentDirections.actionEpisodeDetailsFragmentToEpisodeFragment()
        viewModel.navigate(direction)

    }

    private fun collectCharacters() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.characterFlow.collect {
                    characterAdapter.submitData(it)
                }
            }


        }
    }

    private fun collectEpisode() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.episodeFlow.collect {

                    with(binding) {
                        name.text = it.name
                        airDate.text = it.air_date
                        episode.text = it.episode


                    }
                }
            }


        }
    }


    fun setTextViews() {

    }


}