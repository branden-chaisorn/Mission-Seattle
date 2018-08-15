package com.test.bchaisorn.missionseattle

import com.google.android.gms.maps.model.LatLng
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonParseException
import com.test.bchaisorn.missionseattle.models.Location
import java.lang.reflect.Type

/**
 * TestLocationDeserializer is a test implementation of the LocationDeserializer. This is used here because of the use
 * of Android's distanceTo method in the real one, but we would like to abstract that logic out of our unit tests.
 */

class TestLocationDeserializer : JsonDeserializer<Location> {

  @Throws(JsonParseException::class)
  override fun deserialize(
    json: JsonElement,
    typeOfT: Type,
    context: JsonDeserializationContext): Location {
    val lat = json.asJsonObject.get(LAT_KEY).asDouble
    val lng = json.asJsonObject.get(LNG_KEY).asDouble

    return Location(
      json.asJsonObject.get(ADDRESS_KEY)?.asString,
      LatLng(lat, lng),
      10f // distance in meters, just a test value
    )
  }

  companion object {
    private const val LAT_KEY = "lat"
    private const val LNG_KEY = "lng"
    private const val ADDRESS_KEY = "address"
  }
}
