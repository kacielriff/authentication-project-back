package com.kacielriff.authentication_project.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoggedUserDTO {
    @Schema(description = "ID do usuário logado", example = "1")
    private Long id;

    @Schema(description = "Nome do usuário logado", example = "João da Silva")
    private String name;

    @Schema(description = "Email do usuário logado", example = "email@email.com")
    private String email;
}