package id.kodesumsi.netwatch.ui.main.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.activity.viewModels
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import id.kodesumsi.netwatch.base.BaseActivity
import id.kodesumsi.netwatch.core.data.source.Resource
import id.kodesumsi.netwatch.databinding.ActivityDetailBinding
import id.kodesumsi.netwatch.ui.main.MainActivity
import id.kodesumsi.netwatch.ui.main.MainActivity.Companion.imageResource

@AndroidEntryPoint
class DetailActivity : BaseActivity<ActivityDetailBinding>() {

    private val viewModel: DetailActivityViewModel by viewModels()

    override fun setupViewBinding(): (LayoutInflater) -> ActivityDetailBinding {
        return ActivityDetailBinding::inflate
    }

    override fun setupViewInstance(savedInstanceState: Bundle?) {
        val movieId = intent.getIntExtra(MOVIE_ID, 0)

        Log.d("DETAIL ACTIVITY", "Movie Id = ${movieId}")

        binding.btnDetailBack.setOnClickListener {
            onBackPressed()
        }

        viewModel.getDetailMovie(movieId).observe(this) { movie ->
            if (movie != null) {
                Log.d("DETAIL ACTIVITY", "data = ${movie.data}")
                when (movie) {
                    is Resource.Success -> {
                        Log.d("DETAIL ACTIVITY", "title = ${movie.data?.title}, release date = ${movie.data?.releaseDate}, year = ${movie.data?.releaseDate!!.split("-")[0]} , desc = ${movie.data?.overview}")
                        Glide.with(this).load(imageResource(movie.data?.posterPath.toString())).into(binding.detailThumb)
                        binding.detailMovieTitle.text = movie.data?.title.toString()
                        binding.overviewYear.text = movie.data?.releaseDate!!.split("-")[0]
                        binding.overviewDesc.text = movie.data?.overview.toString()
                    }
                    is Resource.Error -> {
                        Log.e("DETAIL ACTIVITY", "resource error: ${movie.message.toString()}", )
                    }
                }
            }
        }
    }

    companion object {
        const val MOVIE_ID = "movie_id"
    }
}