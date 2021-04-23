package com.voronets.myfavouriteplaces.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface PointDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addPoint(point:Point)

    @Query("SELECT * FROM point_table ORDER BY id ASC")
    fun readAllData(): LiveData<List<Point>>
}