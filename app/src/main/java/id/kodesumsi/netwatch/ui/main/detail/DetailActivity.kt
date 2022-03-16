package id.kodesumsi.netwatch.ui.main.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import dagger.hilt.android.AndroidEntryPoint
import id.kodesumsi.netwatch.base.BaseActivity
import id.kodesumsi.netwatch.databinding.ActivityDetailBinding

@AndroidEntryPoint
class DetailActivity : BaseActivity<ActivityDetailBinding>() {


    override fun setupViewBinding(): (LayoutInflater) -> ActivityDetailBinding {
        return ActivityDetailBinding::inflate
    }

    override fun setupViewInstance(savedInstanceState: Bundle?) {

    }
}