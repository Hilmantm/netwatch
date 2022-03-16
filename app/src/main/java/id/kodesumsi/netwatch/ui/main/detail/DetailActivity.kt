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
import id.kodesumsi.netwatch.ui.main.MainActivity.Companion.getRating
import id.kodesumsi.netwatch.ui.main.MainActivity.Companion.getYear
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
                when (movie) {
                    is Resource.Success -> {
                        Glide.with(this).load(imageResource(movie.data?.posterPath.toString())).into(binding.detailThumb)
                        binding.detailMovieTitle.text = movie.data?.title.toString()
                        binding.overviewYear.text = getYear(movie.data?.releaseDate.toString())
                        binding.overviewDesc.text = movie.data?.overview.toString()
                        binding.overviewVote.text = movie.data?.voteAverage.toString()
                        binding.overviewRating.text = getRating(movie.data?.adult!!)
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