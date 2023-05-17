package com.phamnhantucode.uitestenouvo.data.datasource.dao

import androidx.room.Dao
import androidx.room.Query
import com.phamnhantucode.uitestenouvo.domain.model.Approver

@Dao
interface ApproverDao {

    @Query("SELECT * FROM approvers")
    fun getAllApprover(): List<Approver>

    @Query("SELECT * FROM approvers WHERE id = :id")
    fun getApproverById(id: Int): Approver
}