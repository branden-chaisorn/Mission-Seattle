package com.test.bchaisorn.missionseattle.network

import com.test.bchaisorn.missionseattle.models.BaseResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.QueryMap


interface VenueApi {

    @GET("v2/venues/search")
    fun getVenues(@QueryMap options: Map<String, String>): Observable<BaseResponse>

}