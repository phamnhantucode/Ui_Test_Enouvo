package com.phamnhantucode.uitestenouvo.data.datasource.dao

import androidx.room.*
import com.phamnhantucode.uitestenouvo.domain.model.Approval
import com.phamnhantucode.uitestenouvo.domain.model.ApprovalApprover
import com.phamnhantucode.uitestenouvo.domain.model.Approver
import kotlinx.coroutines.flow.Flow

@Dao
interface ApprovalDao {
    @Insert
    suspend fun insertApproval(approval: Approval): Long

    @Insert
    fun insertApprovalApprover(approvalApprover: ApprovalApprover)


    @Transaction
    suspend fun addApprovalApprover(approval: Approval, approvers: List<Approver>) {
        val approvalId = insertApproval(approval)
        approvers.forEach { approver ->
            insertApprovalApprover(ApprovalApprover(approvalId.toInt(), approver.id))
        }
    }

    @Transaction
    suspend fun updateApproval(approval: Approval, approvers: List<Approver>) {
        updateApproval(approval)

        deleteApprovalApprovers(approval.id!!)

        val approvalApprovers = approvers.map { ApprovalApprover(approval.id, it.id) }
        approvers.forEach { approver ->
            insertApprovalApprover(ApprovalApprover(approval.id, approver.id))
        }
    }


    @Update
    suspend fun updateApproval(approval: Approval)

    @Delete
    suspend fun deleteApproval(approval: Approval)

    @Delete
    suspend fun deleteApprovalApprover(approvalApprover: ApprovalApprover)

    @Query("SELECT * FROM approvals")
    fun getAllApprovals(): Flow<List<Approval>>

    @Query("SELECT * FROM approvals WHERE id = :id")
    suspend fun getApproval(id: Int): Approval

    @Query(
        """
        SELECT approvers.*
        FROM approvers
        INNER JOIN approval_approver ON approval_approver.id_approver = approvers.id
        WHERE approval_approver.id_approval = :approvalId
        """
    )
    suspend fun getListApprover(approvalId: Int?): List<Approver>

    @Query("DELETE FROM approval_approver WHERE id_approval = :approvalId")
    suspend fun deleteApprovalApprovers(approvalId: Int)
}