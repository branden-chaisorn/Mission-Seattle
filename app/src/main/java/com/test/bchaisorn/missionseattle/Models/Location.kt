package com.test.bchaisorn.missionseattle.Models

import android.os.Parcelable
import com.google.android.gms.maps.model.LatLng
import kotlinx.android.parcel.Parcelize

/**
 *
 */

@Parcelize
data class Location (val address: String?, val latLng: LatLng, val distanceToSeattle: Float): Parcelable