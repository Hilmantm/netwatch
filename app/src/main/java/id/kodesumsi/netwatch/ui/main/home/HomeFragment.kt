package id.kodesumsi.netwatch.ui.main.home

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import id.kodesumsi.netwatch.base.BaseAdapter
import id.kodesumsi.netwatch.base.BaseFragment
import id.kodesumsi.netwatch.core.data.source.Resource
import id.kodesumsi.netwatch.core.domain.model.Movie
import id.kodesumsi.netwatch.databinding.FragmentHomeBinding
import id.kodesumsi.netwatch.databinding.ItemMovieShowBinding
import id.kodesumsi.netwatch.ui.main.MainActivity
import id.kodesumsi.netwatch.ui.main.MainActivity.Companion.imageResource
import id.kodesumsi.netwatch.ui.main.MainActivity.Companion.showLoading
import id.kodesumsi.netwatch.ui.main.MainActivity.Companion.showRvContent
import id.kodesumsi.netwatch.ui.search.SearchActivity

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    private lateinit var popularMoviesAdapter: BaseAdapter<ItemMovieShowBinding, Movie>

    private val viewModel: HomeFragmentViewModel by viewModels()

    override fun setupViewBinding(): (LayoutInflater, ViewGroup?, Boolean) -> FragmentHomeBinding = FragmentHomeBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.componentInputSearch.edtSearch.inputType = InputType.TYPE_NULL
        binding.componentInputSearch.edtSearch.setOnClickListener {
            val toSearchActivity = Intent(requireActivity(), SearchActivity::class.java)
            activity?.startActivity(toSearchActivity)
        }
        viewModel.popularMovies.observe(viewLifecycleOwner) { movies ->
            if (movies != null) {
                when(movies) {
                    is Resource.Loading -> showLoading(binding.testing1.pbMovieShow, binding.testing1.rvMovieShow)
                    is Resource.Success -> {
                        popularMoviesAdapter = BaseAdapter(movies.data!!, ItemMovieShowBinding::inflate) { item, popularBinding ->
                            Glide.with(this).load(imageResource(item.posterPath.toString())).into(popularBinding.itemThumb)
                        }
                        binding.testing1.rvMovieShow.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                        binding.testing1.rvMovieShow.adapter = popularMoviesAdapter
                        showRvContent(binding.testing1.pbMovieShow, binding.testing1.rvMovieShow)
                    }
                }
            }
        }
    }


}