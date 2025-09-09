package com.mainProject.Db.DataClassSQL

import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(tableName = "linkMuscleExercise",
    foreignKeys = [ForeignKey(
        entity = Muscle::class,
        parentColumns = ["muscle_id"],
        childColumns = ["id_muscle"],
        onDelete = ForeignKey.Companion.NO_ACTION,
        onUpdate = ForeignKey.Companion.NO_ACTION
    ),
        ForeignKey(
            entity = Exercise::class,
            parentColumns = ["exercise_id"],
            childColumns = ["id_exercise"],
            onDelete = ForeignKey.Companion.NO_ACTION,
            onUpdate = ForeignKey.Companion.NO_ACTION
        )],
    primaryKeys = ["id_muscle","id_exercise"]

)

data class LinkMuscleExercise(
    val id_muscle: Int,
    val id_exercise: Int
)
