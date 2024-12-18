package uk.ac.tees.mad.jj.favourites

import androidx.room.Database
import androidx.room.RoomDatabase
import uk.ac.tees.mad.jj.favourites.dao.FavoriteDao
import uk.ac.tees.mad.jj.favourites.entity.FavouriteJokeInfo


@Database(
    entities = [FavouriteJokeInfo::class],
    version = 1
)
abstract class FavouriteDatabase: RoomDatabase() {

    abstract fun favDao(): FavoriteDao
}