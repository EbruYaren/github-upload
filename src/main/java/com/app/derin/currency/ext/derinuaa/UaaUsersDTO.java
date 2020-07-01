package com.app.derin.currency.ext.derinuaa;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class UaaUsersDTO {
    private Long id;

    @NotNull
    @Size(max = 50)
    private String userName;

    @NotNull
    private String passwordHash;

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    private String email;

    private String imageUrl;

    private Boolean activated;

    private Long languageId;

    public UaaUsersDTO() {
        // Empty constructor needed for Jackson.
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Boolean isActivated() {
        return activated;
    }

    public void setActivated(Boolean activated) {
        this.activated = activated;
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

        UaaUsersDTO uaaUsersDTO = (UaaUsersDTO) o;
        if (uaaUsersDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), uaaUsersDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "UaaUsersDTO{" +
            "id=" + getId() +
            ", userName='" + getUserName() + "'" +
            ", passwordHash='" + getPasswordHash() + "'" +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", email='" + getEmail() + "'" +
            ", imageUrl='" + getImageUrl() + "'" +
            ", activated='" + isActivated() + "'" +
            ", languageId=" + getLanguageId() +
            "}";
    }
}
