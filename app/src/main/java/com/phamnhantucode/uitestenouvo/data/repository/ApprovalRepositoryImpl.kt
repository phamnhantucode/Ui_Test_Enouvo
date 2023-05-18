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
import kotlinx.coroutines.flow.transformLatest

class ApprovalRepositoryImpl(
    private val approverDao: ApproverDao,
    private val approvalDao: ApprovalDao,
    private val featureDao: FeatureDao,
) : ApprovalRepository {

    override suspend fun getApprovalView(id: Int): ApprovalView {
        val approval = getApproval(id)
        return approval.toApprovalView(this)
    }

    override suspend fun getApproval(id: Int): Approval {
        return approvalDao.getApproval(id)
    }

    override suspend fun getAllApproval(): Flow<List<Approval>> {
        return approvalDao.getAllApprovals()
    }

    override suspend fun getAllApprovalView(): Flow<List<ApprovalView>> {
        return getAllApproval().transformLatest { approvals ->
            approvals.map { it.toApprovalView(this@ApprovalRepositoryImpl) }
        }
    }

    override suspend fun updateApproval(approval: Approval) {
        return approvalDao.updateApproval(approval)
    }

    override suspend fun deleteApproval(approval: Approval) {
        return approvalDao.deleteApproval(approval)
    }

    override suspend fun updateApprovalView(approvalView: ApprovalView) {
        approvalDao.updateApproval(
            approval = Approval(
                id = approvalView.id,
                alias = approvalView.alias,
                minimum = approvalView.minimum,
                maximum = approvalView.maximum,
                num_of_approver = approvalView.num_of_approver,
                featureId = approvalView.feature!!.id
            ),
            approvers = approvalView.approvers
        )
    }

    override suspend fun deleteApprovalView(approvalView: ApprovalView) {

    }

    override suspend fun getFeature(featureId: Int): Feature {
        return featureDao.getFeatureById(featureId)
    }


    override suspend fun getAllFeatures(): List<Feature> {
        return featureDao.getAllFeature()
    }

    override suspend fun getAllApprovers(): List<Approver> {
        return approverDao.getAllApprover()
    }

    override suspend fun addNewApprovalMatrix(approvalView: ApprovalView) {
        approvalDao.addApprovalApprover(
            approval = Approval(
                alias = approvalView.alias,
                minimum = approvalView.minimum,
                maximum = approvalView.maximum,
                num_of_approver = approvalView.num_of_approver,
                featureId = approvalView.feature!!.id
            ),
            approvers = approvalView.approvers
        )
    }

    override suspend fun getListApprover(approval: Approval): List<Approver> {
        return approvalDao.getListApprover(approval.id)
    }

}