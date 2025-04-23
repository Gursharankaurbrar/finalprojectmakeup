package com.example.finalprojectmakeup.api.db

import android.content.Context
import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.finalprojectmakeup.api.model.MakeupDataItem

/**
 * Room Database for the Final Project Makeup application.
 * It provides a local database to store makeup product data.
 **/
@Database(entities = [MakeupDataItem:: class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase(){

    /**
     * Abstract method to access the DAO (Data Access Object) for makeup products.
     * Room will generate the implementation of this method.
     */
    abstract  fun makeupDao(): MakeupDao

    companion object{
        @Volatile
        private var INSTANCE: AppDatabase ?=null

        /**
         * Returns the singleton instance of the AppDatabase.
         * Creates the database if it doesn't already exist.
         **/
        fun getInstance(context: Context) : AppDatabase{
            return INSTANCE ?: synchronized(this){
                val instance = androidx.room.Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "Final Project"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                instance
            }
        }

    }
}