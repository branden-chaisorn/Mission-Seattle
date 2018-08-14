package com.test.bchaisorn.missionseattle.Models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Venue (val name: String, val id: String, val location: Location, val url: String?, val categories: List<Category>): Parcelable