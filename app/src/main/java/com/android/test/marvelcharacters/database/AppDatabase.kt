package com.android.test.marvelcharacters.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Characters::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun charactersDao(): CharactersDao
}
