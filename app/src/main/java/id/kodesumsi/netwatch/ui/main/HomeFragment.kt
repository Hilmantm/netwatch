package id.kodesumsi.netwatch.ui.main

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dagger.hilt.android.AndroidEntryPoint
import id.kodesumsi.netwatch.R
import id.kodesumsi.netwatch.base.BaseFragment
import id.kodesumsi.netwatch.databinding.FragmentHomeBinding
import id.kodesumsi.netwatch.ui.search.SearchActivity

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    override fun setupViewBinding(): (LayoutInflater, ViewGroup?, Boolean) -> FragmentHomeBinding = FragmentHomeBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.componentInputSearch.edtSearch.inputType = InputType.TYPE_NULL
        binding.componentInputSearch.edtSearch.setOnClickListener {
            val toSearchActivity = Intent(requireActivity(), SearchActivity::class.java)
            activity?.startActivity(toSearchActivity)
        }
    }


}