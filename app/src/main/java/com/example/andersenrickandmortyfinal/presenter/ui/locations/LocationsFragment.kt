package com.example.andersenrickmorty.presenter.ui.locations

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.andersenrickandmortyfinal.data.base.BaseFragment
import com.example.andersenrickandmortyfinal.data.model.location.LocationRick
import com.example.andersenrickandmortyfinal.databinding.FragmentLocationsBinding
import com.example.andersenrickandmortyfinal.presenter.ui.locations.recycler.LocationAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class LocationsFragment @Inject constructor() :
    BaseFragment<FragmentLocationsBinding, LocationsViewModel>() {

    private val vm: LocationsViewModel by viewModels()

    @Inject
    lateinit var locationAdapter: LocationAdapter
    override val viewModel: LocationsViewModel
        get() = vm
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentLocationsBinding
        get() = FragmentLocationsBinding::inflate


    override fun setupViews() {
        val swipeToRefresh = binding.swiperefresh
        initRecycler(binding.recyclerview, locationAdapter)
        swipeToRefresh(swipeToRefresh) { locationAdapter.refresh() }


    }

    override fun observeViewModel() {
        observeAdapterChanges()
        observeNavigation(viewModel)
    }

    override fun backPressed() {

    }

    private fun observeAdapterChanges() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.locationFlow.collectLatest {
                    locationAdapter.submitData(it)
                }


            }
        }
        fromAdapterToDetailsFragment()

    }

    private fun fromAdapterToDetailsFragment() {
        locationAdapter.bind {
            navigateToDetailsFragment(it as LocationRick)


        }

    }

    private fun navigateToDetailsFragment(item: LocationRick) {
        val direction =
            LocationsFragmentDirections.actionLocatonFragmentToLocationDetailsFragment(item)
        viewModel.navigate(direction)
    }

}