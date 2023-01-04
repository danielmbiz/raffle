package com.example.raffle.model;

import com.example.raffle.dto.RaffleDTO;
import com.example.raffle.model.enums.StatusRaffle;
import com.example.raffle.model.enums.TypeRaffle;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.beans.BeanUtils;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
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
    @Min(value = 10, message = "É preciso ter no mínimo 10 números")
    private Integer tickets;
    private Double price;
    @NotNull(message = "Status é obrigatório")
    private StatusRaffle status;
    @JsonIgnore
    @OneToMany(mappedBy = "raffle")
    private List<RaffleAward> raffleAwards;
    @JsonIgnore
    @OneToMany(mappedBy = "raffle")
    private List<RaffleItem> raffleItems;

    public static Raffle of(RaffleDTO dto) {
        var raffle = new Raffle();
        BeanUtils.copyProperties(dto, raffle);
        return raffle;
    }
}
