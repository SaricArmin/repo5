package com.example.movieappmad24.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieappmad24.data.MovieRepository
import com.example.movieappmad24.models.Movie
import com.example.movieappmad24.models.MovieWithImages
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DetailViewModel(private val movieRepository: MovieRepository, private val movieId: String) : ViewModel() {
    private val _movies = MutableStateFlow(listOf<MovieWithImages>())
    val movies: StateFlow<List<MovieWithImages>> = _movies.asStateFlow()

    init {
        viewModelScope.launch {
            movieRepository.getAllMovies().collect { movieItem ->
                _movies.value = movieItem
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

