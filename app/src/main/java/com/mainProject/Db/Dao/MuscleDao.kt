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
}