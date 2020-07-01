package com.app.derin.currency.ext.derinfw.dto;

import java.io.Serializable;
import java.util.Objects;


public class McfClientsDTO implements Serializable {

    private Long id;

    private String clientCode;

    private String clientName;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getClientCode() {
        return clientCode;
    }

    public void setClientCode(String clientCode) {
        this.clientCode = clientCode;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        McfClientsDTO mcfClientsDTO = (McfClientsDTO) o;
        if (mcfClientsDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), mcfClientsDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "McfClientsDTO{" +
            "id=" + getId() +
            ", clientCode='" + getClientCode() + "'" +
            ", clientName='" + getClientName() + "'" +
            "}";
    }
}
