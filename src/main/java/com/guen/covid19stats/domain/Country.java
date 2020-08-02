package com.guen.covid19stats.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.DBRef;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Country.
 */
@Document(collection = "country")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "country")
public class Country implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("country")
    private String country;

    @Field("slug")
    private String slug;

    @Field("i_so_2")
    private String iSO2;

    @DBRef
    @Field("dailyCases")
    private Set<DailyCases> dailyCases = new HashSet<>();

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

    public Country country(String country) {
        this.country = country;
        return this;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getSlug() {
        return slug;
    }

    public Country slug(String slug) {
        this.slug = slug;
        return this;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getiSO2() {
        return iSO2;
    }

    public Country iSO2(String iSO2) {
        this.iSO2 = iSO2;
        return this;
    }

    public void setiSO2(String iSO2) {
        this.iSO2 = iSO2;
    }

    public Set<DailyCases> getDailyCases() {
        return dailyCases;
    }

    public Country dailyCases(Set<DailyCases> dailyCases) {
        this.dailyCases = dailyCases;
        return this;
    }

    public Country addDailyCases(DailyCases dailyCases) {
        this.dailyCases.add(dailyCases);
        dailyCases.setCountry(this);
        return this;
    }

    public Country removeDailyCases(DailyCases dailyCases) {
        this.dailyCases.remove(dailyCases);
        dailyCases.setCountry(null);
        return this;
    }

    public void setDailyCases(Set<DailyCases> dailyCases) {
        this.dailyCases = dailyCases;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Country)) {
            return false;
        }
        return id != null && id.equals(((Country) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Country{" +
            "id=" + getId() +
            ", country='" + getCountry() + "'" +
            ", slug='" + getSlug() + "'" +
            ", iSO2='" + getiSO2() + "'" +
            "}";
    }
}
