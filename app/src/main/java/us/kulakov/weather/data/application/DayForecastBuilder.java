package us.kulakov.weather.data.application;

public class DayForecastBuilder {
    private String title;
    private String description;
    private String imageURL;
    private Double tempMin;
    private Double tempMax;
    private Double humidity;
    private Long timestamp;

    public DayForecastBuilder setTitle(String title) {
        this.title = title;
        return this;
    }

    public DayForecastBuilder setDescription(String description) {
        this.description = description;
        return this;
    }

    public DayForecastBuilder setImageURL(String imageURL) {
        this.imageURL = imageURL;
        return this;
    }

    public DayForecastBuilder setTempMin(Double tempMin) {
        this.tempMin = tempMin;
        return this;
    }

    public DayForecastBuilder setTempMax(Double tempMax) {
        this.tempMax = tempMax;
        return this;
    }

    public DayForecastBuilder setHumidity(Double humidity) {
        this.humidity = humidity;
        return this;
    }

    public DayForecastBuilder setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public DayForecast createDayForecast() {
        return new DayForecast(title, description, imageURL, tempMin, tempMax, humidity, timestamp);
    }

}