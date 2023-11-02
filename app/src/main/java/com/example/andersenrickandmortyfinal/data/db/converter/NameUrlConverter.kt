package com.example.andersenrickandmortyfinal.data.db.converter

import androidx.room.TypeConverter
import com.example.andersenrickandmortyfinal.data.model.main.NameUrl
import com.google.gson.Gson

class NameUrlConverter {
    private val gson = Gson()

    @TypeConverter
    fun fromNameUrl(nameUrl: NameUrl): String {
        return gson.toJson(nameUrl)
    }

    @TypeConverter
    fun toNameUrl(json: String): NameUrl {
        return gson.fromJson(json, NameUrl::class.java)
    }
}