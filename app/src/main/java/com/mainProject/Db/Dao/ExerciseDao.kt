package com.mainProject.Db.Dao

import androidx.room.Dao
import androidx.room.Query
import com.mainProject.Db.DataClassSQL.Exercise


@Dao
interface ExerciseDao{

    @Query("SELECT * " +
            "FROM exercise,exerciseRecord " +
            "WHERE exercise_id = :idExercise ")
    suspend fun getAnExercise( idExercise : Int): Exercise

    @Query("SELECT * " +
            "FROM exercise,exerciseRecord ")
    suspend fun getExercises(): List<Exercise>

}