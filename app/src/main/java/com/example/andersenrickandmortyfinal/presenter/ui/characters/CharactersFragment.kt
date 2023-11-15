package com.example.andersenrickandmortyfinal.presenter.ui.characters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
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
import com.example.andersenrickandmortyfinal.data.model.character.Character
import com.example.andersenrickandmortyfinal.data.model.main.TypeOfRequest
import com.example.andersenrickandmortyfinal.data.network.connectionmanager.ConnectionManager
import com.example.andersenrickandmortyfinal.databinding.FragmentCharactersBinding
import com.example.andersenrickandmortyfinal.presenter.ui.characters.recycler.CharacterAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class CharactersFragment @Inject constructor() :
    BaseFragment<FragmentCharactersBinding, CharactersViewModel>() {

    private val vM by viewModels<CharactersViewModel>()

    @Inject
    lateinit var networkConnectionManager: ConnectionManager

    @Inject
    lateinit var characterAdapter: CharacterAdapter

    private lateinit var swipeToRefresh: SwipeRefreshLayout

    override val viewModel: CharactersViewModel
        get() = vM
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentCharactersBinding =
        FragmentCharactersBinding::inflate


    override fun observeViewModel() {
        networkConnectionManager.startListenNetworkState()
        collectCharacter()

        listenToInternet()


    }


    private fun collectCharacter() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.pagingDataFlow.collectLatest {
                    characterAdapter.submitData(it)
                }


            }
        }
    }

    override fun backPressed() {

    }

    override fun setupViews() {
        swipeToRefresh = binding.swiperefresh
        initRecycler(binding.recyclerview, characterAdapter)
        genderSpinner()
        statusSpinner()
        setupRadioButton()
        setupEditTextSearch(binding.search) {
            viewModel.onQueryChanged(it)
        }



        setupVisibility()

        fromAdapterToDetailsFragment()

        swipeToRefresh(swipeToRefresh) { characterAdapter.refresh() }
    }

    private fun setupVisibility() {

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                characterAdapter.loadStateFlow.collectLatest { loadState ->
                    swipeToRefresh.isRefreshing = loadState.refresh is LoadState.Loading


                    val isListEmpty =
                        loadState.refresh is LoadState.NotLoading && characterAdapter.itemCount == 0

                    binding.emptyList.isVisible = characterAdapter.itemCount == 0

                    binding.recyclerview.isVisible = !isListEmpty

                    binding.retryButton.isVisible = loadState.source.refresh is LoadState.Error
                    val errorState = loadState.source.append as? LoadState.Error
                        ?: loadState.source.prepend as? LoadState.Error
                        ?: loadState.append as? LoadState.Error
                        ?: loadState.prepend as? LoadState.Error
                    errorState?.let {
                        Toast.makeText(
                            requireContext(),
                            "${it.error}",
                            Toast.LENGTH_LONG
                        ).show()

                    }
                }
            }
        }
    }

    private fun setupRadioButton() {

        binding.idRadioGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.nameRadio -> viewModel.onRadioButtonChanged(TypeOfRequest.Name)
                R.id.typeRadio -> viewModel.onRadioButtonChanged(TypeOfRequest.Type)
                R.id.speciesRadio -> viewModel.onRadioButtonChanged(TypeOfRequest.Species)
                R.id.noneRadio -> viewModel.onRadioButtonChanged(TypeOfRequest.None)
            }
        }
    }


    private fun fromAdapterToDetailsFragment() {
        characterAdapter.bind {
            navigateToDetailsFragment(it as Character)


        }

    }

    private fun navigateToDetailsFragment(item: Character) {

        val direction =
            CharactersFragmentDirections.actionNavigationHomeToCharacterDetailsFragment(item)
        findNavController().navigate(direction)
    }


    private fun genderSpinner() {
        val genderList = resources.getStringArray(R.array.gender_of_character)
        val spinner = binding.spinnerGender
        val adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item, genderList
        )
        spinner.adapter = adapter

        spinner.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?, position: Int, id: Long
            ) {
                if (genderList[position] == requireContext().getString(R.string.no_gender)) {
                    viewModel.onSpinnerGenderChanged("")
                } else {
                    viewModel.onSpinnerGenderChanged(genderList[position])

                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {

            }
        }
    }

    private fun statusSpinner() {
        val statusList = resources.getStringArray(R.array.status_of_character)
        val spinner = binding.spinnerStatus

        if (spinner != null) {
            val adapter = ArrayAdapter(
                requireContext(),
                android.R.layout.simple_spinner_item, statusList
            )
            spinner.adapter = adapter

            spinner.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?, position: Int, id: Long
                ) {
                    if (statusList[position] == requireContext().getString(R.string.no_status)) {
                        viewModel.onSpinnerStatusChanged("")
                    } else {
                        viewModel.onSpinnerStatusChanged(statusList[position])

                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>) {

                }
            }
        }
    }


}