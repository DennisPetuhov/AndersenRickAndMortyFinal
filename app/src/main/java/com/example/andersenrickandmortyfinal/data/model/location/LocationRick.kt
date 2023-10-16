package com.example.andersenrickandmortyfinal.data.model.location

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.andersenrickandmortyfinal.data.db.characters.Constants.LOCATION_TABLE
import com.example.andersenrickandmortyfinal.data.db.converter.StringListConverter

@Entity(tableName = LOCATION_TABLE)
@TypeConverters(StringListConverter::class)
data class LocationRick(
    @PrimaryKey
    var id: Int = 0,
    var created: String = "",
    var dimension: String = "",
    var name: String = "",
    var residents: List<String> = listOf(),
    var type: String = "",
    var url: String = ""
)