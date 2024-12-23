package uk.ac.tees.mad.jj.favourites.entity

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class FavouriteJokeInfo(
    @PrimaryKey
    val id: Int,
    val punchline: String,
    val setup: String,
    val type: String
)
