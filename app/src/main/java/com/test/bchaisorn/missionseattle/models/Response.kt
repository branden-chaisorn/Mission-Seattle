package com.test.bchaisorn.missionseattle.models

/**
 * Data class for the Basic Response object
 *
 * @param venues : list of venue objects generated from the json request
 */
data class Response(val venues: List<Venue>)