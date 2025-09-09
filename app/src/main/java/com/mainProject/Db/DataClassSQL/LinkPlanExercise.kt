package com.mainProject.Db.DataClassSQL

import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(tableName = "linkPlanExercise",
    foreignKeys = [ForeignKey(
        entity = SportPlan::class,
        parentColumns = ["plan_id"],
        childColumns = ["id_plan"],
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
    primaryKeys = ["id_plan","id_exercise"]

)

data class LinkPlanExercise(
    val id_plan: Int,
    val id_exercise: Int

)
