package com.example.hsexercise.common

import com.example.hsexercise.database.FeatureModel
import com.example.hsexercise.database.FeatureTableDao
import com.example.hsexercise.networking.models.GetPhotoListResponseItem
import com.example.hsexercise.networking.services.PhotoListService
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

interface PhotoListRepo: BaseRepo {

    fun photosObservable(): Observable<List<GetPhotoListResponseItem>>

}

class PhotoListRepoImpl(
    val photoListService: PhotoListService,
    val featureDao: FeatureTableDao
) : PhotoListRepo {

    private val disposables = CompositeDisposable()

    init {
        photoListService
            .getImageList()
            .subscribeOn(Schedulers.io())
            .subscribeBy(
                onNext = { thePhotoList ->
                    val featureList = thePhotoList.map { thePhotoItem ->
                        FeatureModel(
                            id = thePhotoItem.id,
                            author = thePhotoItem.author,
                            url = thePhotoItem.downloadUrl,
                            width = thePhotoItem.width,
                            height = thePhotoItem.height
                        )
                    }

                    featureDao.insertAll(featureList)
                },
                onError = {
                    it.printStackTrace()
                }
            )
            .addTo(disposables)
    }


    override fun photosObservable(): Observable<List<GetPhotoListResponseItem>> {
        return featureDao
            .getAll()
            .map { theFeatures ->
                theFeatures.map { theFeature ->
                    GetPhotoListResponseItem(
                        id = theFeature.id,
                        author = theFeature.author,
                        downloadUrl = theFeature.url,
                        height = theFeature.height,
                        width = theFeature.width,
                        url = theFeature.url
                    )
                }
            }
    }


    override fun dispose() {
        disposables.clear()
    }



}