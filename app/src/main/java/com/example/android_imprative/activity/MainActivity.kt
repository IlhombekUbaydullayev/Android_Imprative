package com.example.android_imprative.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.viewModels
import com.example.android_imprative.R
import com.example.android_imprative.adapter.TVShowAdapter
import com.example.android_imprative.databinding.ActivityMainBinding
import com.example.android_imprative.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {
    private val TAG = MainActivity::class.java.simpleName
    private val viewModel: MainViewModel by viewModels()
    lateinit var binding: ActivityMainBinding
    lateinit var adapter: TVShowAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        initViews()
    }

    private fun initViews() {

    }
}