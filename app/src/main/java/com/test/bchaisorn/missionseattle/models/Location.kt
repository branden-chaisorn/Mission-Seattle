package com.test.bchaisorn.missionseattle.models

import android.os.Parcelable
import com.google.android.gms.maps.model.LatLng
import kotlinx.android.parcel.Parcelize

/**
 * Data class for the Location Object
 *
 * @param address : Address of the venue, can be null
 * @param latLng : LatLng, latitude and longitude value of the venue
 * @param distanceToSeattle : distance from the center of Seattle to the venue
 */

@Parcelize
data class Location (val address: String?, val latLng: LatLng, val distanceToSeattle: Float): Parcelable