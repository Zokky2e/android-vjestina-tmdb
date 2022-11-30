package agency.five.codebase.android.movieapp.mock

import agency.five.codebase.android.movieapp.ui.home.HomeMovieCategoryViewState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

object FavoritesDBMock {
    private val _favoriteIds = MutableStateFlow(setOf(2,5))
    val favoriteIds: StateFlow<Set<Int>> = _favoriteIds.asStateFlow()
    fun insert(movieId: Int){
        _favoriteIds.value += movieId
    }
    fun delete(movieId: Int){
        _favoriteIds.value -= movieId
    }
}
