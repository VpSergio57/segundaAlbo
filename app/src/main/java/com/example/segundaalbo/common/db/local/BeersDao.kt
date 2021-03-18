package com.example.segundaalbo.common.db.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.segundaalbo.common.db.entities.BeerEntity
import retrofit2.Response

@Dao
interface BeersDao {

    @Query("SELECT * FROM BeerEntity")
    fun getAllBeers(): MutableList<BeerEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllBears(objects: MutableList<BeerEntity>)
}