package agency.five.codebase.android.movieapp.ui.moviedetails.mapper

import agency.five.codebase.android.movieapp.model.Actor
import agency.five.codebase.android.movieapp.model.Crewman
import agency.five.codebase.android.movieapp.model.MovieDetails
import agency.five.codebase.android.movieapp.ui.component.ActorCardViewState
import agency.five.codebase.android.movieapp.ui.component.CrewItemViewState
import agency.five.codebase.android.movieapp.ui.component.MovieCardViewState
import agency.five.codebase.android.movieapp.ui.favorites.FavoritesViewState
import agency.five.codebase.android.movieapp.ui.moviedetails.MovieDetailsViewState

class MovieDetailsMapperImpl : MovieDetailsMapper {

    override fun toMovieDetailsViewState(movieDetails: MovieDetails): MovieDetailsViewState {
        return MovieDetailsViewState(
            id = movieDetails.movie.id,
            imageUrl = movieDetails.movie.imageUrl,
            voteAverage = movieDetails.voteAverage,
            title = movieDetails.movie.title,
            overview = movieDetails.movie.overview,
            isFavorite = movieDetails.movie.isFavorite,
            crew = toCrewItemViewState(movieDetails.crew),
            cast = toActorCardViewState(movieDetails.cast)
        )
    }

    private fun toActorCardViewState(cast: List<Actor>): List<ActorCardViewState> {
        val actorCardViewStates = mutableListOf<ActorCardViewState>()
        for (actor in cast) {
            val actorCardViewState = ActorCardViewState(
                actor.id,
                actor.imageUrl,
                actor.name,
                actor.character,
            )
            actorCardViewStates += actorCardViewState
        }
        return actorCardViewStates
    }

    private fun toCrewItemViewState(crew: List<Crewman>): List<CrewItemViewState> {
        val crewItemViewStates = mutableListOf<CrewItemViewState>()
        for (person in crew) {
            val crewItemViewState = CrewItemViewState(
                person.id,
                person.name,
                person.job,
            )
            crewItemViewStates += crewItemViewState
        }
        return crewItemViewStates
    }
}
