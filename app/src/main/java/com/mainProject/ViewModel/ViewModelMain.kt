package com.mainProject.ViewModel

import android.annotation.SuppressLint
import android.app.Application
import androidx.annotation.OptIn
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.media3.common.util.Log
import androidx.media3.common.util.UnstableApi
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
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class ViewModelMain(application: Application): AndroidViewModel(application) {
    private val repository = RepertoryDbSqlite(application.applicationContext)

    //----- ROOM DATABASE -----//

    //----- getter table -----//
    suspend fun getAllSessions(): List<SportSession> =
        withContext ( Dispatchers.IO ){ repository.getAllSessions() }

    suspend fun getExerciceFromSession(idSession : Int) =
        withContext ( Dispatchers.IO ){ repository.getExercicesFromSession(idSession) }

    @OptIn(UnstableApi::class)
    @SuppressLint("NewApi")
    suspend fun getDateRecordOfSession(id_session: Int, temporality : String) : Map<Int, Float> =
    withContext ( Dispatchers.IO ){
        var returnMap : MutableMap<Int,Float> = mutableMapOf<Int, Float>()
        val allDateRecord = repository.getDateRecordsOFSession(id_session).map {  LocalDate.parse(it,
            DateTimeFormatter.ISO_LOCAL_DATE_TIME)}
        var currentDate = LocalDateTime.now().toLocalDate()
        if (temporality == "Jours"){
            allDateRecord.forEach { record ->
                var i = 1;
                while (i!=7 && record.compareTo(currentDate.minusDays((7-i).toLong()))!=0){
                    i++;
                }
                if (record.compareTo(currentDate.minusDays((7-i).toLong()))==0) {
                    returnMap[i] = (if (returnMap.containsKey(i) && returnMap.get(i)!=null)  returnMap.get(i)?.plus(1f) else 1f) as Float
                }
            }
        }
        else if (temporality == "Semaines"){
            currentDate=currentDate.minusDays((currentDate.dayOfWeek.value-1).toLong())
            allDateRecord.forEach { record ->
                var i = 1
                val recordToMonday = record.minusDays((record.dayOfWeek.value-1).toLong())
                while (i!=7 &&
                    recordToMonday.compareTo(currentDate.minusDays((7*(7-i)).toLong()))!=0)
                {
                    i++;
                }
                if (recordToMonday.compareTo(currentDate.minusDays((7*(7-i)).toLong()))==0) {
                    returnMap[i] = (if (returnMap.containsKey(i) && returnMap.get(i)!=null)  returnMap.get(i)?.plus(1f) else 1f) as Float
                }
            }
        }
        else if (temporality == "Mois"){
            currentDate=currentDate.minusDays((currentDate.dayOfMonth-1).toLong())
            allDateRecord.forEach { record ->
                var i = 1
                val recordToFirstDayMonth = record.minusDays((record.dayOfMonth-1).toLong())
                while (i!=7 &&
                    recordToFirstDayMonth.compareTo(currentDate.minusMonths(((7-i)).toLong()))!=0)
                {
                    Log.d("testRepertory","${currentDate},${recordToFirstDayMonth}")
                    i++;
                }
                if (recordToFirstDayMonth.compareTo(currentDate.minusMonths(((7-i)).toLong()))==0) {
                    returnMap[i] = (if (returnMap.containsKey(i) && returnMap.get(i)!=null)  returnMap.get(i)?.plus(1f) else 1f) as Float
                }
            }
        }
        returnMap
    }


    //----- other function -----//
    fun increment() {}


}