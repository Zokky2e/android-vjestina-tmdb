package agency.five.codebase.android.movieapp.ui.moviedetails

import agency.five.codebase.android.movieapp.data.repository.MovieRepository
import agency.five.codebase.android.movieapp.ui.favorites.FavoritesViewState
import agency.five.codebase.android.movieapp.ui.favorites.mapper.FavoritesMapper
import agency.five.codebase.android.movieapp.ui.moviedetails.mapper.MovieDetailsMapper
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MovieDetailsViewModel(
    private val movieRepository: MovieRepository,
    movieDetailsMapper: MovieDetailsMapper
) : ViewModel() {
    private val _movieDetailsViewState = MutableStateFlow(MovieDetailsViewState())
    val movieDetailsViewState: StateFlow<MovieDetailsViewState> =
        _movieDetailsViewState.asStateFlow()
    private val _mapper = movieDetailsMapper
    init {
        createViewModel(_mapper)
    }
    private fun createViewModel(movieDetailsMapper: MovieDetailsMapper){
        viewModelScope.launch {
            movieRepository.movieDetails(_movieDetailsViewState.value.id)
                .collect { movieDetails ->
                    _movieDetailsViewState.value =
                        movieDetailsMapper.toMovieDetailsViewState(movieDetails)
                }
        }
    }
    fun toggleFavorite(movieId: Int) {
        movieRepository.toggleFavorite(movieId)
        createViewModel(_mapper)
    }
}
