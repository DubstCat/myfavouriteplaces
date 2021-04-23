package com.voronets.myfavouriteplaces.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "point_table")
data class Point (
    @PrimaryKey(autoGenerate = true)
    val id:Int,
    val name: String,
    val latitude: Double,
    val longitude: Double
)