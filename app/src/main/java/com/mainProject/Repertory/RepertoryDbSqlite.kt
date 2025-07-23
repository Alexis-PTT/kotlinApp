package com.mainProject.Repertory

import android.content.Context
import com.mainProject.Db.AppDatabase
import com.mainProject.Db.DataClassSQL.Exercice
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RepertoryDbSqlite(context : Context) {
    private val databaseSqlite = AppDatabase.getDatabase(context)

    private val daoSportSession = databaseSqlite.SportSession()
    private val daoExercice = databaseSqlite.Exercice()
    private val daoLinkExToSes = databaseSqlite.LinkExerciceToSession()
    private val daoRecordExercice = databaseSqlite.RecordNormalSet()
    private val daoRecordSession = databaseSqlite.RecordSportSession()
    private val daoOtherQueries = databaseSqlite.complexQueries()

    //----- getter des tables -----//
    suspend fun getAllSessions() = daoSportSession.getAllSessions()

    suspend fun getSessionsIdOfExercice(idEx : Int) = daoLinkExToSes.getSessionsIdOfExercice(idEx)

    suspend fun getExercicesFromSession(idSession : Int): List<Exercice> {
        val exId = daoLinkExToSes.getExercicesIdOfSession(idSession)
        var listOfExercice : List<Exercice> = mutableListOf()
        exId.forEach { id ->
            listOfExercice = listOfExercice.plus(daoExercice.getAnExercice(id.id_ex.toInt()))
        }
        return listOfExercice
    }

    suspend fun getRecordsOfExercice(id_ex : Int) = daoRecordExercice.getRecordsOfExercice(id_ex)

    suspend fun getRecordsOfSession(id_session : Int) = daoRecordSession.getRecordsOfSession(id_session)

    suspend fun getDateRecordsOFSession(id_session: Int) = daoRecordSession.getDateRecordsOfSession(id_session)



    //----- setter des tables -----//
}