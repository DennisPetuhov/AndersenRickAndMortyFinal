package com.example.andersenrickandmortyfinal.presenter.ui.characters.details

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.andersenrickandmortyfinal.data.base.BaseFragment
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


        viewModel.getEpisodes(requireArguments())



    }

    override fun observeViewModel() {
        observeNavigation(viewModel)

        initRecycler()
        viewModel.getArguments(requireArguments())
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




//    override fun onAttach(context: Context) {
//        super.onAttach(context)
//        val callback: OnBackPressedCallback =
//            object : OnBackPressedCallback(true) {
//                override fun handleOnBackPressed() {
//                    val direction =
//                        CharacterDetailsFragmentDirections.actionCharacterDetailsFragmentToNavigationHome()
//                    viewModel.navigate(direction)
//                }
//            }
//        requireActivity().onBackPressedDispatcher.addCallback(
//            this,
//            callback
//
//        )
//    }

    override fun backPressed() {
                val direction =
            CharacterDetailsFragmentDirections.actionCharacterDetailsFragmentToNavigationHome()
        viewModel.navigate(direction)
    }
}