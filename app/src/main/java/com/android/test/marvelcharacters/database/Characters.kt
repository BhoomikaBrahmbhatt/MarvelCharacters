package com.android.test.marvelcharacters.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "characters")
data class Characters(
    @PrimaryKey(autoGenerate = true)
    var characterid: Int = 0,
    @ColumnInfo(name ="id"  ) var id: Int?,
    @ColumnInfo(name ="name"  ) var name: String?,
    @ColumnInfo(name ="thumbnail") val thumbnail   : String?
)
