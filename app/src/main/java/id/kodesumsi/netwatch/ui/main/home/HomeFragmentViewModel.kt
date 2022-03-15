package id.kodesumsi.netwatch.ui.main.home

import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import id.kodesumsi.netwatch.core.data.source.DataSourceConstant.Companion.NOW_PLAYING
import id.kodesumsi.netwatch.core.data.source.DataSourceConstant.Companion.POPULAR
import id.kodesumsi.netwatch.core.data.source.DataSourceConstant.Companion.TOP_RATED
import id.kodesumsi.netwatch.core.data.source.DataSourceConstant.Companion.UPCOMING
import id.kodesumsi.netwatch.core.domain.usecase.MovieListUseCase
import javax.inject.Inject

@HiltViewModel
class HomeFragmentViewModel @Inject constructor(
    private val moviewUseCase: MovieListUseCase
): ViewModel() {

    val nowPlayingMovies = LiveDataReactiveStreams.fromPublisher(moviewUseCase.getMovieList(NOW_PLAYING))
    val popularMovies = LiveDataReactiveStreams.fromPublisher(moviewUseCase.getMovieList(POPULAR))
    val topRatedMovies = LiveDataReactiveStreams.fromPublisher(moviewUseCase.getMovieList(TOP_RATED))
    val upcomingMovie = LiveDataReactiveStreams.fromPublisher(moviewUseCase.getMovieList(UPCOMING))

}