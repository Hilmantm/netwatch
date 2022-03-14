package id.kodesumsi.netwatch.ui.main.home

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import id.kodesumsi.netwatch.core.domain.usecase.GetMovieListUseCase
import javax.inject.Inject

@HiltViewModel
class HomeFragmentViewModel @Inject constructor(
    private val moviewUseCase: GetMovieListUseCase
): ViewModel() {

    fun getMovieList(category: String) {
        moviewUseCase.getMovieList(category)
    }

}