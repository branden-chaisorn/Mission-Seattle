package com.test.bchaisorn.missionseattle.network

import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException


internal class AuthorizationInterceptor : Interceptor {
  @Throws(IOException::class)
  override fun intercept(chain: Interceptor.Chain): Response {
    val original = chain.request()
    val originalHttpUrl = original.url()

    val url = originalHttpUrl.newBuilder()
      .addQueryParameter("client_id", CLIENT_ID)
      .addQueryParameter("client_secret", CLIENT_SECRET)
      .addQueryParameter("v", API_VERSION)
      .build()

    val requestBuilder = original.newBuilder().url(url)
    val request = requestBuilder.build()
    return chain.proceed(request)
  }

  companion object {
    private const val CLIENT_ID = "JJCTUQTRFFYUYDC51QVBWD4Y4KEZJNRB1ZEHMX5S2YEKWFPP"
    private const val CLIENT_SECRET = "XHYNDFOOGBE4N3MQBSOISZBRW105AOFREERS3QDQLZ0JGLKX"
    private const val API_VERSION = "20180401"
  }
}