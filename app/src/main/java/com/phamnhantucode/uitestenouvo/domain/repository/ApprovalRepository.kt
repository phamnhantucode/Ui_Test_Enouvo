package com.phamnhantucode.uitestenouvo.domain.repository

import com.phamnhantucode.uitestenouvo.domain.model.Approval
import com.phamnhantucode.uitestenouvo.domain.model.ApprovalView
import kotlinx.coroutines.flow.Flow

interface ApprovalRepository {
    suspend fun getApprovalView(id: Int): ApprovalView
    suspend fun getApproval(id: Int): Approval
    suspend fun getAllApproval(): Flow<List<Approval>>
    suspend fun getAllApprovalView(): Flow<List<ApprovalView>>

    suspend fun updateApproval(approval: Approval)
    suspend fun deleteApproval(approval: Approval)

    suspend fun updateApprovalView(approvalView: ApprovalView)
    suspend fun deleteApprovalView(approvalView: ApprovalView)
}