package com.mainProject.Db.DataClassSQL

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "sportSession")
data class SportSession(
    @PrimaryKey(autoGenerate = true) val session_id: Int? =0,
    @ColumnInfo val name_session: String
)