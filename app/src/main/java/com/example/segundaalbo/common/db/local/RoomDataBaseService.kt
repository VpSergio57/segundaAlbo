package com.example.segundaalbo.common.db.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.segundaalbo.common.db.entities.BeerEntity


@Database(entities = arrayOf(BeerEntity::class), version = 1)
abstract class RoomDataBaseService: RoomDatabase() {
    abstract fun beersDao(): BeersDao

    companion object {
        @Volatile private var instance: RoomDataBaseService? = null

        fun getDatabase(context: Context): RoomDataBaseService =
            instance ?: synchronized(this) { instance ?: buildDatabase(context).also { instance = it } }

        private fun buildDatabase(appContext: Context) =
            Room.databaseBuilder(
                appContext,
                RoomDataBaseService::class.java,
                "AlboDatabase")
                .fallbackToDestructiveMigration()
                .build()

    }

}