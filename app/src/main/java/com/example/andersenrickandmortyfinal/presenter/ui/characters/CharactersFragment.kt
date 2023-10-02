package com.example.andersenrickmorty.presenter.ui.characters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.andersenrickandmortyfinal.R
import com.example.andersenrickandmortyfinal.databinding.FragmentCharactersBinding
import com.example.andersenrickandmortyfinal.domain.utils.NetworkUtils
import com.example.andersenrickandmortyfinal.presenter.ui.characters.recycler.CharacterAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class CharactersFragment @Inject constructor() : Fragment() {

    private val viewModel by viewModels<CharactersViewModel>()
    private var _binding: FragmentCharactersBinding? = null
    private val binding get() = _binding!!
    @Inject
    lateinit var rickAdapter: CharacterAdapter


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCharactersBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val swipeToRefresh = binding.swiperefresh
//        rickAdapter.addLoadStateListener { loadState->
//
//            swipeToRefresh.isRefreshing = loadState.refresh is LoadState.Loading
//            binding.recyclerview.isVisible =loadState.refresh is LoadState.NotLoading
//
//        }
        viewModel.getCharacters("c")

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {

                rickAdapter.loadStateFlow.collectLatest {loadState->
                    binding.recyclerview.isVisible =loadState.refresh is LoadState.NotLoading
                    swipeToRefresh.isRefreshing = loadState.refresh is LoadState.Loading
                }

            }
        }
        swipeToRefresh.setOnRefreshListener {
            if (NetworkUtils.isNetworkAvailable(requireContext())){
                rickAdapter.refresh()
            } else{swipeToRefresh.isRefreshing = false
         showToastNoInternet()}
//            rickAdapter.refresh()
//            swipeToRefresh.isRefreshing = false
        }
        initRecycler()
        initView()
    }

    private fun showToastNoInternet() {
        Toast.makeText(requireContext(),requireContext().getString(R.string.no_internet),Toast.LENGTH_SHORT).show()
    }
//
//    private fun initState() {
//        viewLifecycleOwner.lifecycleScope.launch {
//            repeatOnLifecycle(Lifecycle.State.STARTED) {
//
//                rickAdapter.loadStateFlow.collectLatest {loadState->
//                    binding.recyclerview.isVisible =loadState.refresh is LoadState.NotLoading
//                    swipeToRefresh.isRefreshing = loadState.refresh is LoadState.Loading
//                }
//
//
//
//            }
//        }
//    }

    private fun initView() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.charactersFlowToMediator.collectLatest {
                    rickAdapter.submitData(it)
                }


            }
        }
    }

    private fun initRecycler() {
        binding.recyclerview.apply {
            this.layoutManager = LinearLayoutManager(requireContext())
            adapter = rickAdapter
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}