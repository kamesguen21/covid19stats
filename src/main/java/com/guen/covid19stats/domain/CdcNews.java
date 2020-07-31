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
 * A CdcNews.
 */
@Document(collection = "cdc_news")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "cdcnews")
public class CdcNews implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("title")
    private String title;

    @Field("description")
    private String description;

    @Field("pub_date")
    private String pubDate;

    @Field("last_build_date")
    private String lastBuildDate;

    @Field("link")
    private String link;

    @DBRef
    @Field("items")
    private Set<Item> items = new HashSet<>();

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

    public CdcNews title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public CdcNews description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPubDate() {
        return pubDate;
    }

    public CdcNews pubDate(String pubDate) {
        this.pubDate = pubDate;
        return this;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public String getLastBuildDate() {
        return lastBuildDate;
    }

    public CdcNews lastBuildDate(String lastBuildDate) {
        this.lastBuildDate = lastBuildDate;
        return this;
    }

    public void setLastBuildDate(String lastBuildDate) {
        this.lastBuildDate = lastBuildDate;
    }

    public String getLink() {
        return link;
    }

    public CdcNews link(String link) {
        this.link = link;
        return this;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Set<Item> getItems() {
        return items;
    }

    public CdcNews items(Set<Item> items) {
        this.items = items;
        return this;
    }

    public CdcNews addItems(Item item) {
        this.items.add(item);
        item.setCdcNews(this);
        return this;
    }

    public CdcNews removeItems(Item item) {
        this.items.remove(item);
        item.setCdcNews(null);
        return this;
    }

    public void setItems(Set<Item> items) {
        this.items = items;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CdcNews)) {
            return false;
        }
        return id != null && id.equals(((CdcNews) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CdcNews{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", description='" + getDescription() + "'" +
            ", pubDate='" + getPubDate() + "'" +
            ", lastBuildDate='" + getLastBuildDate() + "'" +
            ", link='" + getLink() + "'" +
            "}";
    }
}
