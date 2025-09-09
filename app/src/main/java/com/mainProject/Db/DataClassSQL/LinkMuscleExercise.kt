package com.mainProject.Db.DataClassSQL

import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(tableName = "linkMuscleExercice",
    foreignKeys = [ForeignKey(
        entity = Muscle::class,
        parentColumns = ["muscle_id"],
        childColumns = ["id_muscle"],
        onDelete = ForeignKey.Companion.NO_ACTION,
        onUpdate = ForeignKey.Companion.NO_ACTION
    ),
        ForeignKey(
            entity = Exercice::class,
            parentColumns = ["exercice_id"],
            childColumns = ["id_exercice"],
            onDelete = ForeignKey.Companion.NO_ACTION,
            onUpdate = ForeignKey.Companion.NO_ACTION
        )],
    primaryKeys = ["id_muscle","id_exercice"]

)

data class LinkMuscleExercice(
    val id_muscle: Int,
    val id_exercice: Int
)
