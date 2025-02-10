package com.kacielriff.authentication_project.docs;

import com.kacielriff.authentication_project.dto.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

public interface AuthControllerDocs {

    @Operation(summary = "Realiza o login", description = "Autenticar um usuário existente no sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login realizado com sucesso.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = LoginResponseUserDTO.class),
                            examples = @ExampleObject(value = """
                        {
                            "id": "1",
                            "email": "email@email.com",
                            "token": "Bearer eyJhbGciOc6IkCJ9.eyJzdWIiOM0tZSI6.SflKxwRJSMeKQT4fw"
                        }
                        """))),
            @ApiResponse(responseCode = "400", description = "Usuário ou senha inválido(s).",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = """
                        {
                            "timestamp": "2024-09-17T14:29:37.582+00:00",
                            "status": 400,
                            "errors": []
                        }
                        """))),
            @ApiResponse(responseCode = "403", description = "Usuário inativo.",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = """
                        {
                            "timestamp": "2024-09-17T14:29:37.582+00:00",
                            "status": 403,
                            "errors": []
                        }
                        """))),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor.",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = """
                        {
                            "timestamp": "2024-09-17T14:29:37.582+00:00",
                            "error": "Internal Server Error",
                            "message": "Erro interno do servidor"
                        }
                        """)))
    })
    ResponseEntity<LoginResponseUserDTO> login(@RequestBody @Valid LoginUserDTO loginUserDTO) throws Exception;

    @Operation(summary = "Realiza o cadastro", description = "Realiza o cadastro de um usuário inexistente no sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Conta criada com sucesso.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = LoginResponseUserDTO.class),
                            examples = @ExampleObject(value = """
                        {
                            "message": "Um email para confirmação de conta foi encaminhado com sucesso."
                        }
                        """))),
            @ApiResponse(responseCode = "400", description = "Usuário ou senha inválido(s).",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = """
                        {
                            "timestamp": "2024-09-17T14:29:37.582+00:00",
                            "status": 400,
                            "errors": []
                        }
                        """))),
            @ApiResponse(responseCode = "409", description = "Email já cadastrado.",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = """
                        {
                            "timestamp": "2024-09-17T14:29:37.582+00:00",
                            "status": 409,
                            "errors": []
                        }
                        """))),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor.",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = """
                        {
                            "timestamp": "2024-09-17T14:29:37.582+00:00",
                            "error": "Internal Server Error",
                            "message": "Erro interno do servidor"
                        }
                        """)))
    })
    ResponseEntity<MessageDTO> create(@RequestBody @Valid CreateUserDTO createUserDTO) throws Exception;

    @Operation(summary = "Esqueceu sua senha", description = "Iniciar processo de recuperação de senha para o candidato.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Solicitação de recuperação de senha realizada com sucesso.",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = """
                        {
                            "message": "Email para recuperação de senha encaminhado com sucesso."
                        }
                        """))),
            @ApiResponse(responseCode = "400", description = "Email inválido.",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = """
                        {
                            "timestamp": "2024-09-17T14:29:37.582+00:00",
                            "status": 400,
                            "errors": []
                        }
                        """))),
            @ApiResponse(responseCode = "403", description = "Usuário inativo.",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = """
                        {
                            "timestamp": "2024-09-17T14:29:37.582+00:00",
                            "status": 403,
                            "errors": []
                        }
                        """))),
            @ApiResponse(responseCode = "404", description = "Usuário ou email não encontrado.",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = """
                        {
                            "timestamp": "2024-09-17T14:29:37.582+00:00",
                            "error": "Not Found",
                            "message": "Usuário não encontrado"
                        }
                        """))),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor.",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = """
                        {
                            "timestamp": "2024-09-17T14:29:37.582+00:00",
                            "error": "Internal Server Error",
                            "message": "Erro interno do servidor"
                        }
                        """)))
    })
    ResponseEntity<MessageDTO> forgotPassword(@RequestBody @Valid ForgotPasswordDTO forgotPasswordDTO) throws Exception;

    @Operation(summary = "Recuperar senha", description = "Finaliza o processo de recuperação de senha para o usuário.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Recuperação de senha realizada com sucesso."),
            @ApiResponse(responseCode = "400", description = "Email inválido.",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = """
                        {
                            "timestamp": "2024-09-17T14:29:37.582+00:00",
                            "status": 400,
                            "errors": []
                        }
                        """))),
            @ApiResponse(responseCode = "404", description = "Usuário ou email não encontrado.",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = """
                        {
                            "timestamp": "2024-09-17T14:29:37.582+00:00",
                            "error": "Not Found",
                            "message": "Usuário não encontrado"
                        }
                        """))),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor.",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = """
                        {
                            "timestamp": "2024-09-17T14:29:37.582+00:00",
                            "error": "Internal Server Error",
                            "message": "Erro interno do servidor"
                        }
                        """)))
    })
    ResponseEntity<MessageDTO> recoverPassword(@RequestBody @Valid RecoverPasswordDTO recoverPasswordDTO) throws Exception;

    @Operation(summary = "Alterar senha", description = "Altera a senha do usuário.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Senha alterada com sucesso.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = LoginResponseUserDTO.class),
                            examples = @ExampleObject(value = """
                        {
                            "id": "1",
                            "email": "email@email.com",
                            "token": "Bearer eyJhbGciOc6IkCJ9.eyJzdWIiOM0tZSI6.SflKxwRJSMeKQT4fw"
                        }
                        """))),
            @ApiResponse(responseCode = "400", description = "Senha inválida.",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = """
                        {
                            "timestamp": "2024-09-17T14:29:37.582+00:00",
                            "status": 400,
                            "errors": []
                        }
                        """))),
            @ApiResponse(responseCode = "404", description = "Usuário ou email não encontrado.",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = """
                        {
                            "timestamp": "2024-09-17T14:29:37.582+00:00",
                            "error": "Not Found",
                            "message": "Usuário não encontrado"
                        }
                        """))),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor.",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = """
                        {
                            "timestamp": "2024-09-17T14:29:37.582+00:00",
                            "error": "Internal Server Error",
                            "message": "Erro interno do servidor"
                        }
                        """)))
    })
    ResponseEntity<LoginResponseUserDTO> changePassword(@RequestBody @Valid ChangePasswordDTO changePasswordDTO) throws Exception;

    @Operation(summary = "Confirmar conta", description = "Confirma a criação de conta do usuário.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Conta confirmada com sucesso.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = LoginResponseUserDTO.class),
                            examples = @ExampleObject(value = """
                        {
                            "message": "Conta confirmada com sucesso."
                        }
                        """))),
            @ApiResponse(responseCode = "401", description = "Não autorizado.",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = """
                        {
                            "timestamp": "2024-09-17T14:29:37.582+00:00",
                            "status": 401,
                            "errors": []
                        }
                        """))),
            @ApiResponse(responseCode = "404", description = "Usuário ou email não encontrado.",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = """
                        {
                            "timestamp": "2024-09-17T14:29:37.582+00:00",
                            "error": "Not Found",
                            "message": "Usuário não encontrado"
                        }
                        """))),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor.",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = """
                        {
                            "timestamp": "2024-09-17T14:29:37.582+00:00",
                            "error": "Internal Server Error",
                            "message": "Erro interno do servidor"
                        }
                        """)))
    })
    ResponseEntity<MessageDTO> confirmAccount(@RequestBody @Valid AccountCreationValidationDTO accountCreationValidationDTO) throws Exception;

    @Operation(summary = "Recupera o usuário logado", description = "Retorna as informações do usuário logado.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna as informações do usuário logado."),
            @ApiResponse(responseCode = "404", description = "Usuário ou email não encontrado.",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = """
                        {
                            "timestamp": "2024-09-17T14:29:37.582+00:00",
                            "error": "Not Found",
                            "message": "Usuário não encontrado"
                        }
                        """))),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor.",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = """
                        {
                            "timestamp": "2024-09-17T14:29:37.582+00:00",
                            "error": "Internal Server Error",
                            "message": "Erro interno do servidor"
                        }
                        """)))
    })
    ResponseEntity<LoggedUserDTO> getLoggedUser() throws Exception;
}