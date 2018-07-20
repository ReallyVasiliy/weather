# ReallyWeather

An simple, hackathon-style, reactive Android weather app. Consumes the OpenWeatherMap API. 

<p align="center">
  <img src="https://user-images.githubusercontent.com/7144594/43004630-5da91ab0-8bfe-11e8-9e91-435ba3f6e7c8.png" width="250">
  <img src="https://user-images.githubusercontent.com/7144594/43004621-56c57b4e-8bfe-11e8-928c-fc99fa935be2.png" width="250">
</p>

## Features overview:
- Map displaying the current location and current weather
- 16-day forecast, and separate details screen for each day
- Required permission checks and rationale
- Unit and UI tests

## Acknowledgements:
The boilerplate used in this project is [android-starter](https://github.com/androidstarters/android-starter)

## This project uses:
- [ReactiveLocation](https://github.com/mcharmas/Android-ReactiveLocation)
- [RxJava2](https://github.com/ReactiveX/RxJava) and [RxAndroid](https://github.com/ReactiveX/RxAndroid)
- [Retrofit](http://square.github.io/retrofit/) / [OkHttp](http://square.github.io/okhttp/)
- [Gson](https://github.com/google/gson)
- [Dagger 2](http://google.github.io/dagger/)
- [Butterknife](https://github.com/JakeWharton/butterknife)
- [Google Play Services](https://developers.google.com/android/guides/overview)
- [Timber](https://github.com/JakeWharton/timber)
- [Glide 3](https://github.com/bumptech/glide)
- [Espresso](https://google.github.io/android-testing-support-library/) for UI tests
- [Robolectric](http://robolectric.org/) for framework specific unit tests
- [Mockito](http://mockito.org/)
- [Checkstyle](http://checkstyle.sourceforge.net/), [PMD](https://pmd.github.io/) and [Findbugs](http://findbugs.sourceforge.net/) for code analysis

## Requirements:
- [OpenWeatherMap API key](https://openweathermap.org/appid)

## Getting started:

Before you build, please first supply your OpenWeatherMap API key (`OWMApiKey`) into `gradle.properties`

## Testing

To run **unit** tests on your machine:

```sh
./gradlew test
```
