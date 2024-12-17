package uk.ac.tees.mad.jj.request

import retrofit2.http.GET
import uk.ac.tees.mad.jj.model.JokesInfoItem

interface ApiService {

    @GET("jokes/ten")
    suspend fun getJokes(): List<JokesInfoItem>
}