package com.example.hsexercise.providers

import android.app.Application
import com.example.hsexercise.common.PhotoListRepo
import com.example.hsexercise.common.PhotoListRepoImpl

object FeatureRepoProvider {

    fun provideFeatureRepo(
        application: Application
    ): PhotoListRepo {

        return PhotoListRepoImpl(
            photoListService = NetworkProvider.providePhotoListService(),
            featureDao = DatabaseProvider.provideRoomDatabase(
                application
            ).featureTableDao()
        )

    }

}

