package com.example.android_imprative.repository

import com.example.android_imprative.db.dao.TVShowDao
import com.example.android_imprative.model.TVShow
import com.example.android_imprative.network.service.TVShowService
import javax.inject.Inject

class TVShowRepository @Inject constructor(
    private val tvShowService: TVShowService,
    private val tvShowDao: TVShowDao) {

    /**
     * Retrofit Related
     * **/
    suspend fun apiTVShowPopular(page: Int) = tvShowService.apiTVShowPopular(page)
    suspend fun apiTVShowDetails(q: Int) = tvShowService.apiTVShowDetails(q)

    /**
     * Room Related
     * **/

    suspend fun getTVShowsFromDB() = tvShowDao.getTVShowsFromDB()
    suspend fun insertTVShowToDB(tvShow: TVShow) = tvShowDao.insertTVShowToDB(tvShow)
    suspend fun deleteTVShowToDB() = tvShowDao.deleteTvShowsFromDB()
}