package com.example.andersenrickandmortyfinal.data.base

import android.os.Parcelable
import androidx.paging.PagingData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

abstract class BaseMainViewMode<T:Parcelable>:BaseViewModel() {



    protected var _pagingDataFlow: MutableStateFlow<PagingData<T>> =
        MutableStateFlow(
            PagingData.empty()
        )
    val pagingDataFlow: StateFlow<PagingData<T>>
        get() = _pagingDataFlow

}