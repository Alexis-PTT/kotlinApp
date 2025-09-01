package com.mainProject.ViewModel

import android.annotation.SuppressLint
import android.app.Application
import android.os.Build
import androidx.annotation.OptIn
import androidx.annotation.RequiresApi
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.media3.common.util.Log
import androidx.media3.common.util.UnstableApi
import com.mainProject.Db.DataClassGraphic.ExerciceRecordData
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
import java.time.temporal.ChronoUnit
import kotlin.collections.get
import kotlin.collections.mapValues
import kotlin.collections.set
import kotlin.math.min

class ViewModelMain(application: Application): AndroidViewModel(application) {
    private val repository = RepertoryDbSqlite(application.applicationContext)

    //----- ROOM DATABASE -----//

    //----- getter table -----//
    suspend fun getAllSessions(): List<SportSession> =
        withContext(Dispatchers.IO) { repository.getAllSessions() }

    suspend fun getExerciceFromSession(idSession: Int) =
        withContext(Dispatchers.IO) { repository.getExercicesFromSession(idSession) }


    @UnstableApi
    @SuppressLint("NewApi")
    suspend fun getDateRecordOfSession(id_session: Int, temporality: String): List<Float> =
        withContext(Dispatchers.IO) {
            var listToReturn = mutableListOf<Float>(0F, 0F, 0F, 0F, 0F, 0F, 0F)
            val datesRecords = repository.getDateRecordsOFSession(id_session)
            val filteredRecords = filterDataSession(datesRecords,temporality)
            filteredRecords.keys.forEach { key ->
                listToReturn[key.toInt()] = 6-filteredRecords[key]!!
            }
            listToReturn.toList()
        }

    @UnstableApi
    @SuppressLint("NewApi")
    //TODO : EXPLAIN  this function and do functions of test on it
    suspend fun getRecordForExercice(
        id_exercice: Int,
        temporality: String,
        reductionType: String,
        dataNeeded : String
    ): Map<Int, Float> =
        withContext(Dispatchers.IO) {
            val minDate = minDatePerTemporality(temporality)
            val exerciceRecords = repository.getRecordsOfExercice(id_exercice)
            val filteredRecords = filterDataExercice(exerciceRecords,temporality)

            if (dataNeeded == "Effort relatif") {
                val repRecord : Map<Long,Float> = repGrow(filteredRecords,reductionType)
                val setRecord : Map<Long,Float> = setGrow(filteredRecords,reductionType)
                val weightRecord : Map<Long,Float> = weightGrow(filteredRecords,reductionType)
                repRecord.mapValues { (key, value) -> value * (setRecord[key] ?: 0F) * (weightRecord[key] ?: 0F)}.mapKeys { (key) -> key.toInt() }
            }else if(dataNeeded == "Ret"){
                repGrow(filteredRecords,reductionType).mapKeys { (key) -> key.toInt() }
            }else if(dataNeeded == "Set"){
                setGrow(filteredRecords,reductionType).mapKeys { (key) -> key.toInt() }
            }else{
                weightGrow(filteredRecords,reductionType).mapKeys { (key) -> key.toInt() }
            }



        }


    //-----private function-----//


    //TODO EXPLAIN comment this function
    @SuppressLint("NewApi")
    private fun weightGrow(exerciceRecords : Map<Long, List<ExerciceRecordData>>, reductionType : String) : Map<Long, Float>{
        val weightRecord = exerciceRecords.mapValues { it.value.mapIndexed { index,value -> value.weight.toFloat() } }
        return if(reductionType=="Moyenne") averageGrow(weightRecord) else maxGrow(weightRecord)
    }


    //TODO EXPLAIN comment this function
    @SuppressLint("NewApi")
    private fun repGrow(exerciceRecords : Map<Long, List<ExerciceRecordData>>, reductionType : String) : Map<Long, Float>{
        val repRecord = exerciceRecords.mapValues { it.value.mapIndexed { index,value -> value.repetition.toFloat() } }
        return if(reductionType=="Moyenne") averageGrow(repRecord) else maxGrow(repRecord)
    }


    //TODO EXPLAIN comment this function
    @SuppressLint("NewApi")
    private fun setGrow(exerciceRecords : Map<Long, List<ExerciceRecordData>>, reductionType : String) : Map<Long, Float>{
        val setRecord = exerciceRecords.mapValues { it.value.mapIndexed { index,value -> value.set_count.toFloat() } }
        return if(reductionType=="Moyenne") averageGrow(setRecord) else maxGrow(setRecord)
    }


    //TODO EXPLAIN comment this function
    @SuppressLint("NewApi")
    private fun maxGrow(dataGrow : Map<Long, List<Float>>) : Map<Long, Float> = dataGrow.mapValues { if (it.value.isEmpty()) 0F else it.value.max() }


    //TODO EXPLAIN comment this function
    @SuppressLint("NewApi")
    private fun averageGrow(dataGrow : Map<Long, List<Float>>) : Map<Long, Float> = dataGrow.mapValues { if (it.value.isEmpty())0F else it.value.average().toFloat() }


