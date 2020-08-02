package com.guen.covid19stats.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.DBRef;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.time.Instant;

/**
 * A DailyCases.
 */
@Document(collection = "daily_cases")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "dailycases")
public class DailyCases implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("lat")
    private Double lat;

    @Field("lon")
    private Double lon;

    @Field("confirmed")
    private Integer confirmed;

    @Field("active")
    private Integer active;

    @Field("deaths")
    private Integer deaths;

    @Field("recovered")
    private Integer recovered;

    @Field("date")
    private Instant date;

    @DBRef
    @Field("country")
    @JsonIgnoreProperties(value = "dailyCases", allowSetters = true)
    private Country country;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Double getLat() {
        return lat;
    }

    public DailyCases lat(Double lat) {
        this.lat = lat;
        return this;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLon() {
        return lon;
    }

    public DailyCases lon(Double lon) {
        this.lon = lon;
        return this;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }

    public Integer getConfirmed() {
        return confirmed;
    }

    public DailyCases confirmed(Integer confirmed) {
        this.confirmed = confirmed;
        return this;
    }

    public void setConfirmed(Integer confirmed) {
        this.confirmed = confirmed;
    }

    public Integer getActive() {
        return active;
    }

    public DailyCases active(Integer active) {
        this.active = active;
        return this;
    }

    public void setActive(Integer active) {
        this.active = active;
    }

    public Integer getDeaths() {
        return deaths;
    }

    public DailyCases deaths(Integer deaths) {
        this.deaths = deaths;
        return this;
    }

    public void setDeaths(Integer deaths) {
        this.deaths = deaths;
    }

    public Integer getRecovered() {
        return recovered;
    }

    public DailyCases recovered(Integer recovered) {
        this.recovered = recovered;
        return this;
    }

    public void setRecovered(Integer recovered) {
        this.recovered = recovered;
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

    public Country getCountry() {
        return country;
    }

    public DailyCases country(Country country) {
        this.country = country;
        return this;
    }

    public void setCountry(Country country) {
        this.country = country;
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
            ", lat=" + getLat() +
            ", lon=" + getLon() +
            ", confirmed=" + getConfirmed() +
            ", active=" + getActive() +
            ", deaths=" + getDeaths() +
            ", recovered=" + getRecovered() +
            ", date='" + getDate() + "'" +
            "}";
    }
}
