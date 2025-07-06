package com.mainProject.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mainProject.Db.DataClassSQL.Exercice
import com.mainProject.Db.DataClassSQL.LinkExerciceToSession
import com.mainProject.Db.DataClassSQL.SportSession
import com.mainProject.Repertory.RepertoryDbSqlite
import com.patrykandpatrick.vico.core.cartesian.data.columnSeries
import com.patrykandpatrick.vico.core.common.data.ExtraStore
import com.patrykandpatrick.vico.multiplatform.cartesian.data.CartesianChartModelProducer
import com.patrykandpatrick.vico.multiplatform.cartesian.data.columnSeries
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ViewModelMain(application: Application): AndroidViewModel(application) {
    private val repository = RepertoryDbSqlite(application.applicationContext)

    //----- ROOM DATABASE -----//

    //----- getter table -----//
    suspend fun getAllSessions(): List<SportSession> =
        withContext ( Dispatchers.IO ){ repository.getAllSessions() }

    suspend fun getExerciceFromSession(idSession : Int) =
        withContext ( Dispatchers.IO ){ repository.getExercicesFromSession(idSession) }

    suspend fun getSessionsFromExercice(id_ex : Int) =
        withContext ( Dispatchers.IO ){ repository.getExercicesFromSession(id_ex) }

    suspend fun getRecordsOfExercice(id_ex : Int) =
        withContext ( Dispatchers.IO ){ repository.getExercicesFromSession(id_ex) }

    suspend fun getRecordsOfSession(id_session : Int) =
        withContext ( Dispatchers.IO ){ repository.getExercicesFromSession(id_session) }


    //----- other function -----//
    fun increment() {}
}