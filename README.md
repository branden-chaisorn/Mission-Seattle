# Mission-Seattle

Mission Seattle is a light weight search application that allows users to query
the Four Square API to get information about various venues near the center of Seattle.

## About Creation
This project was built and tested with Android Studio 3.1.3

## Criteria

1. The main screen should display a search input, and should use industry
best practices to perform a typeahead search against the Foursquare API.
2. When a search returns results, these should be displayed in a list format.
Each list item should provide, at a minimum, the name of the place
(e.g., Flitch Coffee), the category of the place (e.g., Coffee Shop), the
icon from the response, the distance from the center of Seattle
(47.6062° N, 122.3321° W) to the place, and whether the place has been
favorited by the user. Clicking a list item should launch the details
screen for that place.
3. When a search returns results, the main screen should include a
Floating Action Button. Clicking the Floating Action Button should
launch a full-screen map with a pin for every search result. Clicking a
pin should show the name of the place on the map, and clicking on the
name should then open the details screen for the given place.
4. The details screen for a place should use a collapsible toolbar layout
to show a map in the upper half of the screen, with two pins -- one, the
location of search result, and the other, the center of Seattle. The
bottom half of the details screen should provide details about the place,
including whether or not the place is favorited, and should include a link
to the place’s website (if it exists). Clicking this link should open an
external Intent to a browser installed on the device.
5. Favorite selections should be changeable, should persist across
launches of the app, and should show correctly on both the main and details screens.
6. The user should be able to navigate between screens according to Android platform conventions.

## How-To
- Run unit tests
    - ./gradlew test
- Run app
    - Copy debug.keystore to your root android folder (~/.android/debug.keystore on Mac) and run via Android studio.

## Assumptions
- Zoom controls are not required.
- I placed the favorite toggling in the action bar on the details screen instead of down on the bottom half
because it felt more natural for it to be there.


## Libraries Used

| Component     | Description   | License  |
| ------------- |:-------------:| -----:|
| [Retrofit](http://square.github.io/retrofit/)        |  Retrofit turns your HTTP API into a Java interface. | [Apache 2.0](http://www.apache.org/licenses/LICENSE-2.0) |
| [Google Maps](https://developers.google.com/maps/documentation/android-sdk/intro)        | SDK to enable the use of Google Maps in Android | [Apache 2.0](http://www.apache.org/licenses/LICENSE-2.0) |
| [Dagger 2](https://github.com/google/dagger)        | A compile-time evolution approach to dependency injection. | [Apache 2.0](https://github.com/google/dagger/blob/master/LICENSE.txt) |
| [RxJava](https://github.com/ReactiveX/RxJava)        | RxJava is a Java VM implementation of Reactive Extensions: a library for composing asynchronous and event-based programs by using observable sequences. | [Apache 2.0](https://github.com/google/dagger/blob/master/LICENSE.txt) |
| [RxBinding](https://github.com/JakeWharton/RxBinding)        | RxJava binding APIs for Android UI widgets from the platform and support libraries. | [Apache 2.0](http://www.apache.org/licenses/LICENSE-2.0) |
| [Picasso](http://square.github.io/picasso/)        |  A powerful image downloading and caching library for Android | [Apache 2.0](http://www.apache.org/licenses/LICENSE-2.0) |
| [MockWebServer](https://github.com/square/okhttp/tree/master/mockwebserver)        |  A scriptable web server for testing HTTP clients | [Apache 2.0](http://www.apache.org/licenses/LICENSE-2.0) |
### Why These Libraries
- Retrofit
    - Retrofit was used here because it provides an extremely easy to use API for handing network requests on Android.
    Also, because of it's wide adoption, there exist many add ons to it that allow for easy JSON parsing, and even
    hooks for RxJava compatibility.
- Dagger 2
    - Dagger 2 was used here in order to prepare for larger development. In a larger app, injecting dependencies
    through Dagger makes using said dependencies much easier. Traditionally one can create a static DI context
    and use this gain DI in classes that can't be traditionally injected. Additionally, setting up Dagger for
    test instances provides an easy way to change dependencies to test implementations so you can mock out things
    you don't necessarily want to test at the time.
- Google Maps
    - The industry standard way to render and use maps on Android.
- RxBinding
    - This library was used because it provides Rx hooks on UI components. In this case we want to handle typing and
    the sending of search events based on user input so using Rx to do so made sense given the nature of using streams
    of data
- Picasso
    - Picasso was used because of it's ease of use API, and also due to the fact that it provides a lightweight
    way to load images from urls.
- MockWebServer
    - MockWebserver was used here in order to fake an actual web server. This allows us to test our model
    deserialization in isolation.

## Future Improvements / Considerations

- I stayed away from doing any sort of system architecture here. Implementing any one of the M* (MVP, MVVM, MVI...)
architectures makes sense for a larger scale application in that separating concerns there makes for much
easier testing and readability. I traditionally would not have so much logic in places such as MainActivity as
it clouds system logic, as well as makes testing business logic there require Android system components.
- Loading the map before possibly having a list of venues a real situation that can occur here. In this case,
I'm just rendering an empty map with the center of Seattle marked. However, in a production like environment I would
potentially make some sort of cache that would house all the venues from a given query. That way, no matter what's loaded
or not, we could pull from the cache and show something or nothing if the cache was completely empty. Whether that would be an in-memory cache or persisted would depend
on factors such as venue list size, etc.
- Adding zoom support and more controls with the map is something I would like to do given more time. Right now
I passed on this because users still have the option to view Google Maps via the button on the map. At that time
the user can get more information about the location via the map.
- Given more time and a larger scale app I would really like to use a more formal storage format such as
Sqlite or even Realm.
- I did not write any functional tests here give the relatively straightforward UI of the app. With a production
app I would have functional tests on UI components, especially if they were reactive to ensure that things were being
displayed when I expect them to.
- I would like to show more on the venue detail screen as well as clean it up to make it more visual appealing. There I would query for the venue's images if they were
available and display them in a swipeable view pager.
- Fully automated build system. On a larger scale having thorough testing on the
whole code base. I would additionally spend time to generate some code coverage metrics
(though that metric and chasing 100% isn't always a worth while goal).
- I had trouble with finding any venues with URLs from the search api. In the future, if the details page needed
to be more fleshed out, I would query the venue details API foursquare has and display information from that response
in the venue detail section. The venue details API seems to have possibly a different URL than one that would be given from
the search api.
- Find a replacement for Findbugs for Kotlin to find simple code mistakes.
- Make more improvements on the venue detail UI
- Add Crashlytics or some sort of analytics around crashes so debugging would be easier
