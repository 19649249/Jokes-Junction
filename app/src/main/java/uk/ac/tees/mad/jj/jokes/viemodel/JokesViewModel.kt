package uk.ac.tees.mad.jj.jokes.viemodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import uk.ac.tees.mad.jj.favourites.FavouriteDatabase
import uk.ac.tees.mad.jj.model.JokesInfoItem
import uk.ac.tees.mad.jj.request.ApiService
import javax.inject.Inject


@HiltViewModel
class JokesViewModel @Inject constructor(
    private val api: ApiService,
    application: Application
): ViewModel() {

    private val fdb = Room.databaseBuilder(
        application,
        FavouriteDatabase::class.java,
        "favourite.db"
    ).build()

    private val favourite = fdb.favDao()

    private val _jokesList = MutableStateFlow<List<JokesInfoItem>>(emptyList())
    val jokesList = _jokesList.asStateFlow()

    init {
        fetchJokesApi()
    }

    fun fetchJokesApi(){
        viewModelScope.launch {
            try {
                val fetchedJoke = api.getJokes()
                _jokesList.value += fetchedJoke
            }catch (e: Exception){
                Log.i("The Data: ", "Error in fetching the data from the api ${e.message}")
            }
        }
    }

    fun addFavourite(){
        
    }

}