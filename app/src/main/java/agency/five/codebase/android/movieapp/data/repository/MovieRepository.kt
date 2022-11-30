package agency.five.codebase.android.movieapp.data.repository

import agency.five.codebase.android.movieapp.model.Movie
import agency.five.codebase.android.movieapp.model.MovieCategory
import agency.five.codebase.android.movieapp.model.MovieDetails
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    fun popularMovies(movieCategory: MovieCategory): Flow<List<Movie>>
    fun nowPlayingMovies(movieCategory: MovieCategory): Flow<List<Movie>>
    fun upcomingMovies(movieCategory: MovieCategory): Flow<List<Movie>>
    fun movieDetails(movieId: Int): Flow<MovieDetails>
    fun favoriteMovies(): Flow<List<Movie>>
    fun addMovieToFavorites(movieId: Int)
    fun removeMovieFromFavorites(movieId: Int)
    fun toggleFavorite(movieId: Int)
}
