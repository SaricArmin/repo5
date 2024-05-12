import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieappmad24.data.MovieRepository
import com.example.movieappmad24.models.Movie
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import android.util.Log
import com.example.movieappmad24.models.MovieWithImages
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged

class HomeScreenViewModel(private val movieRepository: MovieRepository) : ViewModel() {
    private val _movies = MutableStateFlow(listOf<MovieWithImages>())
    val movies: StateFlow<List<MovieWithImages>> = _movies.asStateFlow()

    init {
        viewModelScope.launch {
            movieRepository.getAllMovies().distinctUntilChanged().collect { moviesList ->
                if (moviesList.isEmpty()) {
                    Log.d("HomeScreenViewModel", "No movies fetched")
                } else {
                    Log.d("HomeScreenViewModel", "Movies fetched: ${moviesList.size}")
                    _movies.value = moviesList
                    Log.d("HomeScreenViewModel", "Movies displayed: ${_movies}")
                }
            }
        }
    }

    fun toggleFavoriteMovie(movieWithImages: Movie) {
        movieWithImages.isFavorite = !movieWithImages.isFavorite

        viewModelScope.launch {
            movieRepository.updateMovie(movieWithImages)
            Log.d("HomeScreenViewModel", "Favorite status toggled for: ${movieWithImages.title}")

        }
    }
}
