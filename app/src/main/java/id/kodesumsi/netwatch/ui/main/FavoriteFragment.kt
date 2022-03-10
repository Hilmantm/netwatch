package id.kodesumsi.netwatch.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dagger.hilt.android.AndroidEntryPoint
import id.kodesumsi.netwatch.R
import id.kodesumsi.netwatch.base.BaseFragment
import id.kodesumsi.netwatch.databinding.FragmentFavoriteBinding

@AndroidEntryPoint
class FavoriteFragment : BaseFragment<FragmentFavoriteBinding>() {

    override fun setupViewBinding(): (LayoutInflater, ViewGroup?, Boolean) -> FragmentFavoriteBinding = FragmentFavoriteBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

}