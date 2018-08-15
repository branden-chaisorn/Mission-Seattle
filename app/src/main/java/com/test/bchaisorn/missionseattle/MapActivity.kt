package com.test.bchaisorn.missionseattle

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.test.bchaisorn.missionseattle.models.Venue

class MapActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnInfoWindowClickListener {

  private lateinit var seattleMap: GoogleMap
  private var venueMap = mutableMapOf<String, Venue>()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_maps)
    if (intent.extras.getParcelableArray(VENUE_LIST_EXTRA) != null) {
      for (venue in intent.extras.getParcelableArray(VENUE_LIST_EXTRA).toList() as List<Venue>) {
        venueMap[venue.name] = venue
      }
    }
    // Obtain the SupportMapFragment and get notified when the map is ready to be used.
    val mapFragment = supportFragmentManager
      .findFragmentById(R.id.map) as SupportMapFragment
    mapFragment.getMapAsync(this)

  }

  override fun onMapReady(googleMap: GoogleMap) {
    seattleMap = googleMap
    seattleMap.setOnInfoWindowClickListener(this)

    val seattle = LatLng(SEATTLE_LAT, SEATTLE_LNG)
    for (venue in venueMap.values) {
      seattleMap.addMarker(MarkerOptions()
        .position(LatLng(venue.location.latLng.latitude, venue.location.latLng.longitude))
        .title(venue.name))
    }
    seattleMap.addMarker(MarkerOptions().position(seattle).title(getString(R.string.seattle_marker_title)))
    seattleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(seattle, DEFAULT_ZOOM_LEVEL))
  }

  override fun onInfoWindowClick(marker: Marker) {
    val selectedVenue = venueMap[marker.title]
    val intent = Intent(this, VenueDetailActivity::class.java).putExtra(VenueDetailActivity.VENUE_EXTRA, selectedVenue)

    startActivity(intent)
  }

  companion object {
    private const val DEFAULT_ZOOM_LEVEL = 13f
    private const val SEATTLE_LAT = 47.606200
    private const val SEATTLE_LNG = -122.332100
    const val VENUE_LIST_EXTRA = "venueList"
  }

}