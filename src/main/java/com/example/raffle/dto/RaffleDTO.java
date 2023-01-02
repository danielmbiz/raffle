package com.example.raffle.dto;

import com.example.raffle.model.Raffle;
import com.example.raffle.model.enums.StatusRaffle;
import com.example.raffle.model.enums.TypeRaffle;
import org.springframework.beans.BeanUtils;

import java.time.LocalDate;

public class RaffleDTO {

    private Long id;
    private String description;
    private LocalDate dateAward;
    private TypeRaffle type;
    private Integer tickets;
    private Double price;
    private StatusRaffle status;

    public RaffleDTO() {
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

    public StatusRaffle getStatus() {
        return status;
    }

    public void setStatus(StatusRaffle status) {
        this.status = status;
    }

    public static RaffleDTO of(Raffle raffle) {
        var dto = new RaffleDTO();
        BeanUtils.copyProperties(raffle, dto);
        return dto;
    }
}
