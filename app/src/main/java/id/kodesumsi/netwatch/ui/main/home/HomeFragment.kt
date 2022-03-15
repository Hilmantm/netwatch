package id.kodesumsi.netwatch.ui.main.home

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewbinding.ViewBinding
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import id.kodesumsi.netwatch.R
import id.kodesumsi.netwatch.base.BaseAdapter
import id.kodesumsi.netwatch.base.BaseBottomSheet
import id.kodesumsi.netwatch.base.BaseFragment
import id.kodesumsi.netwatch.core.data.source.Resource
import id.kodesumsi.netwatch.core.data.source.network.NetworkConstant.Companion.NOW_PLAYING
import id.kodesumsi.netwatch.core.data.source.network.NetworkConstant.Companion.POPULAR
import id.kodesumsi.netwatch.core.data.source.network.NetworkConstant.Companion.TOP_RATED
import id.kodesumsi.netwatch.core.data.source.network.NetworkConstant.Companion.UPCOMING
import id.kodesumsi.netwatch.core.domain.model.Movie
import id.kodesumsi.netwatch.databinding.ComponentBottomSheetOverviewBinding
import id.kodesumsi.netwatch.databinding.ComponentMovieShowListBinding
import id.kodesumsi.netwatch.databinding.FragmentHomeBinding
import id.kodesumsi.netwatch.databinding.ItemMovieShowBinding
import id.kodesumsi.netwatch.ui.main.MainActivity.Companion.imageResource
import id.kodesumsi.netwatch.ui.main.MainActivity.Companion.showLoading
import id.kodesumsi.netwatch.ui.main.MainActivity.Companion.showRvContent
import id.kodesumsi.netwatch.ui.search.SearchActivity

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    private lateinit var popularMoviesAdapter: BaseAdapter<ItemMovieShowBinding, Movie>

    private val viewModel: HomeFragmentViewModel by viewModels()
    private val listOfMovies: MutableMap<String, ViewBinding> = mutableMapOf()

    override fun setupViewBinding(): (LayoutInflater, ViewGroup?, Boolean) -> FragmentHomeBinding = FragmentHomeBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.componentInputSearch.edtSearch.inputType = InputType.TYPE_NULL
        binding.componentInputSearch.edtSearch.setOnClickListener {
            val toSearchActivity = Intent(requireActivity(), SearchActivity::class.java)
            activity?.startActivity(toSearchActivity)
        }

        setUpMovieList()

        viewModel.nowPlayingMovies.observe(viewLifecycleOwner) { movies ->
            val view: ComponentMovieShowListBinding = listOfMovies[NOW_PLAYING] as ComponentMovieShowListBinding
            setMoviesRosource(movies = movies, view = view)
        }

        viewModel.topRatedMovies.observe(viewLifecycleOwner) { movies ->
            val view: ComponentMovieShowListBinding = listOfMovies[TOP_RATED] as ComponentMovieShowListBinding
            setMoviesRosource(movies = movies, view = view)
        }

        viewModel.popularMovies.observe(viewLifecycleOwner) { movies ->
            val view: ComponentMovieShowListBinding = listOfMovies[POPULAR] as ComponentMovieShowListBinding
            setMoviesRosource(movies = movies, view = view)
        }

        viewModel.upcomingMovie.observe(viewLifecycleOwner) { movies ->
            val view: ComponentMovieShowListBinding = listOfMovies[UPCOMING] as ComponentMovieShowListBinding
            setMoviesRosource(movies = movies, view = view)
        }
    }

    private fun setMoviesRosource(movies: Resource<List<Movie>>, view: ComponentMovieShowListBinding) {
        when(movies) {
            is Resource.Loading -> showLoading(view.pbMovieShow, view.rvMovieShow)
            is Resource.Success -> {
                popularMoviesAdapter = BaseAdapter(movies.data!!, ItemMovieShowBinding::inflate) { item, itemRvBinding ->
                    Glide.with(this).load(imageResource(item.posterPath.toString())).into(itemRvBinding.itemThumb)
                    itemRvBinding.root.setOnClickListener {
                        val overviewBottomSheet = BaseBottomSheet(ComponentBottomSheetOverviewBinding::inflate) { bottomSheetBinding, _, context ->
                            bottomSheetBinding.overviewTitle.text = item.title.toString()
                            Glide.with(this).load(imageResource(item.posterPath.toString())).into(bottomSheetBinding.overviewThumb)
                            bottomSheetBinding.overviewDesc.text = item.overview.toString()

                            bottomSheetBinding.btnOverviewDetail.setOnClickListener {
                                Toast.makeText(requireContext(), "Detail", Toast.LENGTH_SHORT).show()
                            }

                            bottomSheetBinding.btnOverviewFav.setOnClickListener {
                                Toast.makeText(requireContext(), "Favorite", Toast.LENGTH_SHORT).show()
                            }

                            bottomSheetBinding.btnOverviewClose.setOnClickListener {
                                context.dismiss()
                            }
                        }
                        overviewBottomSheet.show(childFragmentManager, "Movie Overview Bottom Sheet")
                    }
                }
                view.rvMovieShow.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                view.rvMovieShow.adapter = popularMoviesAdapter
                showRvContent(view.pbMovieShow, view.rvMovieShow)
            }
        }
    }

    private fun setUpMovieList() {
        val layoutParams = ConstraintLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        ).apply {
            setMargins(0, 0, 0, 20)
        }

        val nowPlayingLayoutBinding = ComponentMovieShowListBinding.inflate(LayoutInflater.from(context))
        nowPlayingLayoutBinding.movieShowTitle.text = getString(R.string.movie_now_playing)
        nowPlayingLayoutBinding.root.layoutParams = layoutParams
        listOfMovies[NOW_PLAYING] = nowPlayingLayoutBinding
        binding.movieShowList.addView(nowPlayingLayoutBinding.root)

        val topRatedLayoutBinding = ComponentMovieShowListBinding.inflate(LayoutInflater.from(context))
        topRatedLayoutBinding.movieShowTitle.text = getString(R.string.movie_top_rated)
        topRatedLayoutBinding.root.layoutParams = layoutParams
        listOfMovies[TOP_RATED] = topRatedLayoutBinding
        binding.movieShowList.addView(topRatedLayoutBinding.root)

        val popularLayoutBinding = ComponentMovieShowListBinding.inflate(LayoutInflater.from(context))
        popularLayoutBinding.movieShowTitle.text = getString(R.string.movie_popular)
        popularLayoutBinding.root.layoutParams = layoutParams
        listOfMovies[POPULAR] = popularLayoutBinding
        binding.movieShowList.addView(popularLayoutBinding.root)

        val upcomingLayoutBinding = ComponentMovieShowListBinding.inflate(LayoutInflater.from(context))
        upcomingLayoutBinding.movieShowTitle.text = getString(R.string.movie_upcoming)
        upcomingLayoutBinding.root.layoutParams = layoutParams
        listOfMovies[UPCOMING] = upcomingLayoutBinding
        binding.movieShowList.addView(upcomingLayoutBinding.root)
    }


}