package com.voronets.myfavouriteplaces.data

import androidx.lifecycle.LiveData

class PointRepository(private val pointDao: PointDao) {

    val readAllData: LiveData<List<Point>> = pointDao.readAllData()

    suspend fun addPoint(point:Point){
        pointDao.addPoint(point)
    }
    suspend fun nukeTable(){
        pointDao.nukeTable()
    }
}