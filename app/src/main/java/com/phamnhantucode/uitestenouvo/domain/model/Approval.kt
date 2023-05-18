package com.phamnhantucode.uitestenouvo.domain.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.phamnhantucode.uitestenouvo.data.repository.ApprovalRepositoryImpl


@Entity(
    tableName = "approvals",
    foreignKeys = [
        ForeignKey(
            entity = Feature::class,
            parentColumns = ["id"],
            childColumns = ["id_feature"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Approval(

    @PrimaryKey
    val id: Int ?= null,

    val alias: String,
    val minimum: Long,
    val maximum: Long,
    val num_of_approver: Int,
    @ColumnInfo(name = "id_feature")
    val featureId: Int
) {
    suspend fun toApprovalView(repository: ApprovalRepositoryImpl): ApprovalView {
        return ApprovalView(
            id = this.id!!,
            alias = this.alias,
            minimum = this.minimum,
            maximum = this.maximum,
            num_of_approver = this.num_of_approver,
            feature = repository.getFeature(this.featureId),
            approvers = repository.getListApprover(this)
        )
    }
}
