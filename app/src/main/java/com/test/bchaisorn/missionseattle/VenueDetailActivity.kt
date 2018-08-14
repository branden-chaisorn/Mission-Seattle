package com.test.bchaisorn.missionseattle

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.squareup.picasso.Picasso
import com.test.bchaisorn.missionseattle.Models.Venue
import com.test.bchaisorn.missionseattle.R.string.seattle_marker_title
import com.test.bchaisorn.missionseattle.Storage.FavoriteVenueStore
import kotlinx.android.synthetic.main.activity_venue_detail.*
import kotlinx.android.synthetic.main.venue_item_layout.*
import javax.inject.Inject

class VenueDetailActivity : AppCompatActivity(), OnMapReadyCallback {

  private lateinit var venue: Venue

  lateinit var map: GoogleMap

  @Inject
  lateinit var favoriteVenueStore: FavoriteVenueStore

  private lateinit var mapView: MapView

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_venue_detail)

    (application as MainApplication).applicationComponent().inject(this)

    mapView = this.findViewById(R.id.map_detail)
    mapView.onCreate(savedInstanceState)
    mapView.getMapAsync(this)

    venue =  intent.extras.getParcelable(VENUE_EXTRA) as Venue

    venue_detail_name.text = venue.name
    venue_detail_distance.text = getString(R.string.venue_detail_distance_text, venue.location.distanceToSeattle)
    venue_detail_url.text = venue.url
    venue_detail_address.text = getString(R.string.venue_detail_address_text, venue.location.address)
    if (!venue.categories.isEmpty()) {
      venue_detail_category.text = getString(R.string.venue_detail_category_text, venue.categories[0].name)

    }
  }

  override fun onMapReady(googleMap: GoogleMap) {
    map = googleMap

    // Add a marker in Sydney and move the camera
    val seattle = LatLng(SEATTLE_LAT, SEATTLE_LNG)
    map.addMarker(MarkerOptions().position(seattle).title(getString(seattle_marker_title)))
    map.addMarker(MarkerOptions().position(venue.location.latLng).title(venue.name)).showInfoWindow()
    map.moveCamera(CameraUpdateFactory.newLatLngZoom(seattle, 14f))

  }

  fun toggleFavoriteVenue(): Boolean {
    if (!favoriteVenueStore.containsVenue(venue.id)) {
      favoriteVenueStore.addVenue(venue.id)
      return true
    } else {
      favoriteVenueStore.removeVenue(venue.id)
      return false
    }
  }

  override fun onCreateOptionsMenu(menu: Menu): Boolean {
    menuInflater.inflate(R.menu.detail_menu, menu)
    val item = menu.findItem(R.id.favorite_icon)

    if (favoriteVenueStore.containsVenue(venue.id)) {
      item.icon = getDrawable(R.drawable.ic_baseline_favorite_24px)
    }
    return true
  }

  override  fun onOptionsItemSelected(item: MenuItem): Boolean {
    if (item.itemId == R.id.favorite_icon) {
      if (toggleFavoriteVenue()) {
        item.setIcon(R.drawable.ic_baseline_favorite_24px)
      } else {
        item.setIcon(R.drawable.ic_baseline_favorite_border_24px)
      }
      return true
    }
    return super.onOptionsItemSelected(item)
  }

  companion object {
    const val VENUE_EXTRA = "venue"
    private const val SEATTLE_LAT = 47.606200
    private const val SEATTLE_LNG = -122.332100
  }
}
