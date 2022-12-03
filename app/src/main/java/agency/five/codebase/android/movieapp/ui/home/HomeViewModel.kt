package agency.five.codebase.android.movieapp.ui.home

import agency.five.codebase.android.movieapp.data.repository.MovieRepository
import agency.five.codebase.android.movieapp.model.MovieCategory
import agency.five.codebase.android.movieapp.ui.home.mapper.HomeScreenMapper
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.launch

class HomeViewModel(
    private val movieRepository: MovieRepository,
    homeScreenMapper: HomeScreenMapper,
) : ViewModel() {
    private val _popularSelectedCategory = MutableStateFlow(MovieCategory.POPULAR_STREAMING)
    private val _nowPlayingSelectedCategory = MutableStateFlow(MovieCategory.PLAYING_MOVIES)
    private val _upcomingSelectedCategory = MutableStateFlow(MovieCategory.UPCOMING_TODAY)

    private val _popularCategoryViewState = MutableStateFlow(HomeMovieCategoryViewState())
    val popularCategoryViewState: StateFlow<HomeMovieCategoryViewState> =
        _popularCategoryViewState.asStateFlow()
    private val _nowPlayingCategoryViewState = MutableStateFlow(HomeMovieCategoryViewState())
    val nowPlayingCategoryViewState: StateFlow<HomeMovieCategoryViewState> =
        _nowPlayingCategoryViewState.asStateFlow()
    private val _upcomingCategoryViewState = MutableStateFlow(HomeMovieCategoryViewState())
    val upcomingCategoryViewState: StateFlow<HomeMovieCategoryViewState> =
        _upcomingCategoryViewState.asStateFlow()
    private val _mapper = homeScreenMapper

    init {
        createViewState(_mapper)
    }

    private fun createViewState(homeScreenMapper: HomeScreenMapper) {
        viewModelScope.launch {
            movieRepository.popularMovies(MovieCategory.POPULAR_STREAMING)
                .collect { movies ->
                    _popularCategoryViewState.value =
                        homeScreenMapper.toHomeMovieCategoryViewState(
                            listOf(
                                MovieCategory.POPULAR_STREAMING,
                                MovieCategory.POPULAR_ONTV,
                                MovieCategory.POPULAR_RENT,
                                MovieCategory.POPULAR_THEATERS
                            ),
                            _popularSelectedCategory.value,
                            movies
                        )
                }
        }
        viewModelScope.launch {
            movieRepository.nowPlayingMovies(MovieCategory.PLAYING_MOVIES)
                .collect { movies ->
                    _nowPlayingCategoryViewState.value =
                        homeScreenMapper.toHomeMovieCategoryViewState(
                            listOf(
                                MovieCategory.PLAYING_MOVIES,
                                MovieCategory.PLAYING_TV,
                            ),
                            _nowPlayingSelectedCategory.value,
                            movies
                        )
                }
        }
        viewModelScope.launch {
            movieRepository.upcomingMovies(MovieCategory.UPCOMING_TODAY)
                .collect { movies ->
                    _upcomingCategoryViewState.value =
                        homeScreenMapper.toHomeMovieCategoryViewState(
                            listOf(
                                MovieCategory.UPCOMING_TODAY,
                                MovieCategory.UPCOMING_WEEK,
                            ),
                            _upcomingSelectedCategory.value,
                            movies
                        )
                }
        }
    }

    fun toggleFavorite(movieId: Int) {
        viewModelScope.launch {
            movieRepository.toggleFavorite(movieId)
        }
    }

    fun changeCategory(
        category: Int
    ) {
        when (val tempCategory = MovieCategory.values()[category]) {
            MovieCategory.POPULAR_STREAMING,
            MovieCategory.POPULAR_RENT,
            MovieCategory.POPULAR_ONTV,
            MovieCategory.POPULAR_THEATERS
            -> {
                _popularCategoryViewState.mapLatest { homeMovies ->
                    homeMovies.movieCategories.map {
                        it.copy(isSelected = it.itemId == tempCategory.ordinal)
                    }
                }
                _popularSelectedCategory.value = tempCategory
                createViewState(_mapper)
            }
            MovieCategory.PLAYING_MOVIES,
            MovieCategory.PLAYING_TV -> {
                _nowPlayingCategoryViewState.mapLatest { homeMovies ->
                    homeMovies.movieCategories.map {
                        it.copy(isSelected = it.itemId == tempCategory.ordinal)
                    }
                }
                _nowPlayingSelectedCategory.value = tempCategory
                createViewState(_mapper)
            }
            MovieCategory.UPCOMING_TODAY,
            MovieCategory.UPCOMING_WEEK
            -> {
                _upcomingCategoryViewState.mapLatest { homeMovies ->
                    homeMovies.movieCategories.map {
                        it.copy(isSelected = it.itemId == tempCategory.ordinal)
                    }
                }
                _upcomingSelectedCategory.value = tempCategory
                createViewState(_mapper)
            }
        }
    }
}
