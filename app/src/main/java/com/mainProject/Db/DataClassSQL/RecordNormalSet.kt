package com.mainProject.Db.DataClassSQL

import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "recordNormalSet",
    foreignKeys = [
        ForeignKey(
            entity = RecordSportSession::class,
            parentColumns = ["id_record_session"],
            childColumns = ["id_record_session"],
            onDelete = ForeignKey.Companion.NO_ACTION,
            onUpdate = ForeignKey.Companion.NO_ACTION
        ),
        ForeignKey(
            entity = Exercice::class,
            parentColumns = ["id_ex"],
            childColumns = ["id_ex"],
            onDelete = ForeignKey.Companion.NO_ACTION,
            onUpdate = ForeignKey.Companion.NO_ACTION
        )
    ],
    primaryKeys = ["id_ex","id_record_session"]

)
data class RecordNormalSet(
    val id_record_session: Int,
    val id_ex: Int,
    val weight: Double,
    val repetition: Int,
    val set_count: Int
)
