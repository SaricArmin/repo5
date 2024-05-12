package com.example.movieappmad24.screens

import android.util.Log
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.movieappmad24.data.MovieDatabase
import com.example.movieappmad24.data.MovieRepository
import com.example.movieappmad24.navigation.Screen
//import com.example.movieappmad24.viewmodels.MoviesViewModel
import com.example.movieappmad24.viewmodels.WatchListScreenViewModel
import com.example.movieappmad24.viewmodels.WatchListScreenViewModelFactory
import com.example.movieappmad24.widgets.MovieList
import com.example.movieappmad24.widgets.SimpleBottomAppBar
import com.example.movieappmad24.widgets.SimpleTopAppBar

@Composable
fun WatchlistScreen(
    navController: NavController,
){
    val db = MovieDatabase.getDatabase(LocalContext.current, rememberCoroutineScope())
    val repository = MovieRepository(movieDao = db.movieDao())
    val factory = WatchListScreenViewModelFactory(repository)
    val watchListScreenViewModel: WatchListScreenViewModel = viewModel(
        factory = factory)
    Scaffold (
        topBar = {
            SimpleTopAppBar(title = "Your Watchlist")
        },
        bottomBar = {
            SimpleBottomAppBar(
                navController = navController
            )
        }
    ){ innerPadding ->
        // todo: create own viewModel for watchlist
        // collect favoriteMovies accordingly
        val moviesState by watchListScreenViewModel.movies.collectAsState()
        Log.d("MOVIESSTATE watchlist", " ${moviesState}")

        MovieList(
            modifier = Modifier.padding(innerPadding),
            movies = moviesState,
            onFavoriteClick = { movie ->
                watchListScreenViewModel.toggleFavoriteMovie(movie)
            }
        ) { movieId ->
            navController.navigate(Screen.WatchlistScreen.route + "/$movieId")
        }
    }
}