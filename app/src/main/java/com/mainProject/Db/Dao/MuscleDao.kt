package com.mainProject.Db.Dao

import androidx.room.Dao
import androidx.room.Query
import com.mainProject.Db.DataClassSQL.Exercise


@Dao
interface MuscleDao{

    @Query("SELECT muscle_name " +
            "FROM muscle,exercise,linkMuscleExercise " +
            "WHERE exercise_id = :idExercise " +
            "AND exercise_id = id_exercise " +
            "AND muscle_id = id_muscle ")
    suspend fun getMuscleFromExercise( idExercise : Int): List<String>

    @Query("SELECT DISTINCT muscle.muscle_name " +
            "FROM muscle,exercise,linkMuscleExercise,sportPlan,linkPlanExercise " +
            "WHERE sportPlan.plan_id = :idPlan " +
            "AND sportPlan.plan_id = linkPlanExercise.id_plan "+
            "AND  exercise.exercise_id = linkPlanExercise.id_exercise "+
            "AND exercise.exercise_id = linkMuscleExercise.id_exercise " +
            "AND muscle.muscle_id = linkMuscleExercise.id_muscle ")
    suspend fun getMusclesFromPlan( idPlan : Int) : List<String>
}