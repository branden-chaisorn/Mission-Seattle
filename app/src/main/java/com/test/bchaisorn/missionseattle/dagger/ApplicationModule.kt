package com.test.bchaisorn.missionseattle.dagger

import android.app.Application
import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.test.bchaisorn.missionseattle.models.Location
import com.test.bchaisorn.missionseattle.network.AuthorizationInterceptor
import com.test.bchaisorn.missionseattle.network.LocationDeserializer
import com.test.bchaisorn.missionseattle.network.NetworkService
import com.test.bchaisorn.missionseattle.storage.FavoriteVenueStore
import com.test.bchaisorn.missionseattle.storage.FavoriteVenueStoreImpl
import javax.inject.Singleton

import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory


@Module
class ApplicationModule(private val application: Application) {

  @Provides
  @Singleton
  fun providesGson(): Gson {
    return GsonBuilder().registerTypeAdapter(Location::class.java, LocationDeserializer()).create()
  }

  @Provides
  @Singleton
  fun providesOkHttpClient(): OkHttpClient {
    return OkHttpClient.Builder()
      .addNetworkInterceptor(AuthorizationInterceptor())
      .build()
  }

  @Provides
  @Singleton
  fun provideNetworkService(gson: Gson, httpClient: OkHttpClient): NetworkService {

    return Retrofit.Builder()
      .baseUrl(BASE_URL)
      .addConverterFactory(GsonConverterFactory.create(gson))
      .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
      .client(httpClient)
      .build()
      .create<NetworkService>(NetworkService::class.java)
  }

  @Provides
  @Singleton
  internal fun provideFavoriteVenueStore(): FavoriteVenueStore {
    return FavoriteVenueStoreImpl(application.getSharedPreferences(favoriteVenueStoreKey, Context.MODE_PRIVATE))
  }

  companion object {
    private const val favoriteVenueStoreKey = "FavoriteVenueStore"
    private const val BASE_URL = "https://api.foursquare.com"
  }
}
