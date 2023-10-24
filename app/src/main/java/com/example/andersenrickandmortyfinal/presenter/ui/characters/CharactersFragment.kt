package com.example.andersenrickandmortyfinal.presenter.ui.characters

import android.text.Editable
import android.text.TextWatcher
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
import androidx.paging.LoadState
import com.example.andersenrickandmortyfinal.R
import com.example.andersenrickandmortyfinal.data.base.BaseFragment
import com.example.andersenrickandmortyfinal.data.model.character.Character
import com.example.andersenrickandmortyfinal.data.model.character.TypeOfRequest
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


    override val viewModel: CharactersViewModel
        get() = vM
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentCharactersBinding =
        FragmentCharactersBinding::inflate


    override fun observeViewModel() {
        networkConnectionManager.startListenNetworkState()
        collectCharacter()
        observeNavigation(viewModel)
        listenToInternet()


    }


    fun collectCharacter() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.charactersFlow.collectLatest {
                    characterAdapter.submitData(it)
                }


            }
        }
    }

    override fun backPressed() {

    }



//    private fun   setupEditTextSearch(){
//        binding.search.addTextChangedListener(object : TextWatcher {
//            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
//
//            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
//                viewModel.onQueryChanged(p0.toString())
//            }
//
//            override fun afterTextChanged(p0: Editable?) {}
//        })
//    }

    override fun setupViews() {
        val swipeToRefresh = binding.swiperefresh
        initRecycler(binding.recyclerview, characterAdapter)
        genderSpinner()
        statusSpinner()

        setupRadioButton()
        setupEditTextSearch(binding.search){
            viewModel.onQueryChanged(it)
        }






        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                characterAdapter.loadStateFlow.collectLatest { loadState ->
                    swipeToRefresh.isRefreshing = loadState.refresh is LoadState.Loading

                    //
                    val isListEmpty =
                        loadState.refresh is LoadState.NotLoading && characterAdapter.itemCount == 0
                    // show empty list
                    binding.emptyList.isVisible = characterAdapter.itemCount == 0
                    // Only show the list if refresh succeeds.
                    binding.recyclerview.isVisible = !isListEmpty
                    // Show loading spinner during initial load or refresh.
//                    binding.progressBar.isVisible = loadState.source.refresh is LoadState.Loading
                    // Show the retry state if initial load or refresh fails.
                    binding.retryButton.isVisible = loadState.source.refresh is LoadState.Error

                    // Toast on any error, regardless of whether it came from RemoteMediator or PagingSource
                    val errorState = loadState.source.append as? LoadState.Error
                        ?: loadState.source.prepend as? LoadState.Error
                        ?: loadState.append as? LoadState.Error
                        ?: loadState.prepend as? LoadState.Error
                    errorState?.let {
                        Toast.makeText(
                            requireContext(),
                            "\uD83D\uDE28 Wooops ${it.error}",
                            Toast.LENGTH_LONG
                        ).show()
                        //
                    }
                }
            }
        }

//        rickAdapter.refresh()
        fromAdapterToDetailsFragment()

        swipeToRefresh(swipeToRefresh) { characterAdapter.refresh() }
    }

    private fun setupRadioButton(){

        binding.idRadioGroup.setOnCheckedChangeListener { radioGroup, checkedId ->
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
        viewModel.navigate(direction)
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
                if (genderList[position] == "no gender") {
                    viewModel.onSpinnerGenderChanged("")
                } else {
                    viewModel.onSpinnerGenderChanged(genderList[position])

                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // write code to perform some action
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
                    if (statusList[position] == "no status") {
                        viewModel.onSpinnerStatusChanged("")
                    } else {
                        viewModel.onSpinnerStatusChanged(statusList[position])

                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // write code to perform some action
                }
            }
        }
    }


}