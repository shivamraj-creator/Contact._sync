package com.example.anrapp

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import kotlinx.coroutines.flow.Flow


@Dao
interface ContactDao {

    @Insert(onConflict = REPLACE)
    suspend fun insertContacts(
        contacts: List<ContactEntity>
    )

    @Query("SELECT * FROM contacts ORDER BY name")
    fun getContacts(): Flow<List<ContactEntity>>

}