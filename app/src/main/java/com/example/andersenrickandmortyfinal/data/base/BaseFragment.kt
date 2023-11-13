package com.example.andersenrickandmortyfinal.data.base

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.viewbinding.ViewBinding
import com.example.andersenrickandmortyfinal.R
import com.example.andersenrickandmortyfinal.data.navigation.NavigationCommand
import com.example.andersenrickandmortyfinal.data.network.connectionmanager.ConnectionManager
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject


abstract class BaseFragment<VB : ViewBinding, VM : ViewModel> : Fragment() {

    private var _binding: VB? = null
    protected val binding get() = _binding!!

    @Inject
    lateinit var connectionManager: ConnectionManager
    protected abstract val viewModel: VM

    abstract val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> VB

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = bindingInflater(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        observeViewModel()
    }

    protected fun startListenNetworkState() {
        connectionManager.startListenNetworkState()
    }


    protected fun <T : Any, VH : ViewHolder> initRecycler(
        recyclerView: RecyclerView,
        adapterOutside: PagingDataAdapter<T, VH>
    ) {
        recyclerView.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)

            adapter = adapterOutside

        }
    }

    protected fun listenToInternet() {
        connectionManager.startListenNetworkState()
        connectionManager.isNetworkConnectedFlow.onEach {
            if (!it) {
                showToastNoInternet()
            }
        }.launchIn(lifecycleScope)
    }


    protected fun getDetailsInDetailsFragment(fromInternet: () -> Unit, fromDb: () -> Unit) {
        connectionManager.isNetworkConnectedFlow
            .onEach {
                if (it) {
                    fromInternet()
                } else {
                    fromDb()
                }

            }
            .launchIn(lifecycleScope)
    }

    protected fun swipeToRefreshInDetailsFragment(
        swipeToRefresh: SwipeRefreshLayout,
        adapter: () -> Unit,
        fromInternet: () -> Unit,
        fromDb: () -> Unit
    ) {
        swipeToRefresh.setOnRefreshListener {

            connectionManager.isNetworkConnectedFlow
                .onEach {
                    if (it) {
                        adapter()
                        swipeToRefresh.isRefreshing = false
                        fromInternet()
                        showToastHaveInternet()

                    } else {
                        swipeToRefresh.isRefreshing = false
                        fromDb()
                        showToastNoInternet()

                    }


                }
                .launchIn(lifecycleScope)
        }
    }

    protected fun swipeToRefresh(
        swipeToRefresh: SwipeRefreshLayout,
        adapter: () -> Unit
    ) {
        swipeToRefresh.setOnRefreshListener {

            connectionManager.isNetworkConnectedFlow
                .onEach {

                    if (it) {
                        adapter()
                        swipeToRefresh.isRefreshing = false
                        showToastHaveInternet()

                    } else {
                        swipeToRefresh.isRefreshing = false
                        showToastNoInternet()

                    }


                }
                .launchIn(lifecycleScope)

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

    protected fun showToastHaveInternet() {
        Toast.makeText(
            requireContext(),
            requireContext().getString(R.string.yes_internet),
            Toast.LENGTH_SHORT
        ).show()
    }


    protected abstract fun setupViews()

    protected abstract fun observeViewModel()


    protected fun handleNavigation(navigationCommand: NavigationCommand) {
        val controller = findNavController()
        when (navigationCommand) {
            is NavigationCommand.Back -> {
                controller.navigateUp()
            }

            is NavigationCommand.ToDirections -> {
                controller.navigate(navigationCommand.directions)
            }

            is NavigationCommand.Null -> null

        }


    }

    protected fun observeNavigation(viewModel: BaseViewModel) {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED)
            {
                viewModel.navigation.collect {
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

    protected abstract fun backPressed()


    protected fun setupEditTextSearch(editText: EditText, onQueryChanged: (String) -> Unit) {
        editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                onQueryChanged(p0.toString())
            }

            override fun afterTextChanged(p0: Editable?) {}
        })
    }


}
