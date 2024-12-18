package uk.ac.tees.mad.jj.request

import retrofit2.http.GET
import uk.ac.tees.mad.jj.model.JokesInfoItem

interface ApiService {

    @GET("jokes/random/150")
    suspend fun getJokes(): List<JokesInfoItem>
}