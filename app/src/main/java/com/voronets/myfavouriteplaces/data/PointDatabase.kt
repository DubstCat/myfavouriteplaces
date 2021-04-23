package com.voronets.myfavouriteplaces.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Point::class], version = 1, exportSchema = false)
abstract class PointDatabase:RoomDatabase() {
    abstract fun pointDao(): PointDao

    companion object {
        @Volatile
        private var INSTANCE: PointDatabase? = null

        fun getDatabase(context: Context): PointDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null)
                return tempInstance
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    PointDatabase::class.java,
                    "user_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}