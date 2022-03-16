package id.kodesumsi.netwatch.ui.search

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.activity.viewModels
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewbinding.ViewBinding
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import id.kodesumsi.netwatch.R
import id.kodesumsi.netwatch.base.BaseActivity
import id.kodesumsi.netwatch.base.BaseAdapter
import id.kodesumsi.netwatch.base.BaseBottomSheet
import id.kodesumsi.netwatch.core.data.source.DataSourceConstant
import id.kodesumsi.netwatch.core.data.source.Resource
import id.kodesumsi.netwatch.core.domain.model.Movie
import id.kodesumsi.netwatch.databinding.ActivitySearchBinding
import id.kodesumsi.netwatch.databinding.ComponentBottomSheetOverviewBinding
import id.kodesumsi.netwatch.databinding.ComponentMovieShowListBinding
import id.kodesumsi.netwatch.databinding.ItemMovieShowBinding
import id.kodesumsi.netwatch.ui.main.MainActivity
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
        binding.componentInputSearch.edtSearch.setOnEditorActionListener { textView, i, keyEvent ->
            return@setOnEditorActionListener when(i) {
                EditorInfo.IME_ACTION_SEARCH -> {
                    viewModel.searchMovieList(textView.text.toString()).observe(this@SearchActivity) { movies ->
                        val view: ComponentMovieShowListBinding = listOfSearch[DataSourceConstant.MOVIES] as ComponentMovieShowListBinding

                        when(movies) {
                            is Resource.Loading -> showLoading(view.pbMovieShow, view.rvMovieShow)
                            is Resource.Success -> {
                                searchMovieAdapter = BaseAdapter(movies.data!!, ItemMovieShowBinding::inflate) { item, itemRvBinding ->
                                    Glide.with(this).load(imageResource(item.posterPath.toString())).into(itemRvBinding.itemThumb)
                                    itemRvBinding.root.setOnClickListener {
                                        val overviewBottomSheet = BaseBottomSheet(ComponentBottomSheetOverviewBinding::inflate) { bottomSheetBinding, _, context ->
                                            bottomSheetBinding.overviewTitle.text = item.title.toString()
                                            bottomSheetBinding.overviewYear.text = getYear(item.releaseDate.toString())
                                            bottomSheetBinding.overviewRating.text = getRating(item.adult ?: false)
                                            bottomSheetBinding.overviewVote.text = item.voteAverage.toString()
                                            Glide.with(this).load(imageResource(item.posterPath.toString())).into(bottomSheetBinding.overviewThumb)
                                            bottomSheetBinding.overviewDesc.text = item.overview.toString()

                                            bottomSheetBinding.btnOverviewDetail.setOnClickListener {
                                                val toDetailActivity = Intent(this@SearchActivity, DetailActivity::class.java)
                                                toDetailActivity.putExtra(DetailActivity.MOVIE_ID, item.id)
                                                startActivity(toDetailActivity)
                                            }

                                            bottomSheetBinding.btnOverviewClose.setOnClickListener {
                                                context.dismiss()
                                            }
                                        }
                                        overviewBottomSheet.show(supportFragmentManager, "Movie Overview Bottom Sheet")
                                    }
                                }
                                view.rvMovieShow.layoutManager = LinearLayoutManager(this@SearchActivity, LinearLayoutManager.HORIZONTAL, false)
                                view.rvMovieShow.adapter = searchMovieAdapter
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
        listOfSearch[DataSourceConstant.MOVIES] = searchMovieList
        binding.searchMovieShowList.addView(searchMovieList.root)
    }

}