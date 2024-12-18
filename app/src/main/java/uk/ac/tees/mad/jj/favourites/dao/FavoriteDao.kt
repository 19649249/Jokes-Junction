package uk.ac.tees.mad.jj.favourites.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import uk.ac.tees.mad.jj.favourites.entity.FavouriteJokeInfo


@Dao
interface FavoriteDao {

    @Upsert
    suspend fun addJoke(joke: FavouriteJokeInfo)

    @Delete
    suspend fun deleteJoke(joke: FavouriteJokeInfo)

    @Query("SELECT * FROM favouritejokeinfo")
    suspend fun allFavouriteJokes(): List<FavouriteJokeInfo>

}