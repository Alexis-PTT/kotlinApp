package com.mainProject.Db.DataClassSQL

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "linkExerciceToSession",
    foreignKeys = [ForeignKey(
        entity = SportSession::class,
        parentColumns = ["session_id"],
        childColumns = ["id_session"],
        onDelete = ForeignKey.Companion.NO_ACTION,
        onUpdate = ForeignKey.Companion.NO_ACTION
    ),
    ForeignKey(
        entity = Exercice::class,
        parentColumns = ["id_ex"],
        childColumns = ["id_ex"],
        onDelete = ForeignKey.Companion.NO_ACTION,
        onUpdate = ForeignKey.Companion.NO_ACTION
    )],
    primaryKeys = ["id_ex","id_session"]

)

data class LinkExerciceToSession(
    val id_session: Int,
    val id_ex: Int
)
