package com.mainProject.Db.Dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import com.mainProject.Db.DataClassSQL.Exercice


@Dao
interface ExerciceDao{

    @Query("SELECT * FROM Exercice WHERE id_ex = :idEx")
    suspend fun getAnExercice( idEx : Int): Exercice

}