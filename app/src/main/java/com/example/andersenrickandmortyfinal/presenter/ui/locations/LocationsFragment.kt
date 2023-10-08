package com.example.andersenrickmorty.presenter.ui.locations

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.andersenrickandmortyfinal.R
import com.example.andersenrickandmortyfinal.data.base.BaseFragment
import com.example.andersenrickandmortyfinal.databinding.FragmentLocationsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LocationsFragment : BaseFragment<FragmentLocationsBinding, LocationsViewModel>() {

    //    private var _binding: FragmentLocationsBinding? = null
//    private val binding get() = _binding!!
    private val vm:LocationsViewModel  by viewModels()
    override val layoutId: Int
        get() = R.layout.fragment_locations
    override val viewModel: LocationsViewModel
        get() = vm
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentLocationsBinding
        get() = FragmentLocationsBinding::inflate


    override fun setupViews() {

    }

    override fun observeViewModel() {

    }


    //    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View {
//        val locationsViewModel =
//            ViewModelProvider(this).get(LocationsViewModel::class.java)
//
//        _binding = FragmentLocationsBinding.inflate(inflater, container, false)
//        val root: View = binding.root
//
//
//        return root
//    }

//    override fun onDestroyView() {
//        super.onDestroyView()
//        _binding = null
//    }
}