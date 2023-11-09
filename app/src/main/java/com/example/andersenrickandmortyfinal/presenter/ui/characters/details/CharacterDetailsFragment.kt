package com.example.andersenrickandmortyfinal.presenter.ui.characters.details

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.andersenrickandmortyfinal.data.base.BaseFragment
import com.example.andersenrickandmortyfinal.data.model.episode.Episode
import com.example.andersenrickandmortyfinal.databinding.FragmentCharacterDeteilsBinding
import com.example.andersenrickandmortyfinal.presenter.ui.episodes.recycler.EpisodesAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class CharacterDetailsFragment @Inject constructor() :
    BaseFragment<FragmentCharacterDeteilsBinding, CharacterDetailsViewModel>() {


    private val vM by viewModels<CharacterDetailsViewModel>()


    @Inject
    lateinit var episodeAdapter: EpisodesAdapter


    override val viewModel: CharacterDetailsViewModel
        get() = vM
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentCharacterDeteilsBinding =
        FragmentCharacterDeteilsBinding::inflate

    override fun setupViews() {
        val swipeToRefresh = binding.swiperefresh

        viewModel.getEpisodes(requireArguments())

        getDetailsInDetailsFragment(
            { viewModel.getCharacterFromApi(requireArguments()) },
            { viewModel.findCharacterInDb(requireArguments()) })
        swipeToRefreshInDetailsFragment(
            swipeToRefresh,
            { episodeAdapter.refresh() },
            { viewModel.getCharacterFromApi(requireArguments()) },
            { viewModel.findCharacterInDb(requireArguments()) },
        )
        setupDetails()


    }

    override fun observeViewModel() {
        observeNavigation(viewModel)

        initRecycler()
        viewModel.getArguments(requireArguments())
        listenToInternet()
        fromAdapterToEpisodeDetailsFragment()
        onOriginPress()
        onLocationPress()
        arrowBack()
    }

    private fun onOriginPress() {
        binding.originChDetails6.setOnClickListener {
            val location = viewModel.getLocation(requireArguments())
            val direction =
                CharacterDetailsFragmentDirections.actionCharacterDetailsFragmentToLocationDetailsFragment(
                    location
                )
            viewModel.navigate(direction)


        }
    }

    private fun onLocationPress() {
        binding.locationChDetails.setOnClickListener {
            val location = viewModel.getOrigin(requireArguments())
            val direction =
                CharacterDetailsFragmentDirections.actionCharacterDetailsFragmentToLocationDetailsFragment(
                    location
                )
            viewModel.navigate(direction)


        }
    }


    private fun initRecycler() {
        binding.recyclerChDeteils.apply {
            layoutManager = LinearLayoutManager(requireContext())

            adapter = episodeAdapter

        }
        lifecycleScope.launch {
            viewModel.episodesFlow.collect {
                episodeAdapter.submitData(it)
            }
        }
    }


    override fun backPressed() {
        back()
    }

    private fun back() {
        val direction =
            CharacterDetailsFragmentDirections.actionCharacterDetailsFragmentToNavigationHome()
        viewModel.navigate(direction)
    }

    private fun setupDetails() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {

                viewModel.characterFlow.collect {
                    with(binding) {
                        nameChDetails.text = it.name
                        statusChDetails.text = it.status
                        speciesChDetails.text = it.species
                        typeChDetails.text = it.type
                        genderChDetails.text = it.gender
                        originChDetails6.text = it.origin.name
                        locationChDetails.text = it.location.name
                        Glide.with(requireContext()).load(it.image).into(imageChDeteils)


                    }
                }
            }
        }
    }


    private fun fromAdapterToEpisodeDetailsFragment() {
        episodeAdapter.bind {
            navigateToEpisodeDetailsFragment(it as Episode)


        }

    }

    private fun navigateToEpisodeDetailsFragment(item: Episode) {

        val direction =
            CharacterDetailsFragmentDirections.actionCharacterDetailsFragmentToEpisodeDetailsFragment(
                item
            )


        viewModel.navigate(direction)
    }


    private fun arrowBack() {
        val toolbar = requireActivity().findViewById<Toolbar>(androidx.appcompat.R.id.action_bar)

        toolbar.setNavigationOnClickListener {
            back()
        }
    }
}