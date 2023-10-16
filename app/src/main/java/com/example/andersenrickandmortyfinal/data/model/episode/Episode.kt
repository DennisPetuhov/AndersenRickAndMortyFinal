package com.example.andersenrickandmortyfinal.data.model.episode

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.andersenrickandmortyfinal.data.db.characters.Constants.EPISODE_TABLE
import com.example.andersenrickandmortyfinal.data.db.converter.StringListConverter
import kotlinx.parcelize.Parcelize

@Entity(tableName = EPISODE_TABLE)
@Parcelize
@TypeConverters(StringListConverter::class)
data class Episode(
    @PrimaryKey
    var id: Int = 0,
    var air_date: String = "",
    var characters: List<String> = listOf(),
    var created: String = "",
    var episode: String = "",
    var name: String = "",
    var url: String = ""
) :  Parcelable