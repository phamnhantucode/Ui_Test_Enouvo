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

    @Update
    suspend fun updateApproval(approval: Approval)

    @Delete
    suspend fun deleteApproval(approval: Approval)

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
}