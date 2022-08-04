package com.example.android_imprative.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.core.app.ActivityOptionsCompat
import androidx.core.view.ViewCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.android_imprative.adapter.TVShowAdapter
import com.example.android_imprative.databinding.ActivityMainBinding
import com.example.android_imprative.model.TVShow
import com.example.android_imprative.utils.Loggers
import com.example.android_imprative.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.logging.Logger

class MainActivity : BaseActivity() {
    private val TAG = MainActivity::class.java.simpleName
    val viewModel: MainViewModel by viewModels()
    lateinit var binding: ActivityMainBinding
    lateinit var adapter: TVShowAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        initViews()
    }

    private fun initViews() {
        initObserves()
        val glm = GridLayoutManager(this, 2)
        binding.rvHome.layoutManager = glm
        refreshAdapter(ArrayList())

        binding.rvHome.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (glm.findLastCompletelyVisibleItemPosition() == adapter.itemCount - 1) {
                    val nextPage = viewModel.tvShowPopular.value!!.page + 1
                    Loggers.d(TAG, nextPage.toString())
                    viewModel.apiTVShowPopular(nextPage)
                }
            }
        })
        binding.bFab.setOnClickListener {
            binding.rvHome.smoothScrollToPosition(0)
        }

        viewModel.apiTVShowPopular(1)
    }

    private fun initObserves() {
        // Retrofit Related
        viewModel.tvShowsFromApi.observe(this, {
            Loggers.d(TAG, it.size.toString())
            adapter.setNewTVShows(it)
        })
        viewModel.errorMessage.observe(this, {
            Loggers.d(TAG, it.toString())
        })
        viewModel.isLoading.observe(this, Observer {
            Loggers.d(TAG, it.toString())
            if (viewModel.isLoading.value == true) {
                binding.pbLoading.visibility = View.VISIBLE
            } else {
                binding.pbLoading.visibility = View.GONE
            }
        })

        // Room Related
        viewModel.tvShowsFromDB.observe(this, {
            Loggers.d(TAG, it!!.size.toString())
        })
    }

    private fun refreshAdapter(items: ArrayList<TVShow>) {
        adapter = TVShowAdapter(this, items)
        binding.rvHome.adapter = adapter
    }

    fun callDetailsActivity(tvShow: TVShow, sharedImageView: ImageView) {
        val intent = Intent(this, DetailsActivity::class.java)
        intent.putExtra("show_id", tvShow.id)
        intent.putExtra("show_img", tvShow.image_thumbnail_path)
        intent.putExtra("show_name", tvShow.name)
        intent.putExtra("show_network", tvShow.network)
        intent.putExtra("iv_movie", ViewCompat.getTransitionName(sharedImageView))

        val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
            this, sharedImageView, ViewCompat.getTransitionName(sharedImageView)!!
        )
        startActivity(intent, options.toBundle())
    }

}