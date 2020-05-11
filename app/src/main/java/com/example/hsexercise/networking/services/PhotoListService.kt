package com.example.hsexercise.networking.services

import com.example.hsexercise.networking.models.GetPhotoListResponse
import io.reactivex.Observable
import retrofit2.http.GET

interface PhotoListService {

    @GET("/v2/list")
    fun getImageList(): Observable<GetPhotoListResponse>

}