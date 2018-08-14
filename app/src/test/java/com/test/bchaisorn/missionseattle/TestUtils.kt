package com.test.bchaisorn.missionseattle

import java.io.File

class TestUtils {
  /**
   * Helper function which will load JSON from
   * the path specified
   *
   * @param path : Path of JSON file
   * @return json : JSON from file at given path
   */
  fun getJson(path: String): String {
    val uri = this.javaClass.classLoader.getResource(path)
    val file = File(uri.path)
    return String(file.readBytes())
  }
}