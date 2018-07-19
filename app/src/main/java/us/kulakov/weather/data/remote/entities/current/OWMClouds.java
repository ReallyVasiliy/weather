package us.kulakov.weather.data.remote.entities.current;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Vasiliy Kulakov on 7/17/18.
 * This file is part of the project "weather".
 * Copyright (c) 2018 Vasiliy Kulakov
 *
 * OWM API model. Generated with help from jsonschema2pojo.org
 */
public class OWMClouds {

    @SerializedName("all")
    @Expose
    private Long all;

    /**
     * No args constructor for use in serialization
     *
     */
    public OWMClouds() {
    }

    /**
     *
     * @param all
     */
    public OWMClouds(Long all) {
        super();
        this.all = all;
    }

    public Long getAll() {
        return all;
    }

    public void setAll(Long all) {
        this.all = all;
    }

    public OWMClouds withAll(Long all) {
        this.all = all;
        return this;
    }

}