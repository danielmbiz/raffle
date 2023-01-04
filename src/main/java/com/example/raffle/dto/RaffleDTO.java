package com.example.raffle.dto;

import com.example.raffle.model.Raffle;
import com.example.raffle.model.enums.StatusRaffle;
import com.example.raffle.model.enums.TypeRaffle;
import lombok.*;
import org.springframework.beans.BeanUtils;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class RaffleDTO {

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

    public static RaffleDTO of(Raffle raffle) {
        var dto = new RaffleDTO();
        BeanUtils.copyProperties(raffle, dto);
        return dto;
    }
}
