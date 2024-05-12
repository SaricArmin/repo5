package com.example.movieappmad24.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.movieappmad24.models.Movie
import com.example.movieappmad24.models.MovieImage
import com.example.movieappmad24.models.MovieWithImages
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {
    @Insert
    suspend fun add(movie: Movie)

    @Insert
    suspend fun addAll(movies: List<Movie>)

    @Insert
    suspend fun addImages(images: List<MovieImage>)

    @Update
    suspend fun update(movie: Movie)

    @Delete
    suspend fun delete(movie: Movie)

    @Transaction
    @Query("SELECT * from movie where id = :movieId")
    fun get(movieId: String): Flow<MovieWithImages>//should be moviewithimages

    @Transaction
    @Query("SELECT * from movie")
    fun getAll(): Flow<List<MovieWithImages>>//should be moviewithimages

    @Query("SELECT * from movie where isFavorite= 1")
    fun getFavorites(): Flow<List<MovieWithImages>> //should be moviewithimages


}
