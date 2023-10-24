package com.example.andersenrickandmortyfinal.presenter.ui.locations.details

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.andersenrickandmortyfinal.data.base.BaseFragment
import com.example.andersenrickandmortyfinal.databinding.FragmentLocationDetailsBinding
import com.example.andersenrickandmortyfinal.databinding.FragmentLocationsBinding
import com.example.andersenrickandmortyfinal.presenter.ui.characters.recycler.CharacterAdapter
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
    lateinit var characterAdapter:CharacterAdapter
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentLocationDetailsBinding
        get() = FragmentLocationDetailsBinding::inflate

    override fun setupViews() {

    }

    override fun observeViewModel() {
        observeNavigation(viewModel)
        listenToInternet()

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
    override fun backPressed() {
        val direction =
            LocationDetailsFragmentDirections.actionLocationDetailsFragmentToLocatonFragment()
        viewModel.navigate(direction)

    }


}