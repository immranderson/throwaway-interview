package com.example.hsexercise.providers

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.hsexercise.database.FeatureModel
import com.example.hsexercise.database.FeatureTableDao

const val DATABASE_NAME = "headspace-database"

object DatabaseProvider {
    fun provideRoomDatabase(application: Application): HeadspaceRoomDatabase {
        return Room.databaseBuilder(application, HeadspaceRoomDatabase::class.java,
            DATABASE_NAME
        )
            .fallbackToDestructiveMigration()
            .build()
    }
}

@Database(entities = [FeatureModel::class], version = 1)
abstract class HeadspaceRoomDatabase : RoomDatabase() {
    abstract fun featureTableDao(): FeatureTableDao
}
