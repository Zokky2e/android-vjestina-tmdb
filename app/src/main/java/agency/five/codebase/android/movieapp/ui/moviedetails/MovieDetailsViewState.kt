package agency.five.codebase.android.movieapp.ui.moviedetails

import agency.five.codebase.android.movieapp.ui.component.ActorCardViewState
import agency.five.codebase.android.movieapp.ui.component.CrewItemViewState

data class MovieDetailsViewState(
    val id: Int = 0,
    val imageUrl: String? = "",
    val voteAverage: Float = 0.4f,
    val title: String = "",
    val overview: String = "",
    val isFavorite: Boolean = false,
    val crew: List<CrewItemViewState> = listOf(),
    val cast: List<ActorCardViewState> = listOf(),
)
