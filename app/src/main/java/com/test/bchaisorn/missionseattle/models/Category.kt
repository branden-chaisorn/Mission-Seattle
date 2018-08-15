package com.test.bchaisorn.missionseattle.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Data class for the Category object
 *
 * @param name : name of the category
 * @param icon : icon image class for the category
 */
@Parcelize
data class Category(val name: String, val icon: Icon): Parcelable