package id.kodesumsi.netwatch.ui.main.home

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewbinding.ViewBinding
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import id.kodesumsi.core.databinding.ComponentBottomSheetOverviewBinding
import id.kodesumsi.core.databinding.ComponentMovieShowListBinding
import id.kodesumsi.netwatch.R
import id.kodesumsi.netwatch.base.BaseAdapter
import id.kodesumsi.netwatch.base.BaseBottomSheet
import id.kodesumsi.netwatch.base.BaseFragment
import id.kodesumsi.netwatch.core.data.source.DataSourceConstant.Companion.NOW_PLAYING
import id.kodesumsi.netwatch.core.data.source.DataSourceConstant.Companion.POPULAR
import id.kodesumsi.netwatch.core.data.source.DataSourceConstant.Companion.TOP_RATED
import id.kodesumsi.netwatch.core.data.source.DataSourceConstant.Companion.UPCOMING
import id.kodesumsi.netwatch.core.data.source.Resource
import id.kodesumsi.netwatch.core.domain.model.Movie
import id.kodesumsi.netwatch.databinding.FragmentHomeBinding
import id.kodesumsi.netwatch.databinding.ItemMovieShowBinding
import id.kodesumsi.netwatch.ui.main.MainActivity.Companion.getRating
import id.kodesumsi.netwatch.ui.main.MainActivity.Companion.getYear
import id.kodesumsi.netwatch.ui.main.MainActivity.Companion.imageResource
import id.kodesumsi.netwatch.ui.main.MainActivity.Companion.showLoading
import id.kodesumsi.netwatch.ui.main.MainActivity.Companion.showRvContent
import id.kodesumsi.netwatch.ui.main.detail.DetailActivity
import id.kodesumsi.netwatch.ui.search.SearchActivity

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    private val viewModel: HomeFragmentViewModel by viewModels()
    private val listOfMovies: MutableMap<String, ViewBinding> = mutableMapOf()
    private val listOfMoviesAdapter: MutableMap<String, BaseAdapter<ItemMovieShowBinding, Movie>> = mutableMapOf()

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
            setMoviesRosource(movies = movies, view = view, category = NOW_PLAYING)
        }

        viewModel.topRatedMovies.observe(viewLifecycleOwner) { movies ->
            val view: ComponentMovieShowListBinding = listOfMovies[TOP_RATED] as ComponentMovieShowListBinding
            setMoviesRosource(movies = movies, view = view, category = TOP_RATED)
        }

        viewModel.popularMovies.observe(viewLifecycleOwner) { movies ->
            val view: ComponentMovieShowListBinding = listOfMovies[POPULAR] as ComponentMovieShowListBinding
            setMoviesRosource(movies = movies, view = view, category = POPULAR)
        }

        viewModel.upcomingMovie.observe(viewLifecycleOwner) { movies ->
            val view: ComponentMovieShowListBinding = listOfMovies[UPCOMING] as ComponentMovieShowListBinding
            setMoviesRosource(movies = movies, view = view, category = UPCOMING)
        }
    }

    private fun setMoviesRosource(
        movies: Resource<List<Movie>>,
        view: ComponentMovieShowListBinding,
        category: String
    ) {
        when(movies) {
            is Resource.Loading -> showLoading(view.pbMovieShow, view.rvMovieShow)
            is Resource.Success -> {
                listOfMoviesAdapter[category]?.setData(movies.data)
                showRvContent(view.pbMovieShow, view.rvMovieShow)
            }
        }
    }

    private fun getMovieListAdapter(): BaseAdapter<ItemMovieShowBinding, Movie> {
        return BaseAdapter(ItemMovieShowBinding::inflate) { item, itemRvBinding ->
            Glide.with(requireContext()).load(imageResource(item.posterPath.toString())).into(itemRvBinding.itemThumb)
            itemRvBinding.root.setOnClickListener {
                val overviewBottomSheet = BaseBottomSheet(
                    ComponentBottomSheetOverviewBinding::inflate) { bottomSheetBinding, _, context ->
                    bottomSheetBinding.overviewTitle.text = item.title.toString()
                    bottomSheetBinding.overviewYear.text = getYear(item.releaseDate.toString())
                    bottomSheetBinding.overviewRating.text = getRating(item.adult?:false)
                    bottomSheetBinding.overviewVote.text = item.voteAverage.toString()
                    Glide.with(requireContext()).load(imageResource(item.posterPath.toString())).into(bottomSheetBinding.overviewThumb)
                    bottomSheetBinding.overviewDesc.text = item.overview.toString()

                    bottomSheetBinding.btnOverviewDetail.setOnClickListener {
                        val toDetailActivity = Intent(requireContext(), DetailActivity::class.java)
                        toDetailActivity.putExtra(DetailActivity.MOVIE_ID, item.id)
                        requireContext().startActivity(toDetailActivity)
                        context.dismiss()
                    }

                    bottomSheetBinding.btnOverviewClose.setOnClickListener {
                        context.dismiss()
                    }
                }
                overviewBottomSheet.show(childFragmentManager, "Movie Overview Bottom Sheet")
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
        val nowPlayingAdapter = getMovieListAdapter()
        nowPlayingLayoutBinding.rvMovieShow.adapter = nowPlayingAdapter
        nowPlayingLayoutBinding.rvMovieShow.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        listOfMoviesAdapter[NOW_PLAYING] = nowPlayingAdapter
        listOfMovies[NOW_PLAYING] = nowPlayingLayoutBinding
        binding.movieShowList.addView(nowPlayingLayoutBinding.root)

        val topRatedLayoutBinding = ComponentMovieShowListBinding.inflate(LayoutInflater.from(context))
        topRatedLayoutBinding.movieShowTitle.text = getString(R.string.movie_top_rated)
        topRatedLayoutBinding.root.layoutParams = layoutParams
        val topRatedAdapter = getMovieListAdapter()
        topRatedLayoutBinding.rvMovieShow.adapter = topRatedAdapter
        topRatedLayoutBinding.rvMovieShow.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        listOfMoviesAdapter[TOP_RATED] = topRatedAdapter
        listOfMovies[TOP_RATED] = topRatedLayoutBinding
        binding.movieShowList.addView(topRatedLayoutBinding.root)

        val popularLayoutBinding = ComponentMovieShowListBinding.inflate(LayoutInflater.from(context))
        popularLayoutBinding.movieShowTitle.text = getString(R.string.movie_popular)
        popularLayoutBinding.root.layoutParams = layoutParams
        val popularAdapter = getMovieListAdapter()
        popularLayoutBinding.rvMovieShow.adapter = popularAdapter
        popularLayoutBinding.rvMovieShow.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        listOfMoviesAdapter[POPULAR] = popularAdapter
        listOfMovies[POPULAR] = popularLayoutBinding
        binding.movieShowList.addView(popularLayoutBinding.root)

        val upcomingLayoutBinding = ComponentMovieShowListBinding.inflate(LayoutInflater.from(context))
        upcomingLayoutBinding.movieShowTitle.text = getString(R.string.movie_upcoming)
        upcomingLayoutBinding.root.layoutParams = layoutParams
        val upcomingAdapter = getMovieListAdapter()
        upcomingLayoutBinding.rvMovieShow.adapter = upcomingAdapter
        upcomingLayoutBinding.rvMovieShow.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        listOfMoviesAdapter[UPCOMING] = upcomingAdapter
        listOfMovies[UPCOMING] = upcomingLayoutBinding
        binding.movieShowList.addView(upcomingLayoutBinding.root)
    }


}