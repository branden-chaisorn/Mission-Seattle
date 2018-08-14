package com.test.bchaisorn.missionseattle.Network

import android.location.Location.distanceBetween
import com.google.android.gms.maps.model.LatLng
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonParseException
import com.test.bchaisorn.missionseattle.Models.Location
import java.lang.reflect.Type

/** Custom TypingIndicatorEvent Deserializer. This is needed because we need some form of UID for each event and due
to payload size restrictions, we have to add our own locally.**/

class LocationDeserializer : JsonDeserializer<Location> {

  @Throws(JsonParseException::class)
  override fun deserialize(
    json: JsonElement,
    typeOfT: Type,
    context: JsonDeserializationContext): Location {
    val lat = json.asJsonObject.get(LAT_KEY).asDouble
    val lng = json.asJsonObject.get(LNG_KEY).asDouble

    val distance = FloatArray(1)
    distanceBetween(SEATTLE_LAT, SEATTLE_LNG, lat, lng, distance)

    return Location(
      json.asJsonObject.get(ADDRESS_KEY)?.asString,
      LatLng(lat, lng),
      distance[0] // distance in meters, always take the first value since that returns distance
    )

  }

  companion object {
    private const val LAT_KEY = "lat"
    private const val LNG_KEY = "lng"
    private const val SEATTLE_LAT = 47.606200
    private const val SEATTLE_LNG = -122.332100
    private const val ADDRESS_KEY = "address"
  }

}