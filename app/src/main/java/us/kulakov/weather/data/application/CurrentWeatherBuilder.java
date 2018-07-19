package us.kulakov.weather.data.application;

public class CurrentWeatherBuilder {
    private String title;
    private LatLong latLong;
    private Double temp;
    private Double humidity;
    private String locationName;
    private String imageURL;

    public CurrentWeatherBuilder setTitle(String title) {
        this.title = title;
        return this;
    }

    public CurrentWeatherBuilder setLatLong(LatLong latLong) {
        this.latLong = latLong;
        return this;
    }

    public CurrentWeatherBuilder setTemp(Double temp) {
        this.temp = temp;
        return this;
    }

    public CurrentWeatherBuilder setHumidity(Double humidity) {
        this.humidity = humidity;
        return this;
    }

    public CurrentWeatherBuilder setLocationName(String locationName) {
        this.locationName = locationName;
        return this;
    }

    public CurrentWeatherBuilder setImageURL(String imageURL) {
        this.imageURL = imageURL;
        return this;
    }

    public CurrentWeather createCurrentWeather() {
        return new CurrentWeather(title, latLong, temp, humidity, locationName, imageURL);
    }
}