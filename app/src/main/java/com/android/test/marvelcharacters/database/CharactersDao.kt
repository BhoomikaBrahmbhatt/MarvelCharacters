package com.android.test.marvelcharacters.database

import androidx.room.*

@Dao
interface CharactersDao {
    @Query("SELECT * FROM characters")
    fun getAll():  List<Characters>

    @Insert
    fun insertAll(vararg users: Characters)

    @Update
    fun updateUsers(vararg users: Characters)

    @Delete
    fun delete(user: Characters)

    @Query("DELETE FROM characters")
    fun deleteAll()
}
