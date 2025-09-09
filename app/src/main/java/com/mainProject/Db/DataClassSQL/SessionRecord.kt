package com.mainProject.Db.DataClassSQL

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "recordSportSession",
    foreignKeys = [ForeignKey(
        entity = SportSession::class,
        parentColumns = ["session_id"],
        childColumns = ["session_id"],
        onDelete = ForeignKey.Companion.NO_ACTION,
        onUpdate = ForeignKey.Companion.NO_ACTION
    )]
)
data class RecordSportSession(
    @PrimaryKey(autoGenerate = true) val id_record_session: Int? =0,
    @ColumnInfo val session_id: Int,
    @ColumnInfo val start_session: String,
    @ColumnInfo val end_session: String
)
