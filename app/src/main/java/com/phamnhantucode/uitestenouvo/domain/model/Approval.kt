package com.phamnhantucode.uitestenouvo.domain.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey



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
    val id: Int,
    val alias: String,
    val minimum: Long,
    val maximum: Long,
    val num_of_approver: Int,
    @ColumnInfo(name = "id_feature")
    val featureId: Int
)