    //TODO : EXPLAIN do a Kdoc of the function and explain the
    // first logic for the filter, the group by and the mapValues
    //TODO : OPTIMISE this function filter all the records but what a could do is to use the min
    //  date and do in the SQL command a thing like if > mindate would reduce all the filters
    //TODO : OPTIMISE could factorise the filter
    //TODO : OPTIMISE could factorise the groupBy
    @SuppressLint("NewApi")
    private fun filterDataExercice(data: List<ExerciceRecordData>,temporality: String): Map<Long, List<ExerciceRecordData>> {
        var currentDate = LocalDate.now()
        var returnData: Map<Long, List<ExerciceRecordData>> = mapOf()
        if (temporality == "Jours") {
            val minDate = currentDate.minusDays(7)
            returnData = data.filter { toDate(it.start_session).isAfter(minDate) }
                .groupBy { ChronoUnit.DAYS.between(toDate(it.start_session), currentDate) }
        } else if (temporality == "Semaines") {
            currentDate = LocalDate.now().minusDays((LocalDate.now().dayOfWeek.value - 1).toLong())
            val minDate =
                currentDate.minusDays((currentDate.dayOfWeek.value - 1).toLong()).minusWeeks(7)
            returnData = data.filter { toDate(it.start_session).isAfter(minDate) }
                .groupBy { ChronoUnit.WEEKS.between(toDate(it.start_session), currentDate) }
        } else if (temporality == "Mois") {
            val minDate = currentDate.minusMonths(7)
                .withDayOfMonth(currentDate.minusMonths(7).lengthOfMonth())
            returnData = data.filter { toDate(it.start_session).isAfter(minDate) }
                .groupBy { monthsBetween(toDate(it.start_session), currentDate) }
        }
        return returnData
    }
    @RequiresApi(Build.VERSION_CODES.O)
    private fun minDatePerTemporality(temporality: String): List<LocalDate> {
        var currentDate = LocalDate.now()
        if (temporality == "Jours") {
            return listOf<LocalDate>(currentDate.minusDays(7),currentDate)
        } else if (temporality == "Semaines") {
            currentDate = LocalDate.now().plusDays(7L-LocalDate.now().dayOfWeek.value)
            return listOf<LocalDate>(currentDate.minusWeeks(7),currentDate)
        } else{
            return listOf<LocalDate>(currentDate.minusMonths(7).withDayOfMonth(currentDate.minusMonths(7).lengthOfMonth()),currentDate)
        }
    }


    @RequiresApi(Build.VERSION_CODES.O)
    private fun filterDataExercice2(data: List<ExerciceRecordData>, temporality: String): Map<Long, List<ExerciceRecordData>> {
        var currentDate = LocalDate.now()
        var returnData: Map<Long, List<ExerciceRecordData>> = mapOf()
        if (temporality == "Jours") {
            returnData = data.groupBy { ChronoUnit.DAYS.between(toDate(it.start_session), currentDate) }
        } else if (temporality == "Semaines") {
            returnData = data.groupBy { ChronoUnit.WEEKS.between(toDate(it.start_session), currentDate) }
        } else if (temporality == "Mois") {
            returnData = data.groupBy { monthsBetween(toDate(it.start_session), currentDate) }
        }
        return returnData
    }




    @RequiresApi(Build.VERSION_CODES.O)
    private fun toDate(dateString: String): LocalDate =
        LocalDate.parse(dateString, DateTimeFormatter.ISO_LOCAL_DATE_TIME)


    @RequiresApi(Build.VERSION_CODES.O)
    private fun monthsBetween(date1 : LocalDate, date2 : LocalDate) : Long {
        return if (date1.year==date2.year) (date2.monthValue-date1.monthValue).toLong()
        else ((date2.monthValue)+(12-date1.monthValue)).toLong()
    }


    //TODO : EXPLAIN do a Kdoc of the function and explain the
    // first logic for the filter, the group by and the mapValues
    //TODO : OPTIMISE see if you can factorise the code with the other filter function
    @SuppressLint("NewApi")
    private suspend fun filterDataSession(data: List<String>,temporality: String): Map<Long, Float> {
            var returnData: Map<Long, Float> = mapOf()
            var currentDate = LocalDate.now()
            if (temporality == "Jours") {
                val minDate = currentDate.minusDays(7)
                returnData = data.filter { toDate(it).isAfter(minDate) }
                    .groupBy { ChronoUnit.DAYS.between(toDate(it), currentDate) }
                    .mapValues { (it.value.size).toFloat() }
            } else if (temporality == "Semaines") {
                currentDate = LocalDate.now().minusDays((LocalDate.now().dayOfWeek.value-1).toLong())
                val minDate = currentDate.minusWeeks(7)
                returnData = data.filter { toDate(it).isAfter(minDate) }
                    .groupBy { ChronoUnit.WEEKS.between(toDate(it), currentDate) }
                    .mapValues { (it.value.size).toFloat() }
            } else if (temporality == "Mois") {
                val minDate = currentDate.minusMonths(7)
                    .withDayOfMonth(currentDate.minusMonths(7).lengthOfMonth())
                returnData = data.filter { toDate(it).isAfter(minDate) }
                    .groupBy { monthsBetween(toDate(it), currentDate) }
                    .mapValues { (it.value.size).toFloat() }
            }
            return returnData
        }
}