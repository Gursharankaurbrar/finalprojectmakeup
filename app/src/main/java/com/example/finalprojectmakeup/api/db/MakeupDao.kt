package com.example.finalprojectmakeup.api.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.finalprojectmakeup.api.model.MakeupDataItem


@Dao
interface MakeupDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll( makeups: List<MakeupDataItem>)

    @Query("SELECT * FROM makeups WHERE id = :id")
    fun getMakeupById(id: Int) : MakeupDataItem?

    @Query("SELECT * FROM makeups")
    fun getAllMakeup(): List<MakeupDataItem>

}