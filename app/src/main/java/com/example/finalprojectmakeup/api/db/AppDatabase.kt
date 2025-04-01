package com.example.finalprojectmakeup.api.db

import android.content.Context
import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.finalprojectmakeup.api.model.MakeupDataItem

@Database(entities = [MakeupDataItem:: class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase(){
    abstract  fun makeupDao(): MakeupDao

    // COMPANION OBJECT
    companion object{
        @Volatile
        private var INSTANCE: AppDatabase ?=null

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