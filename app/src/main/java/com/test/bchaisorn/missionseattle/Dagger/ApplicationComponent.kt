package com.test.bchaisorn.missionseattle.Dagger

import com.test.bchaisorn.missionseattle.MainActivity
import com.test.bchaisorn.missionseattle.Network.NetworkService
import com.test.bchaisorn.missionseattle.Storage.FavoriteVenueStore
import com.test.bchaisorn.missionseattle.VenueDetailActivity
import dagger.Component
import javax.inject.Singleton


@Singleton @Component(modules = [(ApplicationModule::class)])
interface ApplicationComponent {
  fun networkService(): NetworkService
  fun favoriteVenueStore(): FavoriteVenueStore

  fun inject(mainActivity: MainActivity)
  fun inject(venueDetailActivity: VenueDetailActivity)
}