package com.test.bchaisorn.missionseattle.Network

import com.test.bchaisorn.missionseattle.Models.BaseResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.QueryMap


interface NetworkService {

    @GET("v2/venues/search")
    fun getVenues(@QueryMap options: Map<String, String>): Observable<BaseResponse>

}