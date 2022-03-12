package id.kodesumsi.netwatch.ui.search

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import dagger.hilt.android.AndroidEntryPoint
import id.kodesumsi.netwatch.base.BaseActivity
import id.kodesumsi.netwatch.databinding.ActivitySearchBinding

@AndroidEntryPoint
class SearchActivity : BaseActivity<ActivitySearchBinding>() {
    override fun setupViewBinding(): (LayoutInflater) -> ActivitySearchBinding = ActivitySearchBinding::inflate

    override fun setupViewInstance(savedInstanceState: Bundle?) {

    }

}