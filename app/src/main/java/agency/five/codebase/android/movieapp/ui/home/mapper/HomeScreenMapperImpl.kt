package agency.five.codebase.android.movieapp.ui.home.mapper

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
    ) = HomeMovieCategoryViewState(
        movieCategories = movieCategories.map {
            MovieCategoryLabelViewState(
                it.ordinal,
                selectedMovieCategory.ordinal == it.ordinal,
                MovieCategoryLabelTextViewState.TextRes(it.textRes)
            )
        },
        movies = movies.map {
            MovieCardViewState(
                it.id,
                it.isFavorite,
                it.imageUrl
            )
        }
    )
}
