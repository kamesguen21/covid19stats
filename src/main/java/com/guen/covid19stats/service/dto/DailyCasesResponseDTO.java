
package com.guen.covid19stats.service.dto;

import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "Country",
    "CountryCode",
    "Province",
    "City",
    "CityCode",
    "Lat",
    "Lon",
    "Confirmed",
    "Deaths",
    "Recovered",
    "Active",
    "Date"
})
public class DailyCasesResponseDTO {

    @JsonProperty("Country")
    private String country;
    @JsonProperty("CountryCode")
    private String countryCode;
    @JsonProperty("Province")
    private String province;
    @JsonProperty("City")
    private String city;
    @JsonProperty("CityCode")
    private String cityCode;
    @JsonProperty("Lat")
    private String lat;
    @JsonProperty("Lon")
    private String lon;
    @JsonProperty("Confirmed")
    private Integer confirmed;
    @JsonProperty("Deaths")
    private Integer deaths;
    @JsonProperty("Recovered")
    private Integer recovered;
    @JsonProperty("Active")
    private Integer active;
    @JsonProperty("Date")
    private String date;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("Country")
    public String getCountry() {
        return country;
    }

    @JsonProperty("Country")
    public void setCountry(String country) {
        this.country = country;
    }

    @JsonProperty("CountryCode")
    public String getCountryCode() {
        return countryCode;
    }

    @JsonProperty("CountryCode")
    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    @JsonProperty("Province")
    public String getProvince() {
        return province;
    }

    @JsonProperty("Province")
    public void setProvince(String province) {
        this.province = province;
    }

    @JsonProperty("City")
    public String getCity() {
        return city;
    }

    @JsonProperty("City")
    public void setCity(String city) {
        this.city = city;
    }

    @JsonProperty("CityCode")
    public String getCityCode() {
        return cityCode;
    }

    @JsonProperty("CityCode")
    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    @JsonProperty("Lat")
    public String getLat() {
        return lat;
    }

    @JsonProperty("Lat")
    public void setLat(String lat) {
        this.lat = lat;
    }

    @JsonProperty("Lon")
    public String getLon() {
        return lon;
    }

    @JsonProperty("Lon")
    public void setLon(String lon) {
        this.lon = lon;
    }

    @JsonProperty("Confirmed")
    public Integer getConfirmed() {
        return confirmed;
    }

    @JsonProperty("Confirmed")
    public void setConfirmed(Integer confirmed) {
        this.confirmed = confirmed;
    }

    @JsonProperty("Deaths")
    public Integer getDeaths() {
        return deaths;
    }

    @JsonProperty("Deaths")
    public void setDeaths(Integer deaths) {
        this.deaths = deaths;
    }

    @JsonProperty("Recovered")
    public Integer getRecovered() {
        return recovered;
    }

    @JsonProperty("Recovered")
    public void setRecovered(Integer recovered) {
        this.recovered = recovered;
    }

    @JsonProperty("Active")
    public Integer getActive() {
        return active;
    }

    @JsonProperty("Active")
    public void setActive(Integer active) {
        this.active = active;
    }

    @JsonProperty("Date")
    public String getDate() {
        return date;
    }

    @JsonProperty("Date")
    public void setDate(String date) {
        this.date = date;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
