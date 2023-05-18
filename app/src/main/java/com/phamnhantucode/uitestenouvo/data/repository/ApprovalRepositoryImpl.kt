package com.phamnhantucode.uitestenouvo.data.repository

import com.phamnhantucode.uitestenouvo.data.datasource.dao.ApprovalDao
import com.phamnhantucode.uitestenouvo.data.datasource.dao.ApproverDao
import com.phamnhantucode.uitestenouvo.data.datasource.dao.FeatureDao
import com.phamnhantucode.uitestenouvo.domain.model.Approval
import com.phamnhantucode.uitestenouvo.domain.model.ApprovalView
import com.phamnhantucode.uitestenouvo.domain.model.Approver
import com.phamnhantucode.uitestenouvo.domain.model.Feature
import com.phamnhantucode.uitestenouvo.domain.repository.ApprovalRepository
import kotlinx.coroutines.flow.Flow

class ApprovalRepositoryImpl(
    private val approverDao: ApproverDao,
    private val approvalDao: ApprovalDao,
    private val featureDao: FeatureDao,
): ApprovalRepository {

    override suspend fun getApprovalView(id: Int): ApprovalView {
        TODO("Not yet implemented")
    }

    override suspend fun getApproval(id: Int): Approval {
        TODO("Not yet implemented")
    }

    override suspend fun getAllApproval(): Flow<List<Approval>> {
        TODO("Not yet implemented")
    }

    override suspend fun getAllApprovalView(): Flow<List<ApprovalView>> {
        TODO("Not yet implemented")
    }

    override suspend fun updateApproval(approval: Approval) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteApproval(approval: Approval) {
        TODO("Not yet implemented")
    }

    override suspend fun updateApprovalView(approvalView: ApprovalView) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteApprovalView(approvalView: ApprovalView) {
        TODO("Not yet implemented")
    }

    override suspend fun getFeature(featureId: Int): Feature {
        TODO("Not yet implemented")
    }

    override suspend fun getListApproval(approval: Approval): List<Approver> {
        TODO("Not yet implemented")
    }

    override suspend fun getAllFeatures(): List<Feature> {
        TODO("Not yet implemented")
    }

    override suspend fun getAllApprovers(): List<Approver> {
        TODO("Not yet implemented")
    }
}