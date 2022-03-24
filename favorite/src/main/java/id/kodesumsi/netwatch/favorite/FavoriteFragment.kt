package id.kodesumsi.netwatch.favorite

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewbinding.ViewBinding
import com.bumptech.glide.Glide
import dagger.hilt.android.EntryPointAccessors
import id.kodesumsi.core.databinding.ComponentBottomSheetOverviewBinding
import id.kodesumsi.core.databinding.ComponentMovieShowListBinding
import id.kodesumsi.netwatch.base.BaseAdapter
import id.kodesumsi.netwatch.base.BaseBottomSheet
import id.kodesumsi.netwatch.base.BaseFragment
import id.kodesumsi.netwatch.core.data.source.DataSourceConstant.Companion.MOVIES
import id.kodesumsi.netwatch.core.di.FavoriteModuleDepencencies
import id.kodesumsi.netwatch.core.domain.model.Movie
import id.kodesumsi.netwatch.core.domain.usecase.MovieListUseCase
import id.kodesumsi.netwatch.databinding.ItemMovieShowBinding
import id.kodesumsi.netwatch.favorite.databinding.FragmentFavoriteBinding
import id.kodesumsi.netwatch.ui.main.MainActivity
import id.kodesumsi.netwatch.ui.main.MainActivity.Companion.getRating
import id.kodesumsi.netwatch.ui.main.MainActivity.Companion.getYear
import id.kodesumsi.netwatch.ui.main.MainActivity.Companion.showRvContent
import id.kodesumsi.netwatch.ui.main.detail.DetailActivity
import javax.inject.Inject

class FavoriteFragment : BaseFragment<FragmentFavoriteBinding>() {

    @Inject
    lateinit var movieUseCase: MovieListUseCase

    private lateinit var viewModel: FavoriteFragmentViewModel
    private val listOfFavorite: MutableMap<String, ViewBinding> = mutableMapOf()

    private lateinit var movieFavoriteAdapter: BaseAdapter<ItemMovieShowBinding, Movie>

    override fun setupViewBinding(): (LayoutInflater, ViewGroup?, Boolean) -> FragmentFavoriteBinding = FragmentFavoriteBinding::inflate

    override fun onCreate(savedInstanceState: Bundle?) {
        DaggerFavoriteComponent.builder()
            .context(requireContext())
            .appDependencies(EntryPointAccessors.fromApplication(requireActivity().applicationContext, FavoriteModuleDepencencies::class.java))
            .build()
            .inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val factory = ViewModelFactory.getInstance(movieUseCase)
        viewModel = ViewModelProvider(requireActivity(), factory)[FavoriteFragmentViewModel::class.java]

        setupFavoriteListLayout()
        val view: ComponentMovieShowListBinding = listOfFavorite[MOVIES] as ComponentMovieShowListBinding
        viewModel.getAllFavoriteMovie().observe(viewLifecycleOwner) { movies ->
            movieFavoriteAdapter.setData(movies)
            showRvContent(view.pbMovieShow, view.rvMovieShow)
        }
    }

    private fun getMovieListAdapter(): BaseAdapter<ItemMovieShowBinding, Movie> {
        return BaseAdapter(ItemMovieShowBinding::inflate) { item, itemRvBinding ->
            Glide.with(requireContext()).load(MainActivity.imageResource(item.posterPath.toString())).into(itemRvBinding.itemThumb)
            itemRvBinding.root.setOnClickListener {
                val overviewBottomSheet = BaseBottomSheet(
                    ComponentBottomSheetOverviewBinding::inflate) { bottomSheetBinding, _, context ->
                    bottomSheetBinding.overviewTitle.text = item.title.toString()
                    bottomSheetBinding.overviewYear.text = getYear(item.releaseDate.toString())
                    bottomSheetBinding.overviewRating.text = getRating(item.adult?:false)
                    bottomSheetBinding.overviewVote.text = item.voteAverage.toString()
                    Glide.with(requireContext()).load(MainActivity.imageResource(item.posterPath.toString())).into(bottomSheetBinding.overviewThumb)
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

    private fun setupFavoriteListLayout() {
        val layoutParams = ConstraintLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        ).apply {
            setMargins(0, 0, 0, 20)
        }

        val movieFavorite = ComponentMovieShowListBinding.inflate(LayoutInflater.from(context))
        movieFavorite.movieShowTitle.text = getString(R.string.favorite_movies)
        movieFavorite.root.layoutParams = layoutParams
        movieFavoriteAdapter = getMovieListAdapter()
        movieFavorite.rvMovieShow.adapter = movieFavoriteAdapter
        movieFavorite.rvMovieShow.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        listOfFavorite[MOVIES] = movieFavorite
        binding.favoriteMovieShowList.addView(movieFavorite.root)
    }

}