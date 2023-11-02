package com.example.andersenrickandmortyfinal.data.model.main

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.andersenrickandmortyfinal.data.db.converter.StringListConverter
import kotlinx.parcelize.Parcelize


@Entity
@TypeConverters(StringListConverter::class)
abstract class BaseModel(
    @PrimaryKey
    var id: Int = 0,
    var created: String = "",
    var name: String = "",
    var url: String = ""
) {}