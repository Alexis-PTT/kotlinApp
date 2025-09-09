package com.mainProject.Db.DataClassSQL

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "muscle")
data class Muscle(
    @PrimaryKey(autoGenerate = true) val muscle_id: Int? =0,
    @ColumnInfo val muscle_name: String
)
