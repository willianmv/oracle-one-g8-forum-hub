package challenge.forumhub.app.controller.doc;

import challenge.forumhub.app.dto.error.ErrorResponseDTO;
import challenge.forumhub.app.dto.topic.TopicDetailsDTO;
import challenge.forumhub.app.dto.topic.TopicRequestDTO;
import challenge.forumhub.app.dto.topic.TopicSummaryDTO;
import challenge.forumhub.app.dto.topic.TopicUpdateDTO;
import challenge.forumhub.app.entity.TopicStatus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface TopicControllerDoc {

    @Operation(summary = "Cria um novo tópico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Tópico criado com sucesso"),

            @ApiResponse(
                    responseCode = "400",
                    description = "Erro de validação",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class))),

            @ApiResponse(
                    responseCode = "404",
                    description = "Curso não encontrado",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class))),

            @ApiResponse(
                    responseCode = "409",
                    description = "Tópico já existe",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class))),

            @ApiResponse(
                    responseCode = "500",
                    description = "Erro interno",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    ResponseEntity<TopicDetailsDTO> create(@RequestBody TopicRequestDTO dto);

    @Operation(summary = "Retorna todos os tópicos com ou sem filtros")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tópicos encontrados"),

            @ApiResponse(
                    responseCode = "500",
                    description = "Erro interno",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    ResponseEntity<List<TopicSummaryDTO>> getAllTopics(
            @Parameter(description = "ID do autor") Long authorId,
            @Parameter(description = "ID do curso") Long courseId,
            @Parameter(description = "ID da categoria") Long categoryId,
            @Parameter(description = "Status do tópico") TopicStatus status);


    @Operation(summary = "Retorna um tópico por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tópico encontrado"),

            @ApiResponse(
                    responseCode = "404",
                    description = "Tópico não encontrado",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    ResponseEntity<TopicDetailsDTO> getTopicById(
            @Parameter(description = "ID do tópico")
            @PathVariable("topicId") long id);


    @Operation(summary = "Atualiza um tópico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tópico atualizado com sucesso"),

            @ApiResponse(
                    responseCode = "400",
                    description = "Erro de validação",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class))),

            @ApiResponse(
                    responseCode = "403",
                    description = "Acesso negado (usuário não é dono ou moderador)",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class))),

            @ApiResponse(
                    responseCode = "404",
                    description = "Tópico ou curso não encontrado",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class))),

            @ApiResponse(
                    responseCode = "409",
                    description = "Tópico já existe com mesmo título e mensagem",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    ResponseEntity<TopicDetailsDTO> updateTopic(
            @Parameter(description = "ID do tópico")
            @PathVariable("topicId") long id,
            @RequestBody TopicUpdateDTO dto);


    @Operation(summary = "Remove (desativa) um tópico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Tópico removido com sucesso"),

            @ApiResponse(
                    responseCode = "403",
                    description = "Acesso negado (usuário não é dono ou moderador)",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class))),

            @ApiResponse(
                    responseCode = "404",
                    description = "Tópico não encontrado",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    ResponseEntity<Void> deleteTopic(
            @Parameter(description = "ID do tópico")
            @PathVariable("topicId") long id);
}
