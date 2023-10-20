package com.example.andersenrickandmortyfinal.data.base

import android.content.Context
import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.viewbinding.ViewBinding
import com.example.andersenrickandmortyfinal.R
import com.example.andersenrickandmortyfinal.domain.utils.NetworkUtils
import com.example.andersenrickandmortyfinal.data.navigation.NavigationCommand
import com.example.andersenrickandmortyfinal.presenter.ui.characters.details.CharacterDetailsFragmentDirections
import kotlinx.coroutines.launch


abstract class BaseFragment<VB : ViewBinding, VM : ViewModel> : Fragment() {

    private var _binding: VB? = null
    protected val binding get() = _binding!!



    protected abstract val viewModel: VM

    abstract val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> VB

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = bindingInflater(inflater, container, false)
//        _binding  = DataBindingUtil.inflate(inflater, layoutId, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        observeViewModel()
    }


    protected fun<T:Any,VH:ViewHolder> initRecycler(
        recyclerView: RecyclerView,
        adapterOutside: PagingDataAdapter<T,VH>
    ) {
        recyclerView.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)

            adapter =adapterOutside

        }
    }

    protected fun swipeToRefresh(
        swipeToRefresh: SwipeRefreshLayout,
        adapter: () -> Unit
    ) {
        swipeToRefresh.setOnRefreshListener {
            if (NetworkUtils.isNetworkAvailable(requireContext())) {
                adapter()
                swipeToRefresh.isRefreshing = false
            } else {
                swipeToRefresh.isRefreshing = false
                showToastNoInternet()
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    protected fun showToastNoInternet() {
        Toast.makeText(
            requireContext(),
            requireContext().getString(R.string.no_internet),
            Toast.LENGTH_SHORT
        ).show()
    }








    protected abstract fun setupViews()

    protected abstract fun observeViewModel()




   protected fun handleNavigation(navigationCommand: NavigationCommand) {
        when (navigationCommand) {
            is NavigationCommand.Back -> {
                findNavController().navigateUp()
            }
            is NavigationCommand.ToDirections -> {
                findNavController().navigate(navigationCommand.directions)
            }
            is NavigationCommand.Null -> null

        }


    }

  protected  fun observeNavigation(viewModel: BaseViewModel) {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED)
            {
                viewModel.navigation.collect{
                    handleNavigation(it)
                }

            }
        }

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val callback: OnBackPressedCallback =
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {

                    backPressed()
                }
            }
        requireActivity().onBackPressedDispatcher.addCallback(
            this,
            callback

        )


}

  protected abstract  fun backPressed()



}