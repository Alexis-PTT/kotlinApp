package com.mainProject.Db.Dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import com.mainProject.Db.DataClassGraphic.ExerciceRecordData


@Dao
interface RecordNormalSetDao {

    @Query("SELECT weight,repetition,set_count,end_session " +
            "FROM recordNormalSet,recordSportSession " +
            "WHERE recordNormalSet.id_record_session = " +
            " recordSportSession.id_record_session " +
            "AND recordNormalSet.id_ex = :idEx ")
    suspend fun getRecordsOfExercice( idEx : Int ) : List<ExerciceRecordData>

}