package com.example.andersenrickandmortyfinal.data.model.main

data class PagedResponse<T>(
    val info: Info?,
    val results: List<T> = listOf()
)


data class Info(
    val count: Int,
    val pages: Int,
    val next: String?,
    val prev: String?
)