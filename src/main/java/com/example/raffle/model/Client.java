package com.example.raffle.model;

import com.example.raffle.dto.ClientDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.beans.BeanUtils;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Nome é obrigatório")
    private String name;
    @Column(unique = true)
    @NotBlank(message = "CPF é obrigatório")
    private String cpf;
    @Column(unique = true)
    @NotBlank(message = "E-mail é obrigatório")
    @Email(message = "E-mail inválido")
    private String email;
    @Column(unique = true)
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
    
    public static Client of(ClientDTO dto) {
        var client = new Client();
        BeanUtils.copyProperties(dto, client);
        return client;
    }
}
