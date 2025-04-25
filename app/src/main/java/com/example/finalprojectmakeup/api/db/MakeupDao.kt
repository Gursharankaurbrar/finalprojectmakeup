package com.example.finalprojectmakeup.api.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.finalprojectmakeup.api.model.MakeupDataItem
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object (DAO) for accessing makeup data in the local Room database.
 * Provides methods for inserting and querying makeup products.
 */
@Dao
interface MakeupDao {
    /**
     * Inserts a list of makeup items into the database.
     * If a conflict occurs (e.g., duplicate primary key), the existing record will be replaced.
     **/
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll( makeups: List<MakeupDataItem>)

    /**
     * Retrieves a makeup item from the database based on its unique ID.
     **/
    @Query("SELECT * FROM makeups WHERE id = :id")
    fun getMakeupById(id: Int) : MakeupDataItem?

    /**
     * Retrieves all makeup items from the database.
     **/
    @Query("SELECT * FROM makeups")
    fun getAllMakeup(): List<MakeupDataItem>

    /**
     * Updates the makeup state in database.
     **/
    @Update
    suspend fun updateMakeupState(makeup: MakeupDataItem)

    /**
     * Retrieves the favorite makeup items.
     **/
    @Query("SELECT * FROM makeups WHERE isFavorite = 1")
    fun getFavoriteMakeups(): Flow<List<MakeupDataItem>>

    /**
     * Retrieves the searched makeup items
     **/
    @Query("SELECT * FROM makeups WHERE name LIKE :query OR brand LIKE :query")
    suspend fun searchMakeups(query: String): List<MakeupDataItem>



    @Query("DELETE FROM makeups WHERE id = :makeupID")
    suspend fun deleteMakeup(makeupID: Int)

}