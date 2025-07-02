package com.mainProject.Db.DataClassSQL

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "exercice")
data class Exercice(
    @ColumnInfo val name_ex: String,
    @ColumnInfo val exercice_type: String,
    @ColumnInfo val muscle: String,
    @PrimaryKey(autoGenerate = true) val id_ex: Int? =0
)
