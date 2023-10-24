package com.example.andersenrickandmortyfinal.presenter.ui.locations

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.andersenrickandmortyfinal.R
import com.example.andersenrickandmortyfinal.data.base.BaseFragment
import com.example.andersenrickandmortyfinal.data.model.character.TypeOfRequest
import com.example.andersenrickandmortyfinal.data.model.location.LocationRick
import com.example.andersenrickandmortyfinal.databinding.FragmentLocationsBinding
import com.example.andersenrickandmortyfinal.presenter.ui.locations.recycler.LocationAdapter
import com.example.andersenrickmorty.presenter.ui.locations.LocationsViewModel
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
        swipeToRefresh(swipeToRefresh) {
            locationAdapter.refresh()
        }
        setupEditTextSearch(binding.search) {
            viewModel.onQueryChanged(it)
        }
        setupRadioButton()


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

//    fun   setupEditTextSearch(){
//      val a =  binding.search
//                val b=  binding.search.addTextChangedListener(object : TextWatcher {
//            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
//
//            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
//                viewModel.onQueryChanged(p0.toString())
//            }
//            override fun afterTextChanged(p0: Editable?) {}
//        })
//    }


    override fun observeViewModel() {
        observeAdapterChanges()
        observeNavigation(viewModel)
        listenToInternet()
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