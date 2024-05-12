package com.example.movieappmad24.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieappmad24.data.MovieRepository
import com.example.movieappmad24.models.Movie
import com.example.movieappmad24.models.MovieWithImages
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class WatchListScreenViewModel(private val movieRepository: MovieRepository) : ViewModel() {
    private val _movies = MutableStateFlow(listOf<MovieWithImages>())
    val movies: StateFlow<List<MovieWithImages>> = _movies.asStateFlow()

    init {
        viewModelScope.launch {
            movieRepository.getFavoriteMovies().collect { favoriteMoviesList ->
                _movies.value = favoriteMoviesList
                Log.d("WatchListScreenViewModel", "Favorite movies displayed: ${_movies.value.size}")
            }
        }
    }
    fun toggleFavoriteMovie(movieWithImages: Movie) {
        movieWithImages.isFavorite = !movieWithImages.isFavorite

        viewModelScope.launch {
            movieRepository.updateMovie(movieWithImages)
        }
    }
}
