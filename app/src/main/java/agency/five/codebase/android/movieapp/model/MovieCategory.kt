package agency.five.codebase.android.movieapp.model

import agency.five.codebase.android.movieapp.R
import androidx.annotation.StringRes

enum class MovieCategory(@StringRes val textRes: Int) {
    POPULAR_STREAMING(R.string.popular_streaming),
    POPULAR_ONTV(R.string.popular_ontv),
    POPULAR_RENT(R.string.popular_rent),
    POPULAR_THEATERS(R.string.popular_theatres),
    PLAYING_MOVIES(R.string.playing_movies),
    PLAYING_TV(R.string.playing_tv),
    UPCOMING_TODAY(R.string.upcoming_today),
    UPCOMING_WEEK(R.string.upcoming_week)
}
