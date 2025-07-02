package com.mainProject.Db.DataClassGraphic

data class SessionPlanData(
    val session_id: Int,
    val name_session: String,
    val quantity_exercice: Int,
    val quantity_record: Int,
    val creation_date: String)