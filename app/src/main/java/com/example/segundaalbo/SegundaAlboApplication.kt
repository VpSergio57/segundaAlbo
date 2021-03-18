package com.example.segundaalbo

import android.app.Application
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.segundaalbo.common.db.local.RoomDataBaseService
import com.example.segundaalbo.common.db.remote.BeerService
import com.example.segundaalbo.common.db.remote.RetrofitHttpService

class SegundaAlboApplication: Application() {

    companion object{
        lateinit var database: RoomDataBaseService
        lateinit var retrofitApi: BeerService
    }

    override fun onCreate() {
        super.onCreate()
        database = RoomDataBaseService.getDatabase(this)

        retrofitApi = RetrofitHttpService.getInstance()
        //retrofitApi.BeerService().
    }

}