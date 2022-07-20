package com.example.android_imprative.repository

import com.example.android_imprative.network.service.TVShowService
import javax.inject.Inject

class TVShowRepository @Inject constructor(private val tvShowService: TVShowService) {

    /**
     * Retrofit Related
     * **/
    suspend fun apiTVShowPopular(page:Int) = tvShowService.apiTVShowPopular(page)
    suspend fun apiTVShowDetails(q:Int) = tvShowService.apiTVShowDetails(q)

    /**
     * Room Related
     * **/
}