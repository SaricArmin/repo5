package com.example.movieappmad24.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.movieappmad24.data.MovieRepository

class DetailViewModelFacotry(private val movieRepository: MovieRepository, private val movieId: String) : ViewModelProvider.Factory {
    //maybe all in one factory?
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return DetailViewModel(movieRepository, movieId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
