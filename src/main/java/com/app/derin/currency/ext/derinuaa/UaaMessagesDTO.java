package com.app.derin.currency.ext.derinuaa;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Objects;

public class UaaMessagesDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 800)
    private String code;

    @NotNull
    @Size(max = 800)
    private String translation;


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

    public String getTranslation() {
        return translation;
    }

    public void setTranslation(String translation) {
        this.translation = translation;
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

        UaaMessagesDTO uaaMessagesDTO = (UaaMessagesDTO) o;
        if (uaaMessagesDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), uaaMessagesDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "UaaMessagesDTO{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", translation='" + getTranslation() + "'" +
            ", languageId=" + getLanguageId() +
            "}";
    }
}
