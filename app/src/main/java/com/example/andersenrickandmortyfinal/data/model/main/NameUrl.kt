package com.example.andersenrickandmortyfinal.data.model.main

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class NameUrl(
    val name: String="",
    val url: String=""
) : Parcelable