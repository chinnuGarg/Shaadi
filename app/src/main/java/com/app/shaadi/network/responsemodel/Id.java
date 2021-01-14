package com.app.shaadi.network.responsemodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Class to parse the Id class from the Random user api
 */
public class Id {
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("value")
    @Expose
    private Object value;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}