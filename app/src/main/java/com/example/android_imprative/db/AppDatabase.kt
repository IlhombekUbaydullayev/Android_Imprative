package com.example.android_imprative.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.android_imprative.db.dao.TVShowDao
import com.example.android_imprative.model.TVShow

@Database(entities = [TVShow::class], version = 1, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {
    abstract fun getShowDao(): TVShowDao

    companion object {
        @Volatile
        private var DB_INSTANCE: AppDatabase? = null

        fun getAppDBInstance(context: Context):AppDatabase{
            if (DB_INSTANCE == null) {
                DB_INSTANCE = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "DB_TV_SHOWS"
                )
                    .allowMainThreadQueries()
                    .build()
            }
            return DB_INSTANCE!!
        }
    }
}