package com.phamnhantucode.uitestenouvo.domain.model

import androidx.room.ColumnInfo

data class ApprovalView(
    val id: Int? = null,
    val alias: String = "",
    val minimum: Long = -1,
    val maximum: Long = -1,
    val num_of_approver: Int = 0,
    val feature: Feature? = null,
    val approver: List<Approver> = listOf()
)
