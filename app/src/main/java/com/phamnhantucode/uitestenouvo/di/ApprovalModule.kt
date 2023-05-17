package com.phamnhantucode.uitestenouvo.di

import com.phamnhantucode.uitestenouvo.data.datasource.ApprovalMatrixDatabase
import com.phamnhantucode.uitestenouvo.data.repository.ApprovalRepositoryImpl
import com.phamnhantucode.uitestenouvo.domain.repository.ApprovalRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApprovalModule {

    @Provides
    @Singleton
    fun provideApprovalRepository(database: ApprovalMatrixDatabase) =
        ApprovalRepositoryImpl(
            database.approverDao,
            database.approvalDao,
            database.featureDao
        )
}