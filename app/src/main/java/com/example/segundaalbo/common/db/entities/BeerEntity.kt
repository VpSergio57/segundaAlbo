package com.example.segundaalbo.common.db.entities

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import org.json.JSONArray

@Parcelize
@Entity(tableName = "BeerEntity", indices = [Index(value = ["id"], unique = true)]  )
data class BeerEntity(
    @PrimaryKey var id:Long,
    var name:String,
    var tagline:String,
    var image_url:String,
    var description:String,
    var first_brewed: String
    //var food_pairing: Array<String>
): Parcelable