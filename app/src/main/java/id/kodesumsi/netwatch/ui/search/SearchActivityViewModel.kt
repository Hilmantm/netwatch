package id.kodesumsi.netwatch.ui.search

import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import id.kodesumsi.netwatch.core.domain.usecase.MovieListUseCase
import javax.inject.Inject

@HiltViewModel
class SearchActivityViewModel @Inject constructor(
    private val movieUseCase: MovieListUseCase
): ViewModel() {

    fun searchMovieList(query: String) = LiveDataReactiveStreams.fromPublisher(movieUseCase.searchMovieList(query))

}