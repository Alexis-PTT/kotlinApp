package com.mainProject.Db.Dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import com.mainProject.Db.DataClassGraphic.ExerciceRecordData


@Dao
interface RecordNormalSetDao {

    @Query("SELECT weight,repetition,set_count,start_session " +
            "FROM recordNormalSet,recordSportSession " +
            "WHERE recordNormalSet.id_record_session = recordSportSession.id_record_session " +
            "AND recordNormalSet.id_ex = :id_exercice ")
    suspend fun getRecordsOfExercice( id_exercice : Int ) : List<ExerciceRecordData>

    @Query("SELECT weight,repetition,set_count,start_session " +
            "FROM recordNormalSet,recordSportSession " +
            "WHERE recordNormalSet.id_record_session = recordSportSession.id_record_session " +
            "AND recordNormalSet.id_ex = :id_exercice " +
            "AND start_session > :minDate ")
    suspend fun getRecordsOfExerciceAfterXDate( id_exercice : Int , minDate : String ) : List<ExerciceRecordData>

    @Query("SELECT weight,repetition,set_count,start_session " +
            "FROM recordNormalSet,recordSportSession " +
            "WHERE recordNormalSet.id_record_session = recordSportSession.id_record_session " +
            "AND recordNormalSet.id_ex = :id_exercice " +
            "ORDER BY start_session DESC " +
            "LIMIT 1 ")
    suspend fun getLastRecordOfExercice( id_exercice : Int , minDate : String ) : List<ExerciceRecordData>

    //TODO : MAYBE a could do a new button for the graphs of the sessions
    // where the data could be the poucentage of progression,
    // it would a average of progression on each exercice compare to the last record (weight,set,rep) it has
    @Query("SELECT weight,repetition,set_count,start_session " +
            "FROM recordNormalSet,recordSportSession " +
            "WHERE recordNormalSet.id_record_session = recordSportSession.id_record_session " +
            "AND recordNormalSet.id_ex = :id_exercice " +
            "ORDER BY start_session DESC " +
            "LIMIT 2 ")
    suspend fun test( id_exercice : Int , minDate : String ) : List<ExerciceRecordData>

    @Query("SELECT weight,repetition,set_count,start_session " +
            "FROM recordNormalSet,recordSportSession " +
            "WHERE recordNormalSet.id_record_session = recordSportSession.id_record_session " +
            "AND recordNormalSet.id_ex = :id_exercice " +
            "ORDER BY start_session DESC " +
            "LIMIT 1 ")
    suspend fun getRecordsOfExercice3( id_exercice : Int , minDate : String ) : List<ExerciceRecordData>
}