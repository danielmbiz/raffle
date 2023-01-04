package com.example.raffle.dto;

import com.example.raffle.model.Client;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ClientDTO {
    private Long id;
    @NotBlank(message = "Nome é obrigatório")
    private String name;
    @NotBlank(message = "CPF é obrigatório")
    private String cpf;
    @NotBlank(message = "E-mail é obrigatório")
    @Email(message = "E-mail inválido")
    private String email;
    @NotBlank(message = "Celular é obrigatório")
    private String cel;
    @NotBlank(message = "CEP é obrigatório")
    private String postCode;
    private String city;
    private String ibgeCity;
    private String state;

    public static ClientDTO of(Client client) {
        return ClientDTO.builder()
                .id(client.getId())
                .name(client.getName())
                .cpf(client.getCpf())
                .email(client.getEmail())
                .cel(client.getCel())
                .postCode(client.getPostCode())
                .city(client.getCity())
                .ibgeCity(client.getIbgeCity())
                .state(client.getState())
                .build();
    }
}
