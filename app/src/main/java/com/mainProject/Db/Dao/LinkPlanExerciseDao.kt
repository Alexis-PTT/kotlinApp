package com.mainProject.Db.Dao

import androidx.room.Dao
import androidx.room.Query
import com.mainProject.Db.DataClassSQL.Exercice

@Dao
interface LinkPlanExerciceDao {
    @Query("SELECT name_exercice,exercice_id " +
            "FROM linkPlanExercice,exercice " +
            "WHERE id_plan = :idPlan " +
            "AND exercice.exercice_id = linkPlanExercice.id_exercice ")
    suspend fun getExercicesFromPlan( idPlan : Int): List<Exercice>

}