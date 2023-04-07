# Foodie
Foodie is a simple application displaying restaurants nearby the user.

## Screenshots

<p>
  <img src="/screenshots/splash_app_bar.png" width="200" />
  <img src="/screenshots/restaurants_page.png" width="200" />
  <img src="/screenshots/retry_view.png" width="200" />
</p>

## Features

### Restaurants page view

Package [`com.example.foodie.pages.restaurantspage`][10]

[`RestaurantsPageView`][11] displays restaurants page with a vertical list of restaurants.
[`RestaurantsPageViewModel`][12] controls this view by exposing UI state and UI event callbacks.

#### Fetching new restaurants page

Each time [`CurrentLocationProvider`][13] provides a new location, the view model sends a request
for new restaurants using [`RestaurantsPageRepository`][14]. On retry click the same request is made.

#### Saving restaurants in favorites

A restaurant can be added to favorites by pressing the heart button. The view model calls
[`FavoritesRepository`][15] to change favorites state and read it.

[10]: app/src/main/java/com/example/foodie/pages/restaurantspage
[11]: app/src/main/java/com/example/foodie/pages/restaurantspage/view/RestaurantsPageView.kt
[12]: app/src/main/java/com/example/foodie/pages/restaurantspage/RestaurantsPageViewModel.kt
[13]: app/src/main/java/com/example/foodie/location/CurrentLocationProvider.kt
[14]: app/src/main/java/com/example/foodie/repository/pages/restaurants/RestaurantsPageRepository.kt
[15]: app/src/main/java/com/example/foodie/repository/favorite/FavoritesRepository.kt

### Global application timer

Package [`com.example.foodie.timer`][20]

[`AppTimer`][21] is global timer for the application that is started for the first time when the
application is initialized in `onCreate` in [`MainActivity`][22]. Since that moment every second
it emits tics in a [`SharedFlow`][23] with strategy [`SharingStarted.Eagerly`][24] so all observers
will get only the most recent tic.

Timer is paused and resumed by [`MainActivity`][22] when the application is backgrounded and
foregrounded to prevent the application from running processes in the background.

Other components can listen to this flow to perform actions that need to be performed repeatedly
in some interval. Example can be found here: [Current location provider][25].

#### Testing

[Turbine][26] was used for testing [`Flow`][27].

[20]: app/src/main/java/com/example/foodie/timer
[21]: app/src/main/java/com/example/foodie/timer/AppTimer.kt
[22]: app/src/main/java/com/example/foodie/MainActivity.kt
[23]: https://kotlinlang.org/api/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/-shared-flow/
[24]: https://kotlinlang.org/api/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/-sharing-started/-companion/-eagerly.html
[25]: #current-location-provider
[26]: https://github.com/cashapp/turbine
[27]: https://kotlinlang.org/api/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/-flow/

### Current location provider

Package [`com.example.foodie.location`][30]

[`CurrentLocationProvider`][31] is global provider for the current location of the device.
It listens to [`AppTimer`][32]'s tics and transforms this flow to a flow of locations. New location
is fetched from [`LocationRepository`][33] and emitted only every 10 seconds.

Similarly to [`AppTimer`][32] it exposes [`SharedFlow`][34] of locations which is shared with
the [`SharingStarted.Eagerly`][35] strategy. It also replays the latest value for each new
subscriber, so they don't need to wait until a new location is emitted.

#### Location repository

Currently implementation of this repository is mocked to fixed list of 10 predefined locations.

[30]: app/src/main/java/com/example/foodie/location
[31]: app/src/main/java/com/example/foodie/location/CurrentLocationProvider.kt
[32]: #global-application-timer
[33]: app/src/main/java/com/example/foodie/repository/location/LocationRepository.kt
[34]: https://kotlinlang.org/api/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/-shared-flow/
[35]: https://kotlinlang.org/api/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/-sharing-started/-companion/-eagerly.html

### Splash screen to app bar transition

<img src="/screenshots/splash_app_bar.gif" width="200" />

Package [`com.example.foodie.pages.shared`][40]

[`SplashAppBar`][41] can be both the splash screen and the application top bar. When shown as the
splash screen it displays the initial enter animation created using [`AnimatedVisibility`][42].
When the data is loaded for the first time and the application is initialized then the splash
screen transforms to the app bar using [`MotionLayout`][43].

[40]: app/src/main/java/com/example/foodie/pages/shared
[41]: app/src/main/java/com/example/foodie/pages/shared/SplashAppBar.kt
[42]: https://developer.android.com/jetpack/compose/animation/composables-modifiers#animatedvisibility
[43]: https://developer.android.com/develop/ui/views/animations/motionlayout

### Retry view animations

<img src="/screenshots/retry_view.gif" width="200" />

Package [`com.example.foodie.pages.shared`][50]

[`RetryView`][51] displays an animation transforming the Foodie logo to a broken one. It uses
[`animate*AsState`][52] to move elements and [`Crossfade`][53] to create smooth transition
between the icons.

[50]: app/src/main/java/com/example/foodie/pages/shared
[51]: app/src/main/java/com/example/foodie/pages/shared/RetryView.kt
[52]: https://developer.android.com/jetpack/compose/animation/value-based#animate-as-state
[53]: https://developer.android.com/jetpack/compose/animation/composables-modifiers#crossfade

### Dark theme

<p>
  <img src="/screenshots/restaurants_page_dark.png" width="200" />
  <img src="/screenshots/retry_view_dark.png" width="200" />
</p>

Foodie is also adjusted to work in dark mode. Different color palettes for light and dark mode
are defined in the [`Theme.kt`][60] file.

[60]: app/src/main/java/com/example/foodie/theme/Theme.kt

### Application config

Package [`com.example.foodie.model`][70]

The behaviour of the application can be adjusted by modifying [`AppConfig`][71].

Currently it's hardcoded but ideally it should be received from backend when the application
is launching.

[70]: app/src/main/java/com/example/foodie/model
[71]: app/src/main/java/com/example/foodie/model/AppConfig.kt

### Detekt

The whole project has [detect][80] configured in [`detekt.yml`][81]. It also uses wrapper over
[ktlint][82] provided by detekt as a [`formatting`][83] rule set.

[80]: https://detekt.dev/
[81]: config/detekt/detekt.yml
[82]: https://github.com/pinterest/ktlint
[83]: https://detekt.dev/docs/intro#adding-more-rule-sets

## Contributions

Restaurant data is provided by [Wolt][100] API.

[Foodie logo icon][101] was created by [Freepik - Flaticon][102].

[Restaurant placeholder][103] was created by [Ali Inay][104].

[100]: https://wolt.com/
[101]: app/src/main/res/drawable/foodie_logo.png
[102]: https://www.flaticon.com/free-icons/restaurant
[103]: app/src/main/res/drawable/restaurant_placeholder.jpg
[104]: https://unsplash.com/@inayali