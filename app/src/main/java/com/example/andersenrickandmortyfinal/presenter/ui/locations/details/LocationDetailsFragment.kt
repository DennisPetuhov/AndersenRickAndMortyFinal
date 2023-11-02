package com.example.andersenrickandmortyfinal.presenter.ui.locations.details

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.andersenrickandmortyfinal.data.base.BaseFragment
import com.example.andersenrickandmortyfinal.data.model.character.Character
import com.example.andersenrickandmortyfinal.databinding.FragmentLocationDetailsBinding
import com.example.andersenrickandmortyfinal.presenter.ui.characters.recycler.CharacterAdapter
import com.example.andersenrickandmortyfinal.presenter.ui.episodes.details.EpisodeDetailsFragmentDirections
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class LocationDetailsFragment @Inject constructor() :
    BaseFragment<FragmentLocationDetailsBinding, LocationDetailsViewModel>() {

    private val vM by viewModels<LocationDetailsViewModel>()
    override val viewModel: LocationDetailsViewModel
        get() = vM


    @Inject
    lateinit var characterAdapter: CharacterAdapter

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentLocationDetailsBinding
        get() = FragmentLocationDetailsBinding::inflate

    override fun setupViews() {
        val swipeToRefresh = binding.swiperefresh
        viewModel.getCharacters(requireArguments())
        initRecycler(binding.recyclerCharaters,characterAdapter)

        swipeToRefreshInDetailsFragment(swipeToRefresh, { characterAdapter.refresh() },
            { viewModel.getSingleLocationByIdFromInternet(requireArguments()) },
            { viewModel.getSingleLocationFromDb(requireArguments()) }
        )
        getDetailsInDetailsFragment(
            { viewModel.getSingleLocationByIdFromInternet(requireArguments()) },
            { viewModel.getSingleLocationFromDb(requireArguments()) }
        )

    }

    override fun observeViewModel() {
        observeNavigation(viewModel)
        listenToInternet()
        collectCharacters()
        collectLocation()
        fromAdapterToCharacterDetailsFragment()

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



    private fun collectLocation() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.locationFlow.collect {

                    with(binding) {
                        name.text = it.name
                        airDate.text = it.dimension
                        episode.text = it.type



                    }
                }
            }


        }
    }


    override fun backPressed() {
        val direction =
            LocationDetailsFragmentDirections.actionLocationDetailsFragmentToLocatonFragment()
        viewModel.navigate(direction)

    }



    private fun fromAdapterToCharacterDetailsFragment() {
        characterAdapter.bind {
            navigateToCharacterDetailsFragment(it as Character)


        }

    }

    private fun navigateToCharacterDetailsFragment(item: Character) {

        val direction =
            LocationDetailsFragmentDirections.actionLocationDetailsFragmentToCharacterDetailsFragment(
                item
            )

        viewModel.navigate(direction)
    }


}