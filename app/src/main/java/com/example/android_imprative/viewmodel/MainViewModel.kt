package com.example.android_imprative.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.android_imprative.model.TVShow
import com.example.android_imprative.model.TVShowDetails
import com.example.android_imprative.model.TVShowPopular
import com.example.android_imprative.repository.TVShowRepository
import javax.inject.Inject

class MainViewModel @Inject constructor(private val tvShowRepository: TVShowRepository) : ViewModel() {
    val isLoading = MutableLiveData<Boolean>()
    val errorMessage = MutableLiveData<String>()
    val tvShowsFromApi = MutableLiveData<ArrayList<TVShow>>()
    val tvShowsFromDB = MutableLiveData<List<TVShow>>()

    val tvShowPopular = MutableLiveData<TVShowPopular>()
    val tvShowDetails = MutableLiveData<TVShowDetails>()

}