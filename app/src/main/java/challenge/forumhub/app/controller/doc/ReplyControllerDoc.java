package challenge.forumhub.app.controller.doc;

import challenge.forumhub.app.dto.error.ErrorResponseDTO;
import challenge.forumhub.app.dto.reply.ReplyDetailsDTO;
import challenge.forumhub.app.dto.reply.ReplyRequestDTO;
import challenge.forumhub.app.dto.reply.ReplySummaryDTO;
import challenge.forumhub.app.dto.reply.ReplyUpdateDTO;
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

public interface ReplyControllerDoc {

    @Operation(summary = "Cria uma nova resposta para um tópico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Resposta criada com sucesso"),

            @ApiResponse(
                    responseCode = "400",
                    description = "Erro de validação ou dados incorretos",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class))),

            @ApiResponse(
                    responseCode = "404",
                    description = "Tópico não encontrado",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class))),

            @ApiResponse(
                    responseCode = "409",
                    description = "Resposta já existente com mesmo título e conteúdo",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    ResponseEntity<ReplyDetailsDTO> create(@RequestBody ReplyRequestDTO dto);


    @Operation(summary = "Retorna todas as respostas ativas")
    @ApiResponse(responseCode = "200", description = "Respostas encontradas com sucesso")
    ResponseEntity<List<ReplySummaryDTO>> getAllReplies();

    @Operation(summary = "Busca uma resposta específica pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Resposta encontrada com sucesso"),

            @ApiResponse(
                    responseCode = "404",
                    description = "Resposta não encontrada",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    ResponseEntity<ReplyDetailsDTO> getReplyById(
            @Parameter(description = "ID da resposta")
            @PathVariable("replyId") long id);


    @Operation(summary = "Atualiza uma resposta existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Resposta atualizada com sucesso"),

            @ApiResponse(responseCode = "400",
                    description = "Erro de validação",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class))),

            @ApiResponse(
                    responseCode = "403",
                    description = "Acesso negado (usuário não é dono, moderador ou admin)",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class))),

            @ApiResponse(
                    responseCode = "404",
                    description = "Resposta ou tópico não encontrado",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class))),

            @ApiResponse(
                    responseCode = "409",
                    description = "Já existe uma resposta com mesmo título e solução",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    ResponseEntity<ReplyDetailsDTO> updateReply(
            @Parameter(description = "ID da resposta")
            @PathVariable("replyId") long id,
            @RequestBody ReplyUpdateDTO dto);


    @Operation(summary = "Remove (desativa) uma resposta")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Resposta removida com sucesso"),

            @ApiResponse(
                    responseCode = "403",
                    description = "Acesso negado (usuário não é dono, moderador ou admin)",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class))),

            @ApiResponse(
                    responseCode = "404",
                    description = "Resposta não encontrada",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    ResponseEntity<Void> deleteReply(
            @Parameter(description = "ID da resposta")
            @PathVariable("replyId") long id);

}
