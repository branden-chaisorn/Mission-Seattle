package com.test.bchaisorn.missionseattle

import android.content.Context
import android.content.Intent
import android.provider.Settings.Global.getString
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.ViewHolder
import android.view.View
import android.view.ViewGroup
import com.test.bchaisorn.missionseattle.Models.Venue
import android.view.LayoutInflater
import kotlinx.android.synthetic.main.venue_item_layout.view.*
import com.squareup.picasso.Picasso
import com.test.bchaisorn.missionseattle.Storage.FavoriteVenueStore

class VenueAdapter(private var context: Context, private val favoriteVenueStore: FavoriteVenueStore) : RecyclerView.Adapter<VenueAdapter.VenueViewHolder>() {

  private var venues = emptyList<Venue>()

  fun setVenues(venues: List<Venue>) {
    this.venues = venues
  }

  override fun onBindViewHolder(holder: VenueViewHolder, position: Int) {
    val currentVenue = venues[position]
    holder.venueItemLayout.venue_item_name.text = currentVenue.name
    holder.venueItemLayout.venue_item_distance.text = context.getString(R.string.venue_item_distance_text, currentVenue.location.distanceToSeattle)
    holder.venueItemLayout.setOnClickListener {
      navigateToVenueDetails(currentVenue)
    }

    if (favoriteVenueStore.containsVenue(currentVenue.id)) {
      holder.venueItemLayout.favorite_icon.visibility = View.VISIBLE
    } else {
      holder.venueItemLayout.favorite_icon.visibility = View.INVISIBLE
    }

    if (!currentVenue.categories.isEmpty()) {
      holder.venueItemLayout.venue_item_category.text = currentVenue.categories[0].name

      Picasso.get()
        .load(currentVenue.categories[0].icon.prefix + IMAGE_MODIFIER + currentVenue.categories[0].icon.suffix)
        .error(R.drawable.default_bg_64)
        .into(holder.venueItemLayout.venue_icon)
    }
  }

  private fun navigateToVenueDetails(venue: Venue) {
    val intent = Intent(context, VenueDetailActivity::class.java).putExtra(VenueDetailActivity.VENUE_EXTRA, venue)
    context.startActivity(intent)
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VenueViewHolder {
    val v = LayoutInflater.from(parent.context)
      .inflate(R.layout.venue_item_layout, parent, false)
    return VenueViewHolder(v)
  }

  override fun getItemCount(): Int {
    return venues.size
  }

  class VenueViewHolder(itemView: View) : ViewHolder(itemView) {
    var venueItemLayout = itemView
  }

  companion object {
    private const val IMAGE_MODIFIER = "bg_64"
  }

}