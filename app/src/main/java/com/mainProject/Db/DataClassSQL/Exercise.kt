package com.mainProject.Db.DataClassSQL

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "exercice")
data class Exercice(
    @ColumnInfo val name_exercice: String,
    @PrimaryKey(autoGenerate = true) val exercice_id: Int? =0
)
