package com.test.bchaisorn.missionseattle.storage

/**
 * FavoriteVenueStore is a storage interface that will simply store a venueId. I opted for this interface
 * so the implementation can be enhanced without impacting consumers of the store. In the end I went with
 * a simple SharedPreferences implementation but ideally this would be backed by a SQLite implementation or maybe
 * even using Realm
 */

interface FavoriteVenueStore {

  fun addVenue(venueId: String)

  fun removeVenue(venueId: String)

  fun containsVenue(venueId: String): Boolean

}