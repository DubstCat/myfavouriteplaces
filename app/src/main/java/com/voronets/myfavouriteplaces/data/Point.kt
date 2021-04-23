package com.voronets.myfavouriteplaces.data

import androidx.room.PrimaryKey

data class Point (
    @PrimaryKey(autoGenerate = true)
    val id:Int,
    val name: String,
    val latitude: Double,
    val longitude: Double
)