package com.example.andersenrickandmortyfinal.data.model.episode

import com.squareup.moshi.Json

data class EpisodePojo(

    var id: Int,
    @Json(name="air_date")
    var airDate: String,
    var characters: List<String>,
    var created: String,
    var episode: String,
    var name: String,
    var url: String
)

fun EpisodePojo.toEntity(): Episode {
    return Episode(
        id = this.id,
        airDate = this.airDate,
        name = this.name,
        url = this.url,
        episode = this.episode,
        created = this.created,
        characters = this.characters


        )
}
