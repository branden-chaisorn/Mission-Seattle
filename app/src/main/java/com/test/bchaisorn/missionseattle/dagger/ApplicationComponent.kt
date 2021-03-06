package com.test.bchaisorn.missionseattle.dagger

import com.test.bchaisorn.missionseattle.MainActivity
import com.test.bchaisorn.missionseattle.VenueDetailActivity
import dagger.Component
import javax.inject.Singleton


@Singleton @Component(modules = [ApplicationModule::class])
interface ApplicationComponent {
  fun inject(mainActivity: MainActivity)
  fun inject(venueDetailActivity: VenueDetailActivity)
}