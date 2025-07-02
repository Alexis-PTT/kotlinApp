package com.mainProject.Db.Dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import com.mainProject.Db.DataClassGraphic.ExerciceRecordData
import com.mainProject.Db.DataClassSQL.Exercice
import com.mainProject.Db.DataClassSQL.RecordSportSession


@Dao
interface RecordSportSessionDao{

    @Query("SELECT * " +
            "FROM recordSportSession " +
            "WHERE session_id = :idSession ")
    suspend fun getRecordsOfSession( idSession : Int ) : List<RecordSportSession>
}