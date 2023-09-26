package com.example.andersenrickandmortyfinal.data.model.character

data class CharacterRickAndMorty(
    val info: Info?,
    val results: List<ResultRickAndMorty> = listOf()
)