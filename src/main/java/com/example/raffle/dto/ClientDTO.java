package com.example.raffle.dto;

import com.example.raffle.model.Client;
import org.springframework.beans.BeanUtils;

public class ClientDTO {

    private Long id;
    private String name;
    private String cpf;
    private String email;
    private String cel;
    private String postCode;
    private String city;
    private String ibgeCity;
    private String state;

    public ClientDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCel() {
        return cel;
    }

    public void setCel(String cel) {
        this.cel = cel;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getIbgeCity() {
        return ibgeCity;
    }

    public void setIbgeCity(String ibgeCity) {
        this.ibgeCity = ibgeCity;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public static ClientDTO of(Client client) {
        var dto = new ClientDTO();
        BeanUtils.copyProperties(client, dto);
        return dto;
    }
}
