package id.kodesumsi.netwatch.ui.main.detail

import android.util.Log
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
    val favBtnActive: MutableLiveData<Boolean> = MutableLiveData()

//    fun isFav(): LiveData<Movie> = _isFav
//    fun favBtnActive(): LiveData<Boolean> = _favBtnActive

    fun getDetailMovie(id: Int) = LiveDataReactiveStreams.fromPublisher(movieUseCase.getMovieDetail(id))
    fun insertFavoriteMovie(movie: Movie) = movieUseCase.insertFavoriteMovie(movie)
    fun removeFavoriteMovie(movie: Movie) = movieUseCase.removeFavoriteMovie(movie)

    fun isFavorite(id: Int) {
        movieUseCase.isFavorite(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnEvent { movie, error ->
                if(movie == null && error == null) {
                    isFav.postValue(Movie())
                    favBtnActive.postValue(true)
                } else {
                    favBtnActive.postValue(false)
                }
            }
            .subscribe({ movie ->
                Log.d("DetailActivityViewModel", "movie: $movie")
                isFav.postValue(movie)
                favBtnActive.postValue(false)
            }, {
                Log.d("DetailActivityViewModel", "error : ${it.message.toString()}")
            })
    }

}