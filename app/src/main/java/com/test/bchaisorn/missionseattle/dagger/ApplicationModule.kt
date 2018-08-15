package com.test.bchaisorn.missionseattle.dagger

import android.app.Application
import android.content.Context
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
class ApplicationModule(application: Application) {
  private val applicationContext: Context

  init {
    applicationContext = application
  }

  @Provides
  @Singleton
  fun provideNetworkService(): NetworkService {
    val httpClient = OkHttpClient.Builder()
      .addNetworkInterceptor(AuthorizationInterceptor())
      .build()

    // Registering custom type adapter instead of using the annotation so that I can implement a test version
    // that does not use the android distanceTo call
    val gsonBuilder = GsonBuilder()
    gsonBuilder.registerTypeAdapter(Location::class.java, LocationDeserializer())

    return Retrofit.Builder()
      .baseUrl(BASE_URL)
      .addConverterFactory(GsonConverterFactory.create(gsonBuilder.create()))
      .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
      .client(httpClient)
      .build()
      .create<NetworkService>(NetworkService::class.java)
  }

  @Provides
  @Singleton
  internal fun provideFavoriteVenueStore(): FavoriteVenueStore {
    return FavoriteVenueStoreImpl(applicationContext.getSharedPreferences(favoriteVenueStoreKey, Context.MODE_PRIVATE))
  }

  companion object {
    private const val favoriteVenueStoreKey = "FavoriteVenueStore"
    private const val BASE_URL = "https://api.foursquare.com"
  }
}
