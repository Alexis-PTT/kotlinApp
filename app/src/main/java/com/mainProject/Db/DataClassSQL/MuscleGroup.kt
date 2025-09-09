package com.mainProject.Db.DataClassSQL

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "muscleGroup")
data class MuscleGroup(
    @PrimaryKey(autoGenerate = true) val muscle_group_id: Int? =0,
    @ColumnInfo val muscle_group_name: String
)
