package com.yats.app.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A Tag.
 */
@Entity
@Table(name = "tag")
public class Tag implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "tag_cd", nullable = false)
    private String tagCd;

    @NotNull
    @Column(name = "tag_desc", nullable = false)
    private String tagDesc;

    @NotNull
    @Column(name = "creation_date", nullable = false)
    private Instant creationDate;

    @Column(name = "last_modified_date")
    private Instant lastModifiedDate;

    @Column(name = "actif")
    private Boolean actif;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTagCd() {
        return tagCd;
    }

    public Tag tagCd(String tagCd) {
        this.tagCd = tagCd;
        return this;
    }

    public void setTagCd(String tagCd) {
        this.tagCd = tagCd;
    }

    public String getTagDesc() {
        return tagDesc;
    }

    public Tag tagDesc(String tagDesc) {
        this.tagDesc = tagDesc;
        return this;
    }

    public void setTagDesc(String tagDesc) {
        this.tagDesc = tagDesc;
    }

    public Instant getCreationDate() {
        return creationDate;
    }

    public Tag creationDate(Instant creationDate) {
        this.creationDate = creationDate;
        return this;
    }

    public void setCreationDate(Instant creationDate) {
        this.creationDate = creationDate;
    }

    public Instant getLastModifiedDate() {
        return lastModifiedDate;
    }

    public Tag lastModifiedDate(Instant lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
        return this;
    }

    public void setLastModifiedDate(Instant lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public Boolean isActif() {
        return actif;
    }

    public Tag actif(Boolean actif) {
        this.actif = actif;
        return this;
    }

    public void setActif(Boolean actif) {
        this.actif = actif;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Tag)) {
            return false;
        }
        return id != null && id.equals(((Tag) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Tag{" +
            "id=" + getId() +
            ", tagCd='" + getTagCd() + "'" +
            ", tagDesc='" + getTagDesc() + "'" +
            ", creationDate='" + getCreationDate() + "'" +
            ", lastModifiedDate='" + getLastModifiedDate() + "'" +
            ", actif='" + isActif() + "'" +
            "}";
    }
}
