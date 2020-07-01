package com.app.derin.currency.ext.derinuaa;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

public class UaaDictionaryDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 800)
    private String code;

    private String description;

    @NotNull
    @Size(max = 800)
    private String translation;


    private Long dictionaryGroupId;

    private Long languageId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTranslation() {
        return translation;
    }

    public void setTranslation(String translation) {
        this.translation = translation;
    }

    public Long getDictionaryGroupId() {
        return dictionaryGroupId;
    }

    public void setDictionaryGroupId(Long uaaDictionaryGroupsId) {
        this.dictionaryGroupId = uaaDictionaryGroupsId;
    }

    public Long getLanguageId() {
        return languageId;
    }

    public void setLanguageId(Long uaaLanguagesId) {
        this.languageId = uaaLanguagesId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        UaaDictionaryDTO uaaDictionaryDTO = (UaaDictionaryDTO) o;
        if (uaaDictionaryDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), uaaDictionaryDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "UaaDictionaryDTO{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", description='" + getDescription() + "'" +
            ", translation='" + getTranslation() + "'" +
            ", dictionaryGroupId=" + getDictionaryGroupId() +
            ", languageId=" + getLanguageId() +
            "}";
    }
}
