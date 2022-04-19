package id.kodesumsi.netwatch.ui.search

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.activity.viewModels
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewbinding.ViewBinding
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import id.kodesumsi.core.databinding.ComponentBottomSheetOverviewBinding
import id.kodesumsi.core.databinding.ComponentMovieShowListBinding
import id.kodesumsi.netwatch.R
import id.kodesumsi.netwatch.base.BaseActivity
import id.kodesumsi.netwatch.base.BaseAdapter
import id.kodesumsi.netwatch.base.BaseBottomSheet
import id.kodesumsi.netwatch.core.data.source.DataSourceConstant
import id.kodesumsi.netwatch.core.data.source.Resource
import id.kodesumsi.netwatch.core.domain.model.Movie
import id.kodesumsi.netwatch.databinding.ActivitySearchBinding
import id.kodesumsi.netwatch.databinding.ItemMovieShowBinding
import id.kodesumsi.netwatch.ui.main.MainActivity.Companion.getRating
import id.kodesumsi.netwatch.ui.main.MainActivity.Companion.getYear
import id.kodesumsi.netwatch.ui.main.MainActivity.Companion.imageResource
import id.kodesumsi.netwatch.ui.main.MainActivity.Companion.showLoading
import id.kodesumsi.netwatch.ui.main.MainActivity.Companion.showRvContent
import id.kodesumsi.netwatch.ui.main.detail.DetailActivity

@AndroidEntryPoint
class SearchActivity : BaseActivity<ActivitySearchBinding>() {

    private val viewModel: SearchActivityViewModel by viewModels()
    private lateinit var searchMovieAdapter: BaseAdapter<ItemMovieShowBinding, Movie>
    private val listOfSearch: MutableMap<String, ViewBinding> = mutableMapOf()

    override fun setupViewBinding(): (LayoutInflater) -> ActivitySearchBinding = ActivitySearchBinding::inflate

    override fun setupViewInstance(savedInstanceState: Bundle?) {
        setupSearchResultLayout()
        binding.btnSearchBack.setOnClickListener {
            onBackPressed()
        }
        binding.componentInputSearch.edtSearch.setOnEditorActionListener { textView, i, keyEvent ->
            return@setOnEditorActionListener when(i) {
                EditorInfo.IME_ACTION_SEARCH -> {
                    val view: ComponentMovieShowListBinding = listOfSearch[DataSourceConstant.MOVIES] as ComponentMovieShowListBinding
                    viewModel.searchMovieList(textView.text.toString()).observe(this@SearchActivity) { movies ->
                        when(movies) {
                            is Resource.Loading -> showLoading(view.pbMovieShow, view.rvMovieShow)
                            is Resource.Success -> {
                                searchMovieAdapter.setData(movies.data)
                                showRvContent(view.pbMovieShow, view.rvMovieShow)
                            }
                        }
                    }

                    true
                }
                else -> false
            }
        }
    }

    private fun getMovieListAdapter(): BaseAdapter<ItemMovieShowBinding, Movie> {
        return BaseAdapter(ItemMovieShowBinding::inflate) { item, itemRvBinding ->
            Picasso.get().load(imageResource(item.posterPath.toString())).into(itemRvBinding.itemThumb)
            itemRvBinding.root.setOnClickListener {
                val overviewBottomSheet = BaseBottomSheet(
                    ComponentBottomSheetOverviewBinding::inflate) { bottomSheetBinding, _, context ->
                    bottomSheetBinding.overviewTitle.text = item.title.toString()
                    bottomSheetBinding.overviewYear.text = getYear(item.releaseDate.toString())
                    bottomSheetBinding.overviewRating.text = getRating(item.adult?:false)
                    bottomSheetBinding.overviewVote.text = item.voteAverage.toString()
                    Picasso.get().load(imageResource(item.posterPath.toString())).into(bottomSheetBinding.overviewThumb)
                    bottomSheetBinding.overviewDesc.text = item.overview.toString()

                    bottomSheetBinding.btnOverviewDetail.setOnClickListener {
                        val toDetailActivity = Intent(this, DetailActivity::class.java)
                        toDetailActivity.putExtra(DetailActivity.MOVIE_ID, item.id)
                        startActivity(toDetailActivity)
                        context.dismiss()
                    }

                    bottomSheetBinding.btnOverviewClose.setOnClickListener {
                        context.dismiss()
                    }
                }
                overviewBottomSheet.show(supportFragmentManager, "Movie Overview Bottom Sheet")
            }
        }
    }

    private fun setupSearchResultLayout() {
        val layoutParams = ConstraintLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        ).apply {
            setMargins(0, 0, 0, 20)
        }

        val searchMovieList = ComponentMovieShowListBinding.inflate(LayoutInflater.from(this))
        searchMovieList.movieShowTitle.text = getString(R.string.movie_search_result)
        searchMovieList.root.layoutParams = layoutParams
        searchMovieAdapter = getMovieListAdapter()
        searchMovieList.rvMovieShow.layoutManager = LinearLayoutManager(this@SearchActivity, LinearLayoutManager.HORIZONTAL, false)
        searchMovieList.rvMovieShow.adapter = searchMovieAdapter
        listOfSearch[DataSourceConstant.MOVIES] = searchMovieList
        binding.searchMovieShowList.addView(searchMovieList.root)
    }

}