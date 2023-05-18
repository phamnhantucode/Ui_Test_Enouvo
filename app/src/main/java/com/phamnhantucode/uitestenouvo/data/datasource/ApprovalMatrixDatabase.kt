package com.phamnhantucode.uitestenouvo.data.datasource

import androidx.room.Database
import androidx.room.RoomDatabase
import com.phamnhantucode.uitestenouvo.data.datasource.dao.ApprovalDao
import com.phamnhantucode.uitestenouvo.data.datasource.dao.ApproverDao
import com.phamnhantucode.uitestenouvo.data.datasource.dao.FeatureDao
import com.phamnhantucode.uitestenouvo.domain.model.Approval
import com.phamnhantucode.uitestenouvo.domain.model.Approver
import com.phamnhantucode.uitestenouvo.domain.model.Feature

@Database(
    entities = [Approval::class, Approver::class, Feature::class],
    version = 1,
    exportSchema = true
)
abstract class ApprovalMatrixDatabase: RoomDatabase() {
    abstract val approvalDao: ApprovalDao
    abstract val featureDao: FeatureDao
    abstract val approverDao: ApproverDao
    companion object {
        const val DATABASE_NAME = "approval_matrix_db"
    }
}