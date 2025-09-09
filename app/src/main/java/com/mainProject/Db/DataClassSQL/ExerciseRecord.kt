package com.mainProject.Db.DataClassSQL

import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(tableName = "exerciseRecord",
    foreignKeys = [
        ForeignKey(
            entity = SessionRecord::class,
            parentColumns = ["session_id"],
            childColumns = ["id_session"],
            onDelete = ForeignKey.Companion.NO_ACTION,
            onUpdate = ForeignKey.Companion.NO_ACTION
        ),
        ForeignKey(
            entity = Exercise::class,
            parentColumns = ["exercise_id"],
            childColumns = ["id_exercise"],
            onDelete = ForeignKey.Companion.NO_ACTION,
            onUpdate = ForeignKey.Companion.NO_ACTION
        )
    ],
    primaryKeys = ["id_exercise","id_session"]

)
data class ExerciseRecord(
    val id_session: Int,
    val id_exercise: Int,
    val weight: Double,
    val rep_count: Int,
    val set_count: Int
)
