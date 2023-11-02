package com.example.andersenrickandmortyfinal.data.model.character

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.andersenrickandmortyfinal.data.db.characters.Constants.CHARACTER_TABLE
import com.example.andersenrickandmortyfinal.data.db.converter.NameUrlConverter
import com.example.andersenrickandmortyfinal.data.db.converter.StringListConverter
import com.example.andersenrickandmortyfinal.data.model.main.BaseModel
import com.example.andersenrickandmortyfinal.data.model.main.NameUrl
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = CHARACTER_TABLE)
@TypeConverters(StringListConverter::class, NameUrlConverter::class)
data class Character(
    @PrimaryKey
     var id: Int = 0,
     var created: String = "",
    var name: String = "",
     var url: String = "",
    var episode: List<String> = listOf(),
    var gender: String = "",
    var image: String = "",
    var origin: NameUrl = NameUrl("", ""),

    var location: NameUrl = NameUrl("", ""),
    var species: String = "",
    var status: String = "",
    var type: String = "",

) : Parcelable {
}