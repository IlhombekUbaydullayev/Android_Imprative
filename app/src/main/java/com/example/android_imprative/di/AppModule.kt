package com.example.android_imprative.di

import android.app.Application
import com.example.android_imprative.db.AppDatabase
import com.example.android_imprative.db.dao.TVShowDao
import com.example.android_imprative.network.Server.IS_TESTER
import com.example.android_imprative.network.Server.SERVER_DEVELOPMENT
import com.example.android_imprative.network.Server.SERVER_PRODUCTION
import com.example.android_imprative.network.service.TVShowService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    /**
     * Retrofit Related
     */
    @Provides
    fun server(): String {
        if (IS_TESTER) return SERVER_DEVELOPMENT
        return SERVER_PRODUCTION
    }

    @Provides
    @Singleton
    fun retrofitClient(): Retrofit {
        return Retrofit.Builder().baseUrl(server())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun tvShowService(): TVShowService {
        return retrofitClient().create(TVShowService::class.java)
    }

    /**
     * Room Related
     * **/

    @Provides
    @Singleton
    fun appDatabase(context:Application):AppDatabase{
        return AppDatabase.getAppDBInstance(context)
    }

    @Provides
    @Singleton
    fun tvShowDao(appDatabase: AppDatabase):TVShowDao{
        return appDatabase.getShowDao()
    }
}