package com.phamnhantucode.uitestenouvo.data.datasource.dao

import androidx.room.Dao
import androidx.room.Query
import com.phamnhantucode.uitestenouvo.domain.model.Feature

@Dao
interface FeatureDao {
    @Query("SELECT * FROM features")
    fun getAllFeature(): List<Feature>

    @Query("SELECT * FROM features WHERE id = :id")
    fun getFeatureById(id: Int): Feature
}