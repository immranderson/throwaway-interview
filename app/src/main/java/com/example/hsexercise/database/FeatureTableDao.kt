package com.example.hsexercise.database

import androidx.room.*
import io.reactivex.Observable

@Dao
interface FeatureTableDao {
    @Query("SELECT * FROM feature")
    fun getAll(): Observable<List<FeatureModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(models: List<FeatureModel>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(featureModel: FeatureModel)
}

