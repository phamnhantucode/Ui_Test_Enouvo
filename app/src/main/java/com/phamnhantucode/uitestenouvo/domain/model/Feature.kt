package com.phamnhantucode.uitestenouvo.domain.model

import androidx.compose.runtime.MutableState
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "features")
data class Feature(
    @PrimaryKey val id: Int,
    val feature: String
)
