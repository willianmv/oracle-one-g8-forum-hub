package challenge.forumhub.app.controller.doc;

import challenge.forumhub.app.dto.error.ErrorResponseDTO;
import challenge.forumhub.app.dto.user.UserDetailsDTO;
import challenge.forumhub.app.dto.user.UserSummaryDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface UserControllerDoc {


    @Operation(
            summary = "Lista todos os usuários",
            description = "Retorna uma lista resumida com todos os usuários ativos cadastrados no sistema.")

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuários encontrados com sucesso"),

            @ApiResponse(
                    responseCode = "500",
                    description = "Erro interno do servidor",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    public ResponseEntity<List<UserSummaryDTO>> getAllUsers();


    @Operation(
            summary = "Busca usuário por ID",
            description = "Retorna os detalhes completos de um usuário identificado pelo ID fornecido.")

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário encontrado com sucesso"),

            @ApiResponse(
                    responseCode = "404",
                    description = "Usuário não encontrado",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class))),
            @ApiResponse(
                    responseCode = "500",
                    description = "Erro interno do servidor",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    public ResponseEntity<UserDetailsDTO> getUserById(
            @Parameter(description = "ID do usuário a ser buscado")
            @PathVariable("userId") long id);

}
