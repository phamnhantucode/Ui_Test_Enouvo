package com.phamnhantucode.uitestenouvo.domain.model

import androidx.room.ColumnInfo

data class ApprovalView(
    val id: Int,
    val alias: String,
    val minimum: Long,
    val maximum: Long,
    val num_of_approver: Int,
    val featureId: Feature,
    val approver: List<Approver>
)
