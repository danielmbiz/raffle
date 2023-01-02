package com.example.raffle.model;

import com.example.raffle.dto.ClientDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.beans.BeanUtils;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Entity
@Table
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Nome é obrigatório")
    private String name;
    @NotBlank(message = "CPF é obrigatório")
    private String cpf;
    @Column(unique = true)
    @NotBlank(message = "Email é obrigatório")
    private String email;
    @NotBlank(message = "Celular é obrigatório")
    private String cel;
    @NotBlank(message = "CEP é obrigatório")
    private String postCode;
    private String city;
    private String ibgeCity;
    private String state;
    @JsonIgnore
    @OneToMany(mappedBy = "client")
    private List<RaffleItem> raffleItems;

    public Client() {
    }

    public Client(Long id, String name, String cpf, String email, String cel, String postCode) {
        this.id = id;
        this.name = name;
        this.cpf = cpf;
        this.email = email;
        this.cel = cel;
        this.postCode = postCode;
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

    public List<RaffleItem> getRaffleItems() {
        return raffleItems;
    }

    public void setRaffleItems(List<RaffleItem> raffleItems) {
        this.raffleItems = raffleItems;
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

    public static Client of(ClientDTO dto) {
        var entity = new Client();
        BeanUtils.copyProperties(dto, entity);
        return entity;
    }
}
