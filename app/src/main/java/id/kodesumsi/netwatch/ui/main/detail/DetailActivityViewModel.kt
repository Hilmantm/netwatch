package id.kodesumsi.netwatch.ui.main.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import id.kodesumsi.netwatch.core.domain.model.Movie
import id.kodesumsi.netwatch.core.domain.usecase.MovieListUseCase
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class DetailActivityViewModel @Inject constructor(
    private val movieUseCase: MovieListUseCase
) : ViewModel() {

    val isFav: MutableLiveData<Movie> = MutableLiveData()
    fun getDetailMovie(id: Int) = LiveDataReactiveStreams.fromPublisher(movieUseCase.getMovieDetail(id))

    fun isFavorite(id: Int) {
        movieUseCase.isFavorite(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ movie ->
                isFav.postValue(movie)
            }, {
                if (it is NoSuchElementException) {
                    isFav.postValue(Movie())
                } else {
                    Log.d("DetailActivityViewModel", "error : ${it.message.toString()}")
                }
            })
    }

}