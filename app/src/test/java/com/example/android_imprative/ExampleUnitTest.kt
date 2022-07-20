package com.example.android_imprative

import com.example.android_imprative.di.AppModule
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun standardTest() = runTest {
        val response = AppModule().tvShowService().apiTVShowPopular(1)
        assertEquals(response.code(),200)
    }

    @Test
    fun checkStatusCode()= runTest {
        val response = AppModule().tvShowService().apiTVShowPopular(1)
        assertEquals(response.code(),200)
    }

    @Test
    fun responseIsSuccessful()= runTest {
        val response = AppModule().tvShowService().apiTVShowPopular(1)
        assertTrue(response.isSuccessful)
    }

    @Test
    fun checkTVShowListNotNull()= runTest {
        val response = AppModule().tvShowService().apiTVShowPopular(1)
        assertNotNull(response.body())
    }

    @Test
    fun checkTVShowListSize()= runTest {
        val response = AppModule().tvShowService().apiTVShowPopular(1)
        val tvShowSearch = response.body()
        assertEquals(tvShowSearch!!.tv_shows.size, 20)
    }

    @Test
    fun checkFirstTVShowCountry()= runTest {
        val response = AppModule().tvShowService().apiTVShowPopular(1)
        val tvShows = response.body()!!.tv_shows
        val tvShow = tvShows[0]
        assertEquals(tvShow.status, "Running")
    }
}