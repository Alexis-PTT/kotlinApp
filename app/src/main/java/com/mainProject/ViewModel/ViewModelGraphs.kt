package com.mainProject.ViewModel

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.media3.common.util.Log
import androidx.media3.common.util.UnstableApi
import com.mainProject.Db.DataClassGraphic.ExerciseRecordData
import com.mainProject.Repertory.RepertoryDbSqlite
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

class ViewModelGraphs(repertory : RepertoryDbSqlite) {
    private val repository = repertory

    //----- Public function -----//

    @UnstableApi
    @SuppressLint("NewApi")
    suspend fun getDateRecords(temporality: String): List<Float> =
        withContext(Dispatchers.IO) {
            var listToReturn = mutableListOf<Float>(0F, 0F, 0F, 0F, 0F, 0F, 0F)
            val datesRecords = repository.getDatesOfSessions()
            val filteredRecords = filterDataSession(datesRecords,temporality)
            filteredRecords.keys.forEach { key ->
                listToReturn[key.toInt()] = 6-filteredRecords[key]!!
            }
            listToReturn.toList()
        }

    @UnstableApi
    @SuppressLint("NewApi")
    suspend fun getRecordsOfExercise(
        id_exercice: Int,
        temporality: String,
        reductionType: String,
        dataNeeded : String
    ): Map<Int, Float> =
        withContext(Dispatchers.IO) {
            val minDate = minDatePerTemporality(temporality)
            val exerciceRecords = repository.getRecordsOfExercise(id_exercice)
            val filteredRecords = filterDataExercice(exerciceRecords,temporality)

            Log.d("","taille filtered record :${filteredRecords.size}")
            if(filteredRecords.size>0){
                if (dataNeeded == "Progression") {
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
            }else{
                val lastRecord=repository.getLastRecordOfExercise(id_exercice)
                val returnMap = mutableMapOf<Int,Float>()
                var valueOnGraph : Float = 0F
                if (dataNeeded == "Progression") {
                    valueOnGraph = (lastRecord.weight*lastRecord.set_count*lastRecord.rep_count).toFloat()
                }else if(dataNeeded == "Ret"){
                    valueOnGraph = (lastRecord.rep_count).toFloat()
                }else if(dataNeeded == "Set"){
                    valueOnGraph = (lastRecord.set_count).toFloat()
                }else{
                    valueOnGraph = (lastRecord.weight).toFloat()
                }
                returnMap.put(0,valueOnGraph)
                returnMap.put(6,valueOnGraph)
                returnMap
            }




        }


    //-----private function-----//


    //TODO EXPLAIN comment this function
    @SuppressLint("NewApi")
    private fun weightGrow(exerciceRecords : Map<Long, List<ExerciseRecordData>>, reductionType : String) : Map<Long, Float>{
        //if ((exerciceRecords.size)!=0){
        val weightRecord = exerciceRecords.mapValues { it.value.mapIndexed { index,value -> value.weight.toFloat() } }
        return if(reductionType=="Moyenne") averageGrow(weightRecord) else maxGrow(weightRecord)
        //}
        //val mapToReturn = exerciceRecords.toMutableMap()
        //val lastWeight =

        //return
    }


    //TODO EXPLAIN comment this function
    @SuppressLint("NewApi")
    private fun repGrow(exerciceRecords : Map<Long, List<ExerciseRecordData>>, reductionType : String) : Map<Long, Float>{
        val repRecord = exerciceRecords.mapValues { it.value.mapIndexed { index,value -> value.rep_count.toFloat() } }
        return if(reductionType=="Moyenne") averageGrow(repRecord) else maxGrow(repRecord)
    }


    //TODO EXPLAIN comment this function
    @SuppressLint("NewApi")
    private fun setGrow(exerciceRecords : Map<Long, List<ExerciseRecordData>>, reductionType : String) : Map<Long, Float>{
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
    private fun filterDataExercice(data: List<ExerciseRecordData>, temporality: String): Map<Long, List<ExerciseRecordData>> {
        var currentDate = LocalDate.now()
        var returnData: Map<Long, List<ExerciseRecordData>> = mapOf()
        if (temporality == "Jours") {
            val minDate = currentDate.minusDays(7)
            returnData = data.filter { toDate(it.dateRecord).isAfter(minDate) }
                .groupBy {6- ChronoUnit.DAYS.between(toDate(it.dateRecord), currentDate) }
        } else if (temporality == "Semaines") {
            currentDate = LocalDate.now().minusDays((LocalDate.now().dayOfWeek.value - 1).toLong())
            val minDate =
                currentDate.minusDays((currentDate.dayOfWeek.value - 1).toLong()).minusWeeks(7)
            returnData = data.filter { toDate(it.dateRecord).isAfter(minDate) }
                .groupBy {6- ChronoUnit.WEEKS.between(toDate(it.dateRecord), currentDate) }
        } else if (temporality == "Mois") {
            val minDate = currentDate.minusMonths(7)
                .withDayOfMonth(currentDate.minusMonths(7).lengthOfMonth())
            returnData = data.filter { toDate(it.dateRecord).isAfter(minDate) }
                .groupBy {6- monthsBetween(toDate(it.dateRecord), currentDate) }
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