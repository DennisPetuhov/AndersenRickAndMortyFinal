//package com.example.andersenrickandmortyfinal.presenter.ui.characters.ForRemmove
//
//import android.text.Editable
//import android.text.TextWatcher
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.AdapterView
//import android.widget.ArrayAdapter
//import android.widget.Toast
//import androidx.fragment.app.viewModels
//import androidx.lifecycle.Lifecycle
//import androidx.lifecycle.lifecycleScope
//import androidx.lifecycle.repeatOnLifecycle
//import androidx.paging.LoadState
//import androidx.recyclerview.widget.LinearLayoutManager
//import com.example.andersenrickandmortyfinal.R
//import com.example.andersenrickandmortyfinal.data.base.BaseFragment
//import com.example.andersenrickandmortyfinal.data.model.character.TypeOfRequest
//import com.example.andersenrickandmortyfinal.databinding.FragmentCharactersBinding
//import com.example.andersenrickandmortyfinal.domain.utils.NetworkUtils
//import com.example.andersenrickandmortyfinal.presenter.ui.characters.CharactersViewModel
//import com.example.andersenrickandmortyfinal.presenter.ui.characters.recycler.CharacterAdapter
//import kotlinx.coroutines.flow.collectLatest
//import kotlinx.coroutines.launch
//import javax.inject.Inject
//
//class CharactersFragment @Inject constructor() : BaseFragment<FragmentCharactersBinding, CharactersViewModel>() {
//    override val layoutId: Int
//        get() = R.layout.fragment_characters
//    override val viewModel: CharactersViewModel by viewModels()
//    @Inject
//    lateinit var rickAdapter: CharacterAdapter
//
//    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentCharactersBinding =
////        { inflater, container, attachToParent ->
////            FragmentCharactersBinding.inflate(inflater, container, attachToParent)
////        }
//        FragmentCharactersBinding::inflate
//
//
//
//    override fun observeViewModel() {
//        viewLifecycleOwner.lifecycleScope.launch {
//            repeatOnLifecycle(Lifecycle.State.STARTED) {
//                viewModel.charactersFlowToMediator.collectLatest {
//                    rickAdapter.submitData(it)
//                }
//
//
//            }
//        }
//    }
//
//    override fun setupViews() {
//        val swipeToRefresh = binding.swiperefresh
//        initRecycler()
//        genderSpinner()
//        statusSpinner()
//
//        binding.search.addTextChangedListener(object : TextWatcher {
//            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
//
//            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
//                viewModel.onQueryChanged(p0.toString())
//            }
//
//            override fun afterTextChanged(p0: Editable?) {}
//        })
//
//        binding.idRadioGroup.setOnCheckedChangeListener { radioGroup, checkedId ->
//            when (checkedId) {
//                R.id.nameRadio -> viewModel.onRadioButtonChanged(TypeOfRequest.Name)
//                R.id.typeRadio -> viewModel.onRadioButtonChanged(TypeOfRequest.Type)
//                R.id.speciesRadio -> viewModel.onRadioButtonChanged(TypeOfRequest.Species)
//                R.id.noneRadio -> viewModel.onRadioButtonChanged(TypeOfRequest.None)
//            }
//        }
//
//        viewLifecycleOwner.lifecycleScope.launch {
//            repeatOnLifecycle(Lifecycle.State.STARTED) {
//                viewModel.queryFlow.collect {
//                    println("rrr"+ it.status)
//                    viewModel.getCharacters(it.typeOfRequest, it.query, it.gender, it.status)
//                }
//            }
//        }
//
//        viewLifecycleOwner.lifecycleScope.launch {
//            repeatOnLifecycle(Lifecycle.State.STARTED) {
//                rickAdapter.loadStateFlow.collectLatest { loadState ->
//                    swipeToRefresh.isRefreshing = loadState.refresh is LoadState.Loading
//                }
//            }
//        }
//
//        rickAdapter.refresh()
//
//        swipeToRefresh.setOnRefreshListener {
//            if (NetworkUtils.isNetworkAvailable(requireContext())) {
//                rickAdapter.refresh()
//                swipeToRefresh.isRefreshing = false
//            } else {
//                swipeToRefresh.isRefreshing = false
//                showToastNoInternet()
//            }
//        }
//    }
//    private fun showToastNoInternet() {
//        Toast.makeText(
//            requireContext(),
//            requireContext().getString(R.string.no_internet),
//            Toast.LENGTH_SHORT
//        ).show()
//    }
//
//    private fun initRecycler() {
//        binding.recyclerview.apply {
//            layoutManager = LinearLayoutManager(requireContext())
//            adapter = rickAdapter
//        }
//    }
//
//    private fun genderSpinner() {
//        val genderList = resources.getStringArray(R.array.gender_of_character)
//        val spinner = binding.spinnerGender
//        val adapter = ArrayAdapter(
//            requireContext(),
//            android.R.layout.simple_spinner_item, genderList
//        )
//        spinner.adapter = adapter
//
//        spinner.onItemSelectedListener = object :
//            AdapterView.OnItemSelectedListener {
//            override fun onItemSelected(
//                parent: AdapterView<*>,
//                view: View, position: Int, id: Long
//            ) {
//                viewModel.onSpinerGenderChanged(genderList[position])
//            }
//
//            override fun onNothingSelected(parent: AdapterView<*>) {
//                // write code to perform some action
//            }
//        }
//    }
//
//    private fun statusSpinner() {
//        val statusList = resources.getStringArray(R.array.status_of_character)
//        val spinner = binding.spinnerStatus
//
//        if (spinner != null) {
//            val adapter = ArrayAdapter(
//                requireContext(),
//                android.R.layout.simple_spinner_item, statusList
//            )
//            spinner.adapter = adapter
//
//            spinner.onItemSelectedListener = object :
//                AdapterView.OnItemSelectedListener {
//                override fun onItemSelected(
//                    parent: AdapterView<*>,
//                    view: View, position: Int, id: Long
//                ) {
//                    viewModel.onSpinnerStatusChanged(statusList[position])
//                }
//
//                override fun onNothingSelected(parent: AdapterView<*>) {
//                    // write code to perform some action
//                }
//            }
//        }
//    }
//}