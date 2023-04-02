package com.padc.themovieapp.persistence

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.padc.themovieapp.data.vos.MovieVO
import com.padc.themovieapp.persistence.daos.MovieDao

@Database(entities = [MovieVO::class], version = 2, exportSchema = false)
abstract class MovieDatabase : RoomDatabase() {
    companion object{
        const val DB_NAME = "THE_MOVIE_DB"  //unique

        var doInstance: MovieDatabase? = null

        fun getDBInstance(context: Context): MovieDatabase?{
            when(doInstance){
                null -> {
                    doInstance = Room.databaseBuilder(context,MovieDatabase::class.java, DB_NAME)
                        .allowMainThreadQueries()
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }
            return doInstance
        }
    }
    abstract fun movieDao(): MovieDao
}