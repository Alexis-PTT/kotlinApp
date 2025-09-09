package com.mainProject.Db.DataClassSQL

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "sessionRecord")
data class SessionRecord(
    @PrimaryKey(autoGenerate = true) val session_id: Int? =0,
    @ColumnInfo val start_session: String,
    @ColumnInfo val end_session: String
)
