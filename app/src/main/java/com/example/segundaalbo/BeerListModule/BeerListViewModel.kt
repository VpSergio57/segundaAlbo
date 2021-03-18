package com.example.segundaalbo.BeerListModule

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.segundaalbo.common.db.entities.BeerEntity
import kotlinx.coroutines.CoroutineExceptionHandler
import retrofit2.Response

class BeerListViewModel : ViewModel() {
    private var beerList: MutableList<BeerEntity>
    private var interactor: BeerListInteractor

    var status = MutableLiveData<String>()
    init{
        interactor = BeerListInteractor()
        beerList = mutableListOf()
    }

    private val beers:  MutableLiveData<MutableList<BeerEntity>> by lazy {
        MutableLiveData<MutableList<BeerEntity>>().also {
        }
    }

    fun getBeers(): LiveData<MutableList<BeerEntity>> {

        return beers
    }

    fun loadBeers(page:String, perPage:String){
        interactor.getBeersApi(page, perPage) { responseBeerList -> //get data from api
            beers.postValue(responseBeerList)
            beerList = responseBeerList

            if(beerList.size == 0){
                    loadBeersSecondChance()
            }else{
                interactor.saveOnDataBase(responseBeerList)
            }
        }
    }

    private fun loadBeersSecondChance(){
        interactor.getBeersRoom{ responseBeerList -> //get data from dao
            beers.postValue(responseBeerList)
            beerList = responseBeerList
            if(beerList.size >0) {
                status.postValue("Datos obtenidos localmente")
            }
            else{
                status.postValue("No hay conexión al servidor ni datos almacenados")
            }
        }
    }


}