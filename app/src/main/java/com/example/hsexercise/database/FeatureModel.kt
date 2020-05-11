package com.example.hsexercise.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "feature")
data class FeatureModel(
    @PrimaryKey
    val id: String,
    val author: String,
    val url: String,
    val width: Int,
    val height: Int
)
