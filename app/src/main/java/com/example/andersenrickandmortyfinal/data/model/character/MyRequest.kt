package com.example.andersenrickandmortyfinal.data.model.character

data class MyRequest(
    val typeOfRequest: TypeOfRequest,
    var query: String="",
    val gender: String="",
    var status: String="",

)

sealed class TypeOfRequest {
    object None : TypeOfRequest()
    object Name : TypeOfRequest()
    object Species : TypeOfRequest()
    object Type : TypeOfRequest()
    object Code : TypeOfRequest()
    object Dimension:TypeOfRequest()
}

