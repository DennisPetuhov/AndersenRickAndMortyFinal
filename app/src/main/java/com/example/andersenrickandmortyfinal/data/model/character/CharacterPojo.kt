package com.example.andersenrickandmortyfinal.data.model.character

import android.os.Parcelable
import com.example.andersenrickandmortyfinal.data.model.main.NameUrl
import kotlinx.parcelize.Parcelize

@Parcelize
data class CharacterPojo
    (
    var id: Int,
    var created: String,
    var name: String,
    var url: String,
    var episode: List<String>,
    var gender: String,
    var image: String,
    var origin: NameUrl = NameUrl(),
    var location: NameUrl = NameUrl(),
    var species: String,
    var status: String,
    var type: String,

    ) : Parcelable {


}

fun CharacterPojo.toEntity(): Character {
    return Character(
        id = this.id,
        name = this.name,
        url = this.url,
        gender = this.gender,
        species = this.species,
        episode = this.episode,
        location = this.location,
        origin = this.origin,
        created = this.created,
        image = this.image,
        status = this.status,
        type = this.type
    )


}