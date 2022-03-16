package id.kodesumsi.netwatch.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ProgressBar
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import id.kodesumsi.netwatch.R
import id.kodesumsi.netwatch.base.BaseActivity
import id.kodesumsi.netwatch.databinding.ActivityMainBinding

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {

    override fun setupViewBinding(): (LayoutInflater) -> ActivityMainBinding = ActivityMainBinding::inflate

    override fun setupViewInstance(savedInstanceState: Bundle?) {
        val navController = findNavController(R.id.main_fragment_container_view)
        binding.bnvMain.setupWithNavController(navController)
    }

    companion object {
        fun showLoading(pb: ProgressBar, rv: RecyclerView) {
            pb.visibility = View.VISIBLE
            rv.visibility = View.INVISIBLE
        }

        fun showRvContent(pb: ProgressBar, rv: RecyclerView) {
            pb.visibility = View.GONE
            rv.visibility = View.VISIBLE
        }

        fun imageResource(path: String): String {
            return "https://image.tmdb.org/t/p/w500/$path"
        }

        fun getYear(releaseDate: String): String = releaseDate.split("-")[0]

        fun getRating(adult: Boolean = false): String = if (!adult) { "13+" } else { "18+" }
    }
}