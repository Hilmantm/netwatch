package id.kodesumsi.netwatch.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import id.kodesumsi.netwatch.core.domain.usecase.MovieListUseCase

class ViewModelFactory(private val movieUseCase: MovieListUseCase): ViewModelProvider.Factory {

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null

        fun getInstance(movieUseCase: MovieListUseCase): ViewModelFactory =
            instance
                ?: synchronized(this) {
                    instance ?: ViewModelFactory(movieUseCase)
                }
    }

    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        when {
            modelClass.isAssignableFrom(FavoriteFragmentViewModel::class.java) -> {
                FavoriteFragmentViewModel(movieUseCase) as T
            }
            else -> throw Throwable("Unknown ViewModel class: " + modelClass.name)
    }
}