package id.kodesumsi.netwatch.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import dagger.hilt.android.AndroidEntryPoint
import id.kodesumsi.netwatch.R
import id.kodesumsi.netwatch.base.BaseActivity
import id.kodesumsi.netwatch.databinding.ActivityMainBinding

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {

    override fun setupViewBinding(): (LayoutInflater) -> ActivityMainBinding = ActivityMainBinding::inflate

    override fun setupViewInstance(savedInstanceState: Bundle?) {

    }
}