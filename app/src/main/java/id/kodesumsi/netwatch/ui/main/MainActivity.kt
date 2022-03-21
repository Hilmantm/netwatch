package id.kodesumsi.netwatch.ui.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.RecyclerView
import com.google.android.play.core.splitinstall.SplitInstallManagerFactory
import com.google.android.play.core.splitinstall.SplitInstallRequest
import dagger.hilt.android.AndroidEntryPoint
import id.kodesumsi.netwatch.R
import id.kodesumsi.netwatch.base.BaseActivity
import id.kodesumsi.netwatch.databinding.ActivityMainBinding
import java.util.*

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {

    override fun setupViewBinding(): (LayoutInflater) -> ActivityMainBinding = ActivityMainBinding::inflate

    override fun setupViewInstance(savedInstanceState: Bundle?) {
        Log.d("MainActivity", "setupViewInstance: MODULE NAME => ${getString(R.string.favorite).lowercase(Locale.getDefault())}")

        val splitInstallManager = SplitInstallManagerFactory.create(this)
        val request = SplitInstallRequest.newBuilder()
            .addModule(getString(R.string.favorite_module))
            .build()
        splitInstallManager.startInstall(request)
            .addOnSuccessListener {
                Toast.makeText(this, "Success install", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                binding.bnvMain.setOnItemSelectedListener { menuitem ->
                    val id = menuitem.itemId
                    if(id == R.id.home_fragment) {
                        Toast.makeText(this, "Module not found", Toast.LENGTH_SHORT).show()
                        true
                    } else {
                        false
                    }
                }
                Log.e("MainActivity", "splitInstallManager: ${it.message.toString()}", )
            }

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

        fun getRating(adult: Boolean? = false): String {
            if (adult != null) {
                return if (!adult) { "13+" } else { "18+" }
            }
            return "13+"
        }
    }
}