package com.example.movieappmad24.screens

import HomeScreenViewModel
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
import com.example.movieappmad24.viewmodels.HomeScreenViewModelFactory
import com.example.movieappmad24.widgets.MovieList
import com.example.movieappmad24.widgets.SimpleBottomAppBar
import com.example.movieappmad24.widgets.SimpleTopAppBar

@Composable
fun HomeScreen(navController: NavController) {
    val db = MovieDatabase.getDatabase(LocalContext.current, rememberCoroutineScope())
    val repository = MovieRepository(movieDao = db.movieDao())
    val factory = HomeScreenViewModelFactory(repository)
    val homeViewModel: HomeScreenViewModel = viewModel(factory = factory)

    Scaffold(
        topBar = {
            SimpleTopAppBar(title = "Movie App")
        },
        bottomBar = {
            SimpleBottomAppBar(navController = navController)
        }
    ) { innerPadding ->
        val moviesState by homeViewModel.movies.collectAsState()
        Log.d("MOVIESSTATE", " ${moviesState}")

        MovieList(
            modifier = Modifier.padding(innerPadding),
            movies = moviesState,
            onFavoriteClick = { movie ->
                homeViewModel.toggleFavoriteMovie(movie)
            }
        ) { movieId ->
            navController.navigate(Screen.DetailScreen.withId(movieId))
        }
    }
}






