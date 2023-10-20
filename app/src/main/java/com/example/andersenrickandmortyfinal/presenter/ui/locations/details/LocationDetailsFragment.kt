package com.example.andersenrickandmortyfinal.presenter.ui.locations.details

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.andersenrickandmortyfinal.data.base.BaseFragment
import com.example.andersenrickandmortyfinal.databinding.FragmentLocationsBinding
import com.example.andersenrickmorty.presenter.ui.locations.LocationsFragmentDirections
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LocationDetailsFragment @Inject constructor() :
    BaseFragment<FragmentLocationsBinding, LocationDetailsViewModel>() {

    val vM by viewModels<LocationDetailsViewModel>()
    override val viewModel: LocationDetailsViewModel
        get() = vM
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentLocationsBinding
        get() = FragmentLocationsBinding::inflate

    override fun setupViews() {

    }

    override fun observeViewModel() {
        observeNavigation(viewModel)

    }

    override fun backPressed() {
        val direction = LocationDetailsFragmentDirections.actionLocationDetailsFragmentToLocatonFragment()
        viewModel.navigate(direction)

    }


}