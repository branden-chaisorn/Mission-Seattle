package com.test.bchaisorn.missionseattle.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Data class for the Venue object
 *
 * @param name : name of the venue
 * @param id : unique id of the venue
 * @param location : Location object created from the venue
 * @param url : Location object created from the venue
 */

@Parcelize
data class Venue (val name: String, val id: String, val location: Location, val url: String?, val categories: List<Category>): Parcelable