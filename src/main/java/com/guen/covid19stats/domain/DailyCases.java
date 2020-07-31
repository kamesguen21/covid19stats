package com.guen.covid19stats.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.time.Instant;

import com.guen.covid19stats.domain.enumeration.Status;

/**
 * A DailyCases.
 */
@Document(collection = "daily_cases")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "dailycases")
public class DailyCases implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("country")
    private String country;

    @Field("country_code")
    private String countryCode;

    @Field("province")
    private String province;

    @Field("city")
    private String city;

    @Field("city_code")
    private String cityCode;

    @Field("lat")
    private Long lat;

    @Field("lon")
    private Long lon;

    @Field("cases")
    private Integer cases;

    @Field("status")
    private Status status;

    @Field("date")
    private Instant date;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCountry() {
        return country;
    }

    public DailyCases country(String country) {
        this.country = country;
        return this;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public DailyCases countryCode(String countryCode) {
        this.countryCode = countryCode;
        return this;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getProvince() {
        return province;
    }

    public DailyCases province(String province) {
        this.province = province;
        return this;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public DailyCases city(String city) {
        this.city = city;
        return this;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCityCode() {
        return cityCode;
    }

    public DailyCases cityCode(String cityCode) {
        this.cityCode = cityCode;
        return this;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public Long getLat() {
        return lat;
    }

    public DailyCases lat(Long lat) {
        this.lat = lat;
        return this;
    }

    public void setLat(Long lat) {
        this.lat = lat;
    }

    public Long getLon() {
        return lon;
    }

    public DailyCases lon(Long lon) {
        this.lon = lon;
        return this;
    }

    public void setLon(Long lon) {
        this.lon = lon;
    }

    public Integer getCases() {
        return cases;
    }

    public DailyCases cases(Integer cases) {
        this.cases = cases;
        return this;
    }

    public void setCases(Integer cases) {
        this.cases = cases;
    }

    public Status getStatus() {
        return status;
    }

    public DailyCases status(Status status) {
        this.status = status;
        return this;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Instant getDate() {
        return date;
    }

    public DailyCases date(Instant date) {
        this.date = date;
        return this;
    }

    public void setDate(Instant date) {
        this.date = date;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DailyCases)) {
            return false;
        }
        return id != null && id.equals(((DailyCases) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DailyCases{" +
            "id=" + getId() +
            ", country='" + getCountry() + "'" +
            ", countryCode='" + getCountryCode() + "'" +
            ", province='" + getProvince() + "'" +
            ", city='" + getCity() + "'" +
            ", cityCode='" + getCityCode() + "'" +
            ", lat=" + getLat() +
            ", lon=" + getLon() +
            ", cases=" + getCases() +
            ", status='" + getStatus() + "'" +
            ", date='" + getDate() + "'" +
            "}";
    }
}
