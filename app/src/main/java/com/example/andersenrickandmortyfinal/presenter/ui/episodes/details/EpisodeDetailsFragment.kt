package com.example.andersenrickandmortyfinal.presenter.ui.episodes.details

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.andersenrickandmortyfinal.data.base.BaseFragment
import com.example.andersenrickandmortyfinal.data.model.character.Character
import com.example.andersenrickandmortyfinal.data.network.connectionmanager.ConnectionManager
import com.example.andersenrickandmortyfinal.databinding.FragmentEpisodeDetailsBinding
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

    @Inject
    lateinit var networkConnectionManager: ConnectionManager
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentEpisodeDetailsBinding
        get() = FragmentEpisodeDetailsBinding::inflate

    override fun setupViews() {
        viewModel.getCharacters(requireArguments())
        val swipeToRefresh = binding.swiperefresh
        initRecycler(binding.recyclerview, characterAdapter)
        networkConnectionManager.startListenNetworkState()
        swipeToRefreshInDetailsFragment(swipeToRefresh, { characterAdapter.refresh() },
            { viewModel.getSingleEpisodeByIdFromInternet(requireArguments()) },
            { viewModel.getSingleEpisodeFromDb(requireArguments()) }
        )
        getDetailsInDetailsFragment(
            { viewModel.getSingleEpisodeByIdFromInternet(requireArguments()) },
            { viewModel.getSingleEpisodeFromDb(requireArguments()) }
        )


    }



    override fun observeViewModel() {
        observeNavigation(viewModel)
        collectCharacters()
        collectEpisode()
        fromAdapterToCharacterDetailsFragment()
        listenToInternet()

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
//                    println(it.air_date + "EEEEEEEEEEEEEEEEEE")
                    with(binding) {
                        name.text = it.name
                        airDate.text = it.airDate
                        episode.text = it.episode


                    }
                }
            }


        }
    }

    private fun fromAdapterToCharacterDetailsFragment() {
        characterAdapter.bind {
            navigateToCharacterDetailsFragment(it as Character)


        }

    }

    private fun navigateToCharacterDetailsFragment(item: Character) {

        val direction =
            EpisodeDetailsFragmentDirections.actionEpisodeDetailsFragmentToCharacterDetailsFragment(
                item
            )
        viewModel.navigate(direction)
    }


}