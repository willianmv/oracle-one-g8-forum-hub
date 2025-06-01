package challenge.forumhub.app.controller.doc;

import challenge.forumhub.app.dto.auth.LoginRequestDTO;
import challenge.forumhub.app.dto.auth.RegisterRequestDTO;
import challenge.forumhub.app.dto.auth.TokenResponseDTO;
import challenge.forumhub.app.dto.error.ErrorResponseDTO;
import challenge.forumhub.app.dto.user.UserDetailsDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

public interface AuthControllerDoc {

    @Operation(
            summary = "Registra um novo usuário",
            description = "Cria um novo usuário com perfil padrão (MEMBER). Retorna os dados do usuário criado."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Usuário registrado com sucesso"),

            @ApiResponse(responseCode = "400",
                    description = "E-mail já cadastrado ou erro de validação nos campos",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class))),

            @ApiResponse(
                    responseCode = "404",
                    description = "Profile padrão não encontrado",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class))),

            @ApiResponse(
                    responseCode = "500",
                    description = "Erro interno do servidor",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    ResponseEntity<UserDetailsDTO> register(@RequestBody RegisterRequestDTO registerRequestDTO);


    @Operation(
            summary = "Autentica um usuário",
            description = "Recebe e-mail e senha, e retorna um token JWT válido para autenticação dos endpoints protegidos."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário autenticado com sucesso"),

            @ApiResponse(
                    responseCode = "400",
                    description = "E-mail ou senha inválidos",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class))),

            @ApiResponse(
                    responseCode = "500",
                    description = "Erro interno do servidor",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    ResponseEntity<TokenResponseDTO> login(@RequestBody LoginRequestDTO loginRequestDTO);

}
