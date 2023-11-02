package com.example.andersenrickandmortyfinal.data.model.location


data class LocationPojo(
    var id: Int,
    var created: String,
    var dimension: String,
    var name: String,
    var residents: List<String>,
    var type: String,
    var url: String
)

fun LocationPojo.toEntity(): LocationRick {
    return LocationRick(
        id = this.id,
        dimension = this.dimension,
        name = this.name,
        url = this.url,
        residents = this.residents,
        created = this.created,
        type = this.type

    )
}
