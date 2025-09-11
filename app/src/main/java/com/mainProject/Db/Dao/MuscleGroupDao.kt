package com.mainProject.Db.Dao

import androidx.room.Dao
import androidx.room.Query
import com.mainProject.Db.DataClassSQL.Exercise


@Dao
interface MuscleGroupDao{
    @Query("SELECT DISTINCT muscle_group_name  " +
            "FROM exercise,muscle,muscleGroup,linkMuscleExercise,linkMuscleGroupMuscle " +
            "WHERE exercise_id = :idExercise " +
            "AND exercise_id = id_exercise " +
            "AND muscle.muscle_id = linkMuscleExercise.id_muscle " +
            "AND muscleGroup.muscle_group_id = linkMuscleGroupMuscle.id_muscle_group " +
            "AND linkMuscleGroupMuscle.id_muscle = linkMuscleGroupMuscle.id_muscle_group ")
    suspend fun getMuscleGroupFromExercise( idExercise : Int): List<String>



    @Query("SELECT DISTINCT muscleGroup.muscle_group_name " +
            "FROM exercise,linkMuscleExercise, " +
            "sportPlan,linkPlanExercise," +
            "muscleGroup,linkMuscleGroupMuscle " +
            "WHERE sportPlan.plan_id = :idPlan " +
            "AND sportPlan.plan_id = linkPlanExercise.id_plan " +
            "AND exercise.exercise_id = linkPlanExercise.id_exercise " +
            "AND exercise.exercise_id = linkMuscleExercise.id_exercise " +
            "AND linkMuscleGroupMuscle.id_muscle = linkMuscleExercise.id_muscle " +
            "AND linkMuscleGroupMuscle.id_muscle_group = muscleGroup.muscle_group_id ")
    suspend fun getMusclesGroupsFromPlan( idPlan : Int) : List<String>


}