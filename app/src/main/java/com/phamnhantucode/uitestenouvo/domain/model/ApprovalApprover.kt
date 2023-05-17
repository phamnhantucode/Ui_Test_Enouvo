package com.phamnhantucode.uitestenouvo.domain.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey


@Entity(
    tableName = "approval_approver",
    primaryKeys = ["id_approval", "id_approver"],
    foreignKeys = [
        ForeignKey(
            entity = Approval::class,
            parentColumns = ["id"],
            childColumns = ["id_approval"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Approver::class,
            parentColumns = ["id"],
            childColumns = ["id_approver"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class ApprovalApprover(
    @ColumnInfo(name = "id_approval")
    val approvalId: Int,
    @ColumnInfo(name = "id_approver")
    val approverId: Int
)
