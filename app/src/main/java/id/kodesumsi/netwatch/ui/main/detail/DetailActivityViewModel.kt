package id.kodesumsi.netwatch.ui.main.detail

import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import id.kodesumsi.netwatch.core.domain.usecase.MovieListUseCase
import javax.inject.Inject

@HiltViewModel
class DetailActivityViewModel @Inject constructor(
    private val movieUseCase: MovieListUseCase
) : ViewModel() {

    fun getDetailMovie(id: Int) = LiveDataReactiveStreams.fromPublisher(movieUseCase.getMovieDetail(id))

}