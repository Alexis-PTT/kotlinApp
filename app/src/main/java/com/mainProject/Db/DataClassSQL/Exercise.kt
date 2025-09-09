package com.mainProject.Db.DataClassSQL

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "exercise")
data class Exercise(
    @ColumnInfo val name_exercise: String,
    @PrimaryKey(autoGenerate = true) val exercise_id: Int? =0
)
