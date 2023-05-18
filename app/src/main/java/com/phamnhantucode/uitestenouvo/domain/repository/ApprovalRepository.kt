package com.phamnhantucode.uitestenouvo.domain.repository

import com.phamnhantucode.uitestenouvo.domain.model.Approval
import com.phamnhantucode.uitestenouvo.domain.model.ApprovalView
import com.phamnhantucode.uitestenouvo.domain.model.Approver
import com.phamnhantucode.uitestenouvo.domain.model.Feature
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
    suspend fun getFeature(featureId: Int): Feature
    suspend fun getAllFeatures(): List<Feature>
    suspend fun getAllApprovers(): List<Approver>

    suspend fun addNewApprovalMatrix(approvalView: ApprovalView)
    suspend fun getListApprover(approval: Approval): List<Approver>
}