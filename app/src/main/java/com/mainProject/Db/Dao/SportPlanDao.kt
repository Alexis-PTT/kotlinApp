package com.mainProject.Db.Dao

import androidx.room.Dao
import androidx.room.Query
import com.mainProject.Db.DataClassSQL.SportPlan

@Dao
interface SportPlanDao {

    @Query("SELECT * FROM sportPlan")
    suspend fun getAllPlans(): List<SportPlan>

}