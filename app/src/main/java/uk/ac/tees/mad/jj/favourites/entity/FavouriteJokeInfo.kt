package uk.ac.tees.mad.jj.favourites.entity

import androidx.room.Entity


@Entity
data class FavouriteJokeInfo(
    val id: Int,
    val punchline: String,
    val setup: String,
    val type: String
)
