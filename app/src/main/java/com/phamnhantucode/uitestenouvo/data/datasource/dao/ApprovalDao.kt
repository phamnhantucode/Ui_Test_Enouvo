package com.phamnhantucode.uitestenouvo.data.datasource.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.phamnhantucode.uitestenouvo.domain.model.Approval
import kotlinx.coroutines.flow.Flow

@Dao
interface ApprovalDao {
    @Insert
    suspend fun insertApproval(approval: Approval)

    @Update
    suspend fun updateApproval(approval: Approval)

    @Delete
    suspend fun deleteApproval(approval: Approval)

    @Query("SELECT * FROM approvals")
    fun getAllApprovals(): Flow<List<Approval>>

    @Query("SELECT * FROM approvals WHERE id = :id")
    fun getAllApprovals(id: Int): Flow<List<Approval>>
}