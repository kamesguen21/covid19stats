package com.guen.covid19stats.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.DBRef;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Item.
 */
@Document(collection = "item")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "item")
public class Item implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("title")
    private String title;

    @Field("description")
    private String description;

    @Field("link")
    private String link;

    @Field("pub_date")
    private String pubDate;

    @Field("media")
    private String media;

    @DBRef
    @Field("categories")
    private Set<Category> categories = new HashSet<>();

    @DBRef
    @Field("cdcNews")
    @JsonIgnoreProperties(value = "items", allowSetters = true)
    private CdcNews cdcNews;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public Item title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public Item description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLink() {
        return link;
    }

    public Item link(String link) {
        this.link = link;
        return this;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getPubDate() {
        return pubDate;
    }

    public Item pubDate(String pubDate) {
        this.pubDate = pubDate;
        return this;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public String getMedia() {
        return media;
    }

    public Item media(String media) {
        this.media = media;
        return this;
    }

    public void setMedia(String media) {
        this.media = media;
    }

    public Set<Category> getCategories() {
        return categories;
    }

    public Item categories(Set<Category> categories) {
        this.categories = categories;
        return this;
    }

    public Item addCategories(Category category) {
        this.categories.add(category);
        category.setItem(this);
        return this;
    }

    public Item removeCategories(Category category) {
        this.categories.remove(category);
        category.setItem(null);
        return this;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }

    public CdcNews getCdcNews() {
        return cdcNews;
    }

    public Item cdcNews(CdcNews cdcNews) {
        this.cdcNews = cdcNews;
        return this;
    }

    public void setCdcNews(CdcNews cdcNews) {
        this.cdcNews = cdcNews;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Item)) {
            return false;
        }
        return id != null && id.equals(((Item) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Item{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", description='" + getDescription() + "'" +
            ", link='" + getLink() + "'" +
            ", pubDate='" + getPubDate() + "'" +
            ", media='" + getMedia() + "'" +
            "}";
    }
}
