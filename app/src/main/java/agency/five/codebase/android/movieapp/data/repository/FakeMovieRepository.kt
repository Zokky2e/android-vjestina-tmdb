package agency.five.codebase.android.movieapp.data.repository

import agency.five.codebase.android.movieapp.mock.FavoritesDBMock
import agency.five.codebase.android.movieapp.mock.MoviesMock
import agency.five.codebase.android.movieapp.model.Movie
import agency.five.codebase.android.movieapp.model.MovieCategory
import agency.five.codebase.android.movieapp.model.MovieDetails
import androidx.coordinatorlayout.widget.CoordinatorLayout.DispatchChangeEvent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext

class FakeMovieRepository(
    private val ioDispatcher: CoroutineDispatcher,
) : MovieRepository {
    private val fakeMovies = MoviesMock.getMoviesList().toMutableList()
    private val movies: Flow<List<Movie>> = FavoritesDBMock.favoriteIds
        .mapLatest { favoriteIds ->
            fakeMovies.map { movie ->
                movie.copy(isFavorite = favoriteIds.contains(movie.id))
            }
        }
        .flowOn(ioDispatcher)

    override fun popularMovies(movieCategory: MovieCategory) = movies
    override fun nowPlayingMovies(movieCategory: MovieCategory) = movies
    override fun upcomingMovies(movieCategory: MovieCategory) = movies
    override fun movieDetails(movieId: Int): Flow<MovieDetails> =
        FavoritesDBMock.favoriteIds
            .mapLatest { favoriteIds ->
                val movieDetails = MoviesMock.getMovieDetails(movieId)
                val tempMovie =
                    movieDetails.movie.copy(isFavorite = favoriteIds.contains(movieId))
                movieDetails.copy(movie = tempMovie)
            }
            .flowOn(ioDispatcher)

    override fun favoriteMovies(): Flow<List<Movie>> = movies.mapLatest {
        it.filter { fakeMovie -> fakeMovie.isFavorite }
    }

    override suspend fun addMovieToFavorites(movieId: Int) {
        return withContext(Dispatchers.IO){
            FavoritesDBMock.insert(movieId)
        }
    }

    override suspend fun removeMovieFromFavorites(movieId: Int) {
        return withContext(Dispatchers.IO){
        FavoritesDBMock.delete(movieId)
        }
    }

    override suspend fun toggleFavorite(movieId: Int) =
        if (FavoritesDBMock.favoriteIds.value.contains(movieId)) {
            removeMovieFromFavorites(movieId)
        } else {
            addMovieToFavorites(movieId)
        }
}
