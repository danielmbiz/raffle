package com.example.raffle.model;

import com.example.raffle.dto.RaffleDTO;
import com.example.raffle.model.enums.StatusRaffle;
import com.example.raffle.model.enums.TypeRaffle;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.beans.BeanUtils;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table
public class Raffle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Descrição é obrigatório")
    @NotNull
    private String description;
    @NotNull(message = "Data do sorteio é obrigatório")
    private LocalDate dateAward;
    @NotNull(message = "Tipo é obrigatório")
    private TypeRaffle type;
    private Integer tickets;
    private Double price;
    private StatusRaffle status;
    @JsonIgnore
    @OneToMany(mappedBy = "raffle")
    private List<RaffleAward> raffleAwards;
    @JsonIgnore
    @OneToMany(mappedBy = "raffle")
    private List<RaffleItem> raffleItems;

    public Raffle() {
    }

    public Raffle(Long id, String description, LocalDate dateAward, TypeRaffle type, Integer tickets, Double price, StatusRaffle status) {
        this.id = id;
        this.description = description;
        this.dateAward = dateAward;
        this.type = type;
        this.tickets = tickets;
        this.price = price;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDateAward() {
        return dateAward;
    }

    public void setDateAward(LocalDate dateAward) {
        this.dateAward = dateAward;
    }

    public TypeRaffle getType() {
        return type;
    }

    public void setType(TypeRaffle type) {
        this.type = type;
    }

    public Integer getTickets() {
        return tickets;
    }

    public void setTickets(Integer tickets) {
        this.tickets = tickets;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public List<RaffleAward> getRaffleAwards() {
        return raffleAwards;
    }

    public void setRaffleAwards(List<RaffleAward> raffleAwards) {
        this.raffleAwards = raffleAwards;
    }

    public List<RaffleItem> getRaffleItems() {
        return raffleItems;
    }

    public void setRaffleItems(List<RaffleItem> raffleItems) {
        this.raffleItems = raffleItems;
    }

    public StatusRaffle getStatus() {
        return status;
    }

    public void setStatus(StatusRaffle status) {
        this.status = status;
    }

    public static Raffle of(RaffleDTO dto) {
        var raffle = new Raffle();
        BeanUtils.copyProperties(dto, raffle);
        return raffle;
    }
}
