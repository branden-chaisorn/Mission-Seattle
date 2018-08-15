package com.test.bchaisorn.missionseattle.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Data class for the Icon object
 *
 * @param prefix : prefix for the venue URL
 * @param suffix : suffix for the venue URL
 */
@Parcelize
data class Icon(val prefix: String, val suffix: String): Parcelable