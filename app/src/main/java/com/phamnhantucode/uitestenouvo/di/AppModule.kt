package com.phamnhantucode.uitestenouvo.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.phamnhantucode.uitestenouvo.data.datasource.ApprovalMatrixDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideAppContext(app: Application): Context = app.applicationContext

    @Provides
    @Singleton
    fun provideDatabase(app: Application): ApprovalMatrixDatabase {
        return Room.databaseBuilder(
            app.applicationContext,
            ApprovalMatrixDatabase::class.java,
            ApprovalMatrixDatabase.DATABASE_NAME
        ).createFromAsset("database/money_manager.db").build()
    }
}
