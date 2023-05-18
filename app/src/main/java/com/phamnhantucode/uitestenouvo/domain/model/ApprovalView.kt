package com.phamnhantucode.uitestenouvo.domain.model

data class ApprovalView(
    val id: Int? = null,
    val alias: String = "",
    val minimum: Long = -1,
    val maximum: Long = -1,
    val num_of_approver: Int = 0,
    val feature: Feature? = null,
    val approvers: List<Approver> = listOf()
)
