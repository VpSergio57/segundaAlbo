package com.example.segundaalbo.BeerListModule

import android.util.Log
import com.example.segundaalbo.SegundaAlboApplication
import com.example.segundaalbo.common.db.entities.BeerEntity
import kotlinx.coroutines.*
import retrofit2.Response
import java.lang.Exception

class BeerListInteractor {

    val scope = CoroutineScope(Dispatchers.IO)

    fun getBeersApi(page:String, perPege:String,callback: (MutableList<BeerEntity>) -> Unit){
        scope.launch {
            try {
                val response: Response<MutableList<BeerEntity>> =
                    SegundaAlboApplication.retrofitApi.getBeers(page, perPege)
                if (response.isSuccessful) {
                    callback(response.body()!!)
                }
            }
            catch (e:Exception){
                scope.launch {
                    //Log.d("HOLA","AQUI ANDAMOS")
                    callback(mutableListOf())
                }

            }
        }
    }

    fun getBeersRoom(callback: (MutableList<BeerEntity>) -> Unit){
        scope.launch {
           val response = SegundaAlboApplication.database.beersDao().getAllBeers()
            callback(response)
        }
    }

    fun saveOnDataBase(beerList: MutableList<BeerEntity>){
        scope.launch {
           SegundaAlboApplication.database.beersDao().insertAllBears(beerList)
        }
    }

}