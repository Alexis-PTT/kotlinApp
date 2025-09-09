package com.mainProject.Db.DataClassSQL

import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(tableName = "linkPlanExercice",
    foreignKeys = [ForeignKey(
        entity = SportPlan::class,
        parentColumns = ["plan_id"],
        childColumns = ["id_plan"],
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
    primaryKeys = ["id_plan","id_exercice"]

)

data class LinkPlanExercice(
    val id_plan: Int,
    val id_exercice: Int

)
