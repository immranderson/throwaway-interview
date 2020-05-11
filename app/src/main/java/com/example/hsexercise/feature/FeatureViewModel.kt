package com.example.hsexercise.feature

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.hsexercise.providers.FeatureRepoProvider
import com.example.hsexercise.networking.models.GetPhotoListResponseItem
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.Subject

class FeatureViewModel(
    application: Application
) : ViewModel() {

    private val disposables = CompositeDisposable()

    private val _loadingState: Subject<FeatureLoadingState> = BehaviorSubject.create()

    class Factory(private val application: Application) :
        ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>) = FeatureViewModel(application) as T
    }

    init {

        //Under normal circumstances I would have provided this dependency directly through Dagger2
        FeatureRepoProvider
            .provideFeatureRepo(application)
            .photosObservable()
            .doOnSubscribe {
                _loadingState.onNext(FeatureLoadingState.Loading)
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onNext = { photoListItems ->
                    val photoListViewHolderModels = photoListItems?.map { theItem: GetPhotoListResponseItem ->
                        ItemRow(
                            authorName = theItem.author,
                            xDimen = "${theItem.width}",
                            yDimen = "${theItem.height}",
                            imageUrl = theItem.downloadUrl
                        )
                    }

                    if (photoListViewHolderModels.isNullOrEmpty()) {
                        _loadingState.onNext(FeatureLoadingState.Empty)
                    } else {
                        _loadingState.onNext(
                            FeatureLoadingState.Success(
                                FeatureListViewState(
                                    items = photoListViewHolderModels
                                )
                            )
                        )
                    }
                },
                onError = {
                    _loadingState.onNext(FeatureLoadingState.Error)
                    it.printStackTrace()
                }
            )
            .addTo(disposables)

    }

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }

    fun loadingStateObservable(): Observable<FeatureLoadingState> = _loadingState

    sealed class FeatureLoadingState {
        object Loading : FeatureLoadingState()
        object Error: FeatureLoadingState()
        object Empty: FeatureLoadingState()
        data class Success(val featureListViewState: FeatureListViewState): FeatureLoadingState()
    }
}
