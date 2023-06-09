package com.phamnhantucode.uitestenouvo.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "approvers")
data class Approver(
    @PrimaryKey
    val id: Int,
    val approver: String
)
