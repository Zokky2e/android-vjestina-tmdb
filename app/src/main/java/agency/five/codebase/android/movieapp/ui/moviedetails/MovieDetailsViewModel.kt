package agency.five.codebase.android.movieapp.ui.moviedetails

import agency.five.codebase.android.movieapp.data.repository.MovieRepository
import agency.five.codebase.android.movieapp.model.MovieDetails
import agency.five.codebase.android.movieapp.ui.favorites.FavoritesViewState
import agency.five.codebase.android.movieapp.ui.favorites.mapper.FavoritesMapper
import agency.five.codebase.android.movieapp.ui.moviedetails.mapper.MovieDetailsMapper
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class MovieDetailsViewModel(
    movieId: Int,
    private val movieRepository: MovieRepository,
    movieDetailsMapper: MovieDetailsMapper
) : ViewModel() {
    private val _movieDetailsViewState = MutableStateFlow(MovieDetailsViewState())
    val movieDetailsViewState: StateFlow<MovieDetailsViewState> =
        _movieDetailsViewState.asStateFlow()

    init {
        viewModelScope.launch {
             movieRepository.movieDetails(movieId)
                .map(movieDetailsMapper::toMovieDetailsViewState)
                .stateIn(
                    scope = viewModelScope,
                    started = SharingStarted.WhileSubscribed(5_000),
                    initialValue = MovieDetailsViewState()
                )
                 .collect {
                     _movieDetailsViewState.value = it
                 }
        }
    }

    fun toggleFavorite(movieId: Int) {
        viewModelScope.launch {
            movieRepository.toggleFavorite(movieId)
        }
    }
}
