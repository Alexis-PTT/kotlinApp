package com.mainProject.Db.DataClassSQL

import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(tableName = "exerciceRecord",
    foreignKeys = [
        ForeignKey(
            entity = SessionRecord::class,
            parentColumns = ["session_id"],
            childColumns = ["id_session"],
            onDelete = ForeignKey.Companion.NO_ACTION,
            onUpdate = ForeignKey.Companion.NO_ACTION
        ),
        ForeignKey(
            entity = Exercice::class,
            parentColumns = ["exercice_id"],
            childColumns = ["id_exercice"],
            onDelete = ForeignKey.Companion.NO_ACTION,
            onUpdate = ForeignKey.Companion.NO_ACTION
        )
    ],
    primaryKeys = ["id_exercice","id_session"]

)
data class ExerciseRecord(
    val id_session: Int,
    val id_exercice: Int,
    val weight: Double,
    val rep_count: Int,
    val set_count: Int
)
