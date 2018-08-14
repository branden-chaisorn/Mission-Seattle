package com.test.bchaisorn.missionseattle

import android.app.Application
import com.test.bchaisorn.missionseattle.Dagger.ApplicationComponent
import com.test.bchaisorn.missionseattle.Dagger.ApplicationModule
import com.test.bchaisorn.missionseattle.Dagger.DaggerApplicationComponent

class MainApplication : Application() {

  private lateinit var applicationComponent: ApplicationComponent

  override fun onCreate() {
    super.onCreate()

    applicationComponent = DaggerApplicationComponent
      .builder()
      .applicationModule(ApplicationModule(this))
      .build()
  }

  fun applicationComponent(): ApplicationComponent {
    return applicationComponent
  }
}