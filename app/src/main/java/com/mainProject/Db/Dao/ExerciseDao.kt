package com.mainProject.Db.Dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.mainProject.Db.DataClassSQL.Exercise


@Dao
interface ExerciseDao{

    @Query("SELECT * " +
            "FROM exercise " +
            "WHERE exercise_id = :idExercise ")
    suspend fun getAnExercise( idExercise : Int): Exercise

    @Query("SELECT * " +
            "FROM exercise")
    suspend fun getExercises(): List<Exercise>

    @Insert
    suspend fun insertExercise(exercise: Exercise)
}