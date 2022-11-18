package agency.five.codebase.android.movieapp.ui.home.mapper

import agency.five.codebase.android.movieapp.R
import agency.five.codebase.android.movieapp.model.Movie
import agency.five.codebase.android.movieapp.model.MovieCategory
import agency.five.codebase.android.movieapp.ui.component.MovieCardViewState
import agency.five.codebase.android.movieapp.ui.component.MovieCategoryLabelTextViewState
import agency.five.codebase.android.movieapp.ui.component.MovieCategoryLabelViewState
import agency.five.codebase.android.movieapp.ui.home.HomeMovieCategoryViewState

class HomeScreenMapperImpl : HomeScreenMapper {

    override fun toHomeMovieCategoryViewState(
        movieCategories: List<MovieCategory>,
        selectedMovieCategory: MovieCategory,
        movies: List<Movie>
    ): HomeMovieCategoryViewState {
        val movieCategoryLabelViewStates = mutableListOf<MovieCategoryLabelViewState>()
        val homeMovieViewStates = mutableListOf<MovieCardViewState>()
        for (index in movieCategories.indices) {
            val movieCategoryLabelViewState = MovieCategoryLabelViewState(
                itemId = movieCategories[index].ordinal,
                isSelected = movieCategories[index].ordinal == selectedMovieCategory.ordinal,
                categoryText =
                MovieCategoryLabelTextViewState.TextRes(movieCategories[index].textRes)
            )
            movieCategoryLabelViewStates += movieCategoryLabelViewState
        }
        for (movie in movies) {
            val homeMovieViewState = MovieCardViewState(
                id = movie.id,
                imageUrl = movie.imageUrl,
                isFavorite = movie.isFavorite
            )
            homeMovieViewStates += homeMovieViewState
        }

        return HomeMovieCategoryViewState(movieCategoryLabelViewStates, homeMovieViewStates)

    }

}
