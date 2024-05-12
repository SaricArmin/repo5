package com.example.movieappmad24.models


import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movieImages")
data class MovieImage(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val movieId: String,
    val url: String
)