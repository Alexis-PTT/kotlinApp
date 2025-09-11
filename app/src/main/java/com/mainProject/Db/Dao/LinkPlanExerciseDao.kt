package com.mainProject.Db.Dao

import androidx.room.Dao
import androidx.room.Query
import com.mainProject.Db.DataClassSQL.Exercise

@Dao
interface LinkPlanExerciseDao {
    @Query("SELECT name_exercise,exercise_id " +
            "FROM linkPlanExercise,exercise " +
            "WHERE id_plan = :idPlan " +
            "AND exercise.exercise_id = linkPlanExercise.id_exercise ")
    suspend fun getExercicesFromPlan( idPlan : Int): List<Exercise>

}