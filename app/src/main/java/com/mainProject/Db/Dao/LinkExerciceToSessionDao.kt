package com.mainProject.Db.Dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import com.mainProject.Db.DataClassSQL.Exercice
import com.mainProject.Db.DataClassSQL.LinkExerciceToSession


@Dao
interface LinkExerciceToSessionDao{

    @Query("SELECT * " +
            "FROM linkExerciceToSession " +
            "WHERE id_ex = :idEx")
    suspend fun getSessionsIdOfExercice( idEx : Int ) : List<LinkExerciceToSession>

    @Query("SELECT * " +
            "FROM linkExerciceToSession " +
            "WHERE id_session = :idSession")
    suspend fun getExercicesIdOfSession( idSession : Int ) : List<LinkExerciceToSession>

}