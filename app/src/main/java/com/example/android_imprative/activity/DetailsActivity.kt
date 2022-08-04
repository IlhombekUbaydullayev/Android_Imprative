package com.example.android_imprative.activity

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.android_imprative.adapter.EpisodesAdapter
import com.example.android_imprative.adapter.TVShortAdapter
import com.example.android_imprative.databinding.ActivityDetailsBinding
import com.example.android_imprative.model.Details
import com.example.android_imprative.model.Episode
import com.example.android_imprative.utils.Loggers
import com.example.android_imprative.viewmodel.DetailsViewModel
import com.google.android.material.tabs.TabLayout
import java.util.*
import java.util.logging.Logger
import kotlin.collections.ArrayList

//https://mikescamell.com/shared-element-transitions-part-3/
//https://mikescamell.com/shared-element-transitions-part-4-recyclerview/

class DetailsActivity : BaseActivity() {
    private val TAG = DetailsActivity::class.java.simpleName
    lateinit var binding: ActivityDetailsBinding
    private val viewModel: DetailsViewModel by viewModels()
    private val tvShortAdapter by lazy { TVShortAdapter() }
    private val episodesAdapter by lazy { EpisodesAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)
        initViews()
        initObserves()
    }

    private fun initViews() {
        binding.rvShorts.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.rvShorts.adapter = tvShortAdapter

        binding.rvEpisodes.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.rvEpisodes.adapter = episodesAdapter

        val iv_detail: ImageView = binding.ivDetails
        binding.ivClose.setOnClickListener {
            ActivityCompat.finishAfterTransition(this)
        }

        val extras = intent.extras
        val show_id = extras!!.getLong("show_id")
        val show_img = extras.getString("show_img")
        val show_name = extras.getString("show_name")
        val show_network = extras.getString("show_network")

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val imageTransitionName = extras.getString("iv_movie")
            iv_detail.transitionName = imageTransitionName
        }

        binding.tvName.text = show_name
        binding.tvType.text = show_network
        Glide.with(this).load(show_img).into(iv_detail)

        viewModel.apiTVShowDetails(show_id.toInt())
    }

    private fun initObserves() {
        viewModel.tvShowDetails.observe(this) {
            Loggers.d(TAG, it.toString())
            tvShortAdapter.submitList(it.tvShow!!.pictures)
            getAllSeasons(it.tvShow)
            binding.tvDetails.text = it.tvShow.description
        }

        viewModel.errorMessage.observe(this) {
            Loggers.d(TAG, it.toString())
        }

        viewModel.isLoading.observe(this) {
            Loggers.d(TAG, it.toString())
            if (it) {
                binding.pbLoading.visibility = View.VISIBLE
            } else {
                binding.pbLoading.visibility = View.GONE
            }
        }
    }

    private fun getAllSeasons(tvShow: Details){
        val map = TreeMap<String, ArrayList<Episode>>()

        Loggers.d("@@@", tvShow.episodes.toString())
        for (i in tvShow.episodes){
            val items = map.getOrDefault("Season ${i.season.toString()}", ArrayList())
            items.add(i)
            map.put("Season ${i.season.toString()}", items)
        }

        for(k in map.keys){
            binding.tabSeasons.addTab(binding.tabSeasons.newTab().setText(k))
        }

        episodesAdapter.submitList(map["Season ${tvShow.episodes[0].season}"])

        binding.tabSeasons.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                episodesAdapter.submitList(map[tab?.text])
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

        })

        Loggers.d("@@@", "Map: ${map.size}")

    }

}