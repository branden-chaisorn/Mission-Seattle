package com.test.bchaisorn.missionseattle.dagger

import com.google.gson.Gson
import com.test.bchaisorn.missionseattle.MainActivity
import com.test.bchaisorn.missionseattle.network.NetworkService
import com.test.bchaisorn.missionseattle.storage.FavoriteVenueStore
import com.test.bchaisorn.missionseattle.VenueDetailActivity
import dagger.Component
import okhttp3.OkHttpClient
import javax.inject.Singleton


@Singleton @Component(modules = [(ApplicationModule::class)])
interface ApplicationComponent {
  fun networkService(): NetworkService
  fun favoriteVenueStore(): FavoriteVenueStore
  fun gson(): Gson
  fun okHttpClient(): OkHttpClient

  fun inject(mainActivity: MainActivity)
  fun inject(venueDetailActivity: VenueDetailActivity)
}