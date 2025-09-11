package com.mainProject.Db.Dao

import androidx.room.Dao
import androidx.room.Query
import com.mainProject.Db.DataClassSQL.SportPlan

@Dao
interface SportPlanDao {

    @Query("SELECT * FROM sportPlan")
    suspend fun getPlans(): List<SportPlan>
    @Query("SELECT COUNT(*) " +
            "FROM sportPlan,linkPlanExercise " +
            "WHERE sportPlan.plan_id = :idPlan " +
            "AND sportPlan.plan_id = linkPlanExercise.id_plan ")
    suspend fun getQuantityOfExercisePerPlan( idPlan : Int) : Int

}