package com.mainProject.Db.DataClassSQL

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "sportPlan")
data class SportPlan(
    @PrimaryKey(autoGenerate = true) val plan_id: Int? =0,
    @ColumnInfo val name_plan: String,
    @ColumnInfo val desc_plan: String? =""
)
