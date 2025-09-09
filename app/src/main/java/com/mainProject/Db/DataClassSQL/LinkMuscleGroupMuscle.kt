package com.mainProject.Db.DataClassSQL

import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(tableName = "linkMuscleGroupMuscle",
    foreignKeys = [ForeignKey(
        entity = Muscle::class,
        parentColumns = ["muscle_id"],
        childColumns = ["id_muscle"],
        onDelete = ForeignKey.Companion.NO_ACTION,
        onUpdate = ForeignKey.Companion.NO_ACTION
    ),
        ForeignKey(
            entity = MuscleGroup::class,
            parentColumns = ["muscle_group_id"],
            childColumns = ["id_muscle_group"],
            onDelete = ForeignKey.Companion.NO_ACTION,
            onUpdate = ForeignKey.Companion.NO_ACTION
        )],
    primaryKeys = ["id_muscle","id_muscle_group"]

)

data class LinkMuscleGroupMuscle(
    val id_muscle: Int,
    val id_muscle_group: Int
)
