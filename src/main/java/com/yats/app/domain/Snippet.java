package com.yats.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A Snippet.
 */
@Entity
@Table(name = "snippet")
public class Snippet implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "code", nullable = false)
    private String code;

    @NotNull
    @Column(name = "libelle", nullable = false)
    private String libelle;

    @NotNull
    @Column(name = "description", nullable = false)
    private String description;

    @NotNull
    @Column(name = "text_code", nullable = false)
    private String textCode;

    @NotNull
    @Column(name = "creation_date", nullable = false)
    private Instant creationDate;

    @Column(name = "last_modified_date")
    private Instant lastModifiedDate;

    @Column(name = "actif")
    private Boolean actif;

    @Column(name = "gitlab_external_code")
    private Long gitlabExternalCode;

    @ManyToOne
    @JsonIgnoreProperties(value = "snippets", allowSetters = true)
    private Category categoryCd;

    @ManyToOne
    @JsonIgnoreProperties(value = "snippets", allowSetters = true)
    private Tag tagCd;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public Snippet code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLibelle() {
        return libelle;
    }

    public Snippet libelle(String libelle) {
        this.libelle = libelle;
        return this;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String getDescription() {
        return description;
    }

    public Snippet description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTextCode() {
        return textCode;
    }

    public Snippet textCode(String textCode) {
        this.textCode = textCode;
        return this;
    }

    public void setTextCode(String textCode) {
        this.textCode = textCode;
    }

    public Instant getCreationDate() {
        return creationDate;
    }

    public Snippet creationDate(Instant creationDate) {
        this.creationDate = creationDate;
        return this;
    }

    public void setCreationDate(Instant creationDate) {
        this.creationDate = creationDate;
    }

    public Instant getLastModifiedDate() {
        return lastModifiedDate;
    }

    public Snippet lastModifiedDate(Instant lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
        return this;
    }

    public void setLastModifiedDate(Instant lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public Boolean isActif() {
        return actif;
    }

    public Snippet actif(Boolean actif) {
        this.actif = actif;
        return this;
    }

    public void setActif(Boolean actif) {
        this.actif = actif;
    }

    public Long getGitlabExternalCode() {
        return gitlabExternalCode;
    }

    public Snippet gitlabExternalCode(Long gitlabExternalCode) {
        this.gitlabExternalCode = gitlabExternalCode;
        return this;
    }

    public void setGitlabExternalCode(Long gitlabExternalCode) {
        this.gitlabExternalCode = gitlabExternalCode;
    }

    public Category getCategoryCd() {
        return categoryCd;
    }

    public Snippet categoryCd(Category category) {
        this.categoryCd = category;
        return this;
    }

    public void setCategoryCd(Category category) {
        this.categoryCd = category;
    }

    public Tag getTagCd() {
        return tagCd;
    }

    public Snippet tagCd(Tag tag) {
        this.tagCd = tag;
        return this;
    }

    public void setTagCd(Tag tag) {
        this.tagCd = tag;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Snippet)) {
            return false;
        }
        return id != null && id.equals(((Snippet) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Snippet{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", libelle='" + getLibelle() + "'" +
            ", description='" + getDescription() + "'" +
            ", textCode='" + getTextCode() + "'" +
            ", creationDate='" + getCreationDate() + "'" +
            ", lastModifiedDate='" + getLastModifiedDate() + "'" +
            ", actif='" + isActif() + "'" +
            ", gitlabExternalCode=" + getGitlabExternalCode() +
            "}";
    }
}
