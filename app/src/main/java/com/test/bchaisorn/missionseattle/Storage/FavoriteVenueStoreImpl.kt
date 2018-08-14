package com.test.bchaisorn.missionseattle.Storage

import android.content.SharedPreferences

class FavoriteVenueStoreImpl(private val storePreferences: SharedPreferences) : FavoriteVenueStore {

  override fun addVenue(venueId: String) {
    if (!storePreferences.contains(venueId)) {
      storePreferences.edit().putBoolean(venueId, true).apply()
    }
  }

  override fun removeVenue(venueId: String) {
    if (storePreferences.contains(venueId)) {
      storePreferences.edit().remove(venueId).apply()
    }
  }

  override fun containsVenue(venueId: String): Boolean {
    return storePreferences.contains(venueId)
  }

}