package com.test.bchaisorn.missionseattle

import com.test.bchaisorn.missionseattle.network.AuthorizationInterceptor
import com.test.bchaisorn.missionseattle.network.NetworkService
import io.reactivex.observers.TestObserver
import junit.framework.Assert.assertEquals
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import com.google.android.gms.maps.model.LatLng
import com.google.gson.*
import com.test.bchaisorn.missionseattle.models.*


@RunWith(JUnit4::class)
class DeserializationTest {

  lateinit var mockServer : MockWebServer
  lateinit var networkService : NetworkService

  @Before
  @Throws fun setUp() {
    mockServer = MockWebServer()
    mockServer.start(8080)

    // Normally I would set these things up via a test dagger module and component and inject them here, but for the sake
    // of speed I've opted to just set them up here for this situation
    val okHttpClient = OkHttpClient.Builder()
      .addInterceptor(AuthorizationInterceptor())
      .build()

    val gsonBuilder = GsonBuilder()
    gsonBuilder.registerTypeAdapter(Location::class.java, TestLocationDeserializer())

    val retrofit = Retrofit.Builder()
      .baseUrl("http://localhost:8080/")
      .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
      .addConverterFactory(GsonConverterFactory.create(gsonBuilder.create()))
      .client(okHttpClient)
      .build()

    networkService = retrofit.create(NetworkService::class.java)
  }

  @Test
  fun testReturnsBaseResponse() {
    val testObserver = TestObserver<BaseResponse>()

    val mockResponse = MockResponse()
      .setResponseCode(200)
      .setBody(TestUtils().getJson("testResponse.json"))
    // Enqueue request
    mockServer.enqueue(mockResponse)

    // Call the API
    networkService.getVenues(mapOf(
      "query" to "potato",
      "near" to "Seattle",
      "limit" to "20"
    )).subscribe(testObserver)

    testObserver.awaitTerminalEvent(2, TimeUnit.SECONDS)
    testObserver.assertNoErrors()
    testObserver.assertValueCount(1)

    val retrievedVenus = testObserver.values()[0].response.venues

    val expectedLocation = Location(address = "1508 7th Ave", latLng = LatLng(47.61179097, 122.333508), distanceToSeattle = 10f)
    val expectedIcon = Icon(prefix = "https://ss3.4sqi.net/img/categories_v2/shops/financial_", suffix = ".png")
    val expectedCategory = Category(name = "ATM", icon = expectedIcon)
    val expectedVenue = Venue(id = "593e1dbefc9e940adf207cbe", name = "U.S. Bank ATM", url = null, location = expectedLocation, categories = listOf(expectedCategory))

    assertEquals(expectedVenue, retrievedVenus[0])
  }

  @Test
  fun testReturnsBaseResponseNoAddressNoCategory() {
    val testObserver = TestObserver<BaseResponse>()

    val mockReponse = MockResponse()
      .setResponseCode(200)
      .setBody(TestUtils().getJson("testResponse_noAddress_noCategory.json"))

    mockServer.enqueue(mockReponse)

    // Call the API
    networkService.getVenues(mapOf(
      "query" to "potato",
      "near" to "Seattle",
      "limit" to "20"
    )).subscribe(testObserver)

    testObserver.awaitTerminalEvent(2, TimeUnit.SECONDS)
    testObserver.assertNoErrors()
    testObserver.assertValueCount(1)

    val retrievedVenus = testObserver.values()[0].response.venues

    val expectedLocation = Location(address = null, latLng = LatLng(47.59500262477, 122.33169910087), distanceToSeattle = 10f)
    val expectedVenue = Venue(id = "4fd541fce4b0ba0232ea8690", name = "Seattle", url = null, location = expectedLocation, categories = listOf())

    assertEquals(expectedVenue, retrievedVenus[0])
  }

  @After
  @Throws fun tearDown() {
    mockServer.shutdown()
  }

}