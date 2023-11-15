package com.example.andersenrickandmortyfinal.presenter.ui.locations

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
import com.example.andersenrickandmortyfinal.data.model.location.LocationRick
import com.example.andersenrickandmortyfinal.data.model.main.TypeOfRequest
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
    private lateinit var swipeToRefresh: SwipeRefreshLayout


    override fun setupViews() {

        swipeToRefresh = binding.swiperefresh
        initRecycler(binding.recyclerview, locationAdapter)
        swipeToRefresh(swipeToRefresh) {
            locationAdapter.refresh()
        }
        setupEditTextSearch(binding.search) {
            viewModel.onQueryChanged(it)
        }
        setupRadioButton()
        setupVisibility()


    }

    private fun setupRadioButton() {

        binding.idLocationRadioGroup.setOnCheckedChangeListener { radioGroup, checkedId ->
            when (checkedId) {
                R.id.nameLocationRadio -> viewModel.onRadioButtonChanged(TypeOfRequest.Name)
                R.id.typeLocationRadio -> viewModel.onRadioButtonChanged(TypeOfRequest.Type)
                R.id.dimensionLocationRadio -> viewModel.onRadioButtonChanged(TypeOfRequest.Dimension)
                R.id.noneLocationRadio -> viewModel.onRadioButtonChanged(TypeOfRequest.None)
            }
        }
    }


    override fun observeViewModel() {
        observeAdapterChanges()

        listenToInternet()
    }

    override fun backPressed() {

    }

    private fun observeAdapterChanges() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.pagingDataFlow.collectLatest {
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
        findNavController().navigate(direction)
    }

    private fun setupVisibility() {

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                locationAdapter.loadStateFlow.collectLatest { loadState ->
                    swipeToRefresh.isRefreshing = loadState.refresh is LoadState.Loading

                    val isListEmpty =
                        loadState.refresh is LoadState.NotLoading && locationAdapter.itemCount == 0

                    binding.emptyList.isVisible = locationAdapter.itemCount == 0

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