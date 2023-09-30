package com.example.andersenrickandmortyfinal.data.model.character

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.andersenrickandmortyfinal.data.db.characters.Constants.CHARACTER_TABLE
import com.example.andersenrickandmortyfinal.data.db.converter.StringListConverter
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = CHARACTER_TABLE)
@TypeConverters(StringListConverter::class)
data class CharacterRickAndMorty(
    @PrimaryKey
    var id: Int=0,
    var created: String="",
    var episode: List<String> = listOf(),
    var gender: String="",
    var image: String="",
    @Ignore
    var origin: Origin = Origin("",""),
    var name: String="",
     @Ignore
     var location: Location = Location("",""),
    var species: String="",
    var status: String="",
    var type: String="",
    var url: String=""
):Parcelable{



}