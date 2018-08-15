package com.test.bchaisorn.missionseattle

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.annotation.Nullable
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import com.jakewharton.rxbinding2.widget.RxTextView
import com.test.bchaisorn.missionseattle.models.Venue
import com.test.bchaisorn.missionseattle.network.NetworkService
import com.test.bchaisorn.missionseattle.storage.FavoriteVenueStore
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

  private lateinit var venueList: List<Venue>
  private lateinit var layoutManager: RecyclerView.LayoutManager

  @Inject
  lateinit var networkService: NetworkService

  @Inject
  lateinit var favoriteVenueStore: FavoriteVenueStore

  private lateinit var queryTextSub: Disposable

  private lateinit var venueAdapter: VenueAdapter

  override fun onCreate(@Nullable savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    (application as MainApplication).applicationComponent().inject(this)

    layoutManager = LinearLayoutManager(this)
    venueAdapter =  VenueAdapter(this, favoriteVenueStore)
    venueListView.layoutManager = layoutManager
    venueListView.adapter = venueAdapter

    queryVenues()
  }

  public override fun onResume() {
    super.onResume()
    // In the essence of time I opted to go this route to update the UI if you update the favorite status.
    // Ideally, I'd like to update the item only with notifyItemChanged if I had reference to it's index by possibly
    // controlling favorite toggling on the RecyclerView itself
    venueAdapter.notifyDataSetChanged()
  }

  public override fun onPause() {
    super.onPause()
    queryTextSub.dispose()
  }

  private fun queryVenues() {
    queryTextSub = RxTextView.textChanges(queryInputLayout.editText!!)
      .subscribeOn(Schedulers.io())
      .observeOn(AndroidSchedulers.mainThread())
      .debounce(DEBOUNCE_TIME, TimeUnit.MILLISECONDS)
      .flatMap { query ->
        networkService
          .getVenues(getQueryOptionMap(query.toString()))
          .subscribeOn(Schedulers.io())
          .observeOn(AndroidSchedulers.mainThread())
      }
      .subscribe ({ baseResponse ->
        val venues = baseResponse.response.venues
        venueList = venues
        venueAdapter.setVenues(venues)
        venueAdapter.notifyDataSetChanged()
      }, { e -> Log.e(TAG, e.printStackTrace().toString())})
  }

  private fun getQueryOptionMap(query: String): Map<String, String> {
    return mapOf(
      "query" to query,
      "near" to NEAR,
      "limit" to LIMIT)
  }

  fun showMap(view: View) {
    val intent = Intent(this, MapActivity::class.java).putExtra(MapActivity.VENUE_LIST_EXTRA, venueList.toTypedArray())
    startActivity(intent)
  }

  companion object {
    private const val NEAR = "Seattle,+WA"
    private const val LIMIT = "20"
    private const val DEBOUNCE_TIME = 200L

    val TAG = MainActivity::class.java.simpleName!!
  }
}
