package agency.five.codebase.android.movieapp


import agency.five.codebase.android.movieapp.data.di.dataModule
import agency.five.codebase.android.movieapp.ui.favorites.FavoritesViewModel
import agency.five.codebase.android.movieapp.ui.favorites.di.favoriteModule
import agency.five.codebase.android.movieapp.ui.home.di.homeModule
import agency.five.codebase.android.movieapp.ui.moviedetails.di.movieDetailsModule
import android.app.Application
import android.util.Log
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.GlobalContext.startKoin
import org.koin.dsl.module

class MovieApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MovieApp)
            modules(
                dataModule,
                favoriteModule,
                homeModule,
                movieDetailsModule
            )
        }
        Log.d("MovieApp", "App started")
    }
}
