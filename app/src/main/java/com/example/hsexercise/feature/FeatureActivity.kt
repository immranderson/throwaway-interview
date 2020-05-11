package com.example.hsexercise.feature

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hsexercise.common.BaseActivity
import com.example.hsexercise.databinding.ActivityFeatureBinding
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

class FeatureActivity : BaseActivity<FeatureViewModel>() {

    private val disposables = CompositeDisposable()
    override val viewModelClass = FeatureViewModel::class.java
    private val adapter = ItemAdapter()
    private lateinit var viewBinding: ActivityFeatureBinding

    override fun provideViewModelFactory() = FeatureViewModel.Factory(this.application)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityFeatureBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        viewBinding.featureRecyclerView.adapter = adapter
        viewBinding.featureRecyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
    }

    override fun onViewLoad(savedInstanceState: Bundle?) {

        viewModel
            .loadingStateObservable()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onNext = { theLoadingState ->

                    when (theLoadingState) {

                        FeatureViewModel.FeatureLoadingState.Loading -> {
                            Log.d(TAG, "LOADING")
                            viewBinding.loadingProgressBar.visibility = View.VISIBLE
                            viewBinding.errorTextView.visibility = View.GONE
                        }

                        FeatureViewModel.FeatureLoadingState.Error -> {
                            Log.d(TAG, "ERROR")
                            viewBinding.loadingProgressBar.visibility = View.GONE
                            viewBinding.errorTextView.visibility = View.VISIBLE
                            viewBinding.errorTextView.text = "There was an error."
                        }

                        FeatureViewModel.FeatureLoadingState.Empty -> {
                            Log.d(TAG, "EMPTY")
                            viewBinding.loadingProgressBar.visibility = View.GONE
                            viewBinding.errorTextView.visibility = View.VISIBLE
                            viewBinding.errorTextView.text = "Sorry, there were no items."
                        }

                        is FeatureViewModel.FeatureLoadingState.Success -> {
                            Log.d(TAG, "SUCCESS")
                            viewBinding.loadingProgressBar.visibility = View.GONE
                            viewBinding.errorTextView.visibility = View.GONE
                            adapter.update(theLoadingState.featureListViewState.items)
                        }

                    }

                },
                onError = {
                    viewBinding.errorTextView.text = "There was an error."
                    it.printStackTrace()
                }
            )
            .addTo(disposables)


    }

    override fun onDestroy() {
        super.onDestroy()
        disposables.clear()
    }

    companion object {
        val TAG = FeatureActivity::class.java.simpleName
    }
}
