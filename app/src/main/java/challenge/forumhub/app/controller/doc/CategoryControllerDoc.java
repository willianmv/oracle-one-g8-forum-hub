package challenge.forumhub.app.controller.doc;

import challenge.forumhub.app.dto.category.CategoryDetailsDTO;
import challenge.forumhub.app.dto.category.CategoryRequestDTO;
import challenge.forumhub.app.dto.category.CategorySummaryDTO;
import challenge.forumhub.app.dto.category.CategoryUpdateDTO;
import challenge.forumhub.app.dto.error.ErrorResponseDTO;
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

public interface CategoryControllerDoc {

    @Operation(summary = "Cria uma nova categoria (ADMIN)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Categoria criada com sucesso"),

            @ApiResponse(
                    responseCode = "400",
                    description = "Dados inválidos",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class))),

            @ApiResponse(
                    responseCode = "409",
                    description = "Categoria já existente",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class))),

            @ApiResponse(
                    responseCode = "500",
                    description = "Erro interno",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    ResponseEntity<CategoryDetailsDTO> create(@RequestBody CategoryRequestDTO dto);

    @Operation(summary = "Lista todas as categorias ativas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Categorias retornadas com sucesso"),

            @ApiResponse(
                    responseCode = "500",
                    description = "Erro interno",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    ResponseEntity<List<CategorySummaryDTO>> getAllCategories();

    @Operation(summary = "Busca categoria por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Categoria encontrada"),

            @ApiResponse(
                    responseCode = "404",
                    description = "Categoria não encontrada",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class))),

            @ApiResponse(
                    responseCode = "500",
                    description = "Erro interno",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    ResponseEntity<CategoryDetailsDTO> getCategoryById(
            @Parameter(description = "ID da categoria")
            @PathVariable("categoryId") long id);


    @Operation(summary = "Atualiza uma categoria existente (ADMIN)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Categoria atualizada com sucesso"),

            @ApiResponse(
                    responseCode = "400",
                    description = "Dados inválidos",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class))),

            @ApiResponse(
                    responseCode = "404",
                    description = "Categoria não encontrada",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class))),

            @ApiResponse(
                    responseCode = "409",
                    description = "Nome de categoria já existente em outra categoria",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class))),

            @ApiResponse(responseCode = "500",
                    description = "Erro interno",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    ResponseEntity<CategoryDetailsDTO> updateCategory(
            @Parameter(description = "ID da categoria a ser atualizada")
            @PathVariable("categoryId") long id,
            @RequestBody CategoryUpdateDTO dto);


    @Operation(summary = "Remove uma categoria (soft delete) (ADMIN)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Categoria desativada com sucesso"),

            @ApiResponse(
                    responseCode = "404",
                    description = "Categoria não encontrada",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class))),

            @ApiResponse(
                    responseCode = "500",
                    description = "Erro interno",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    ResponseEntity<Void> delete(
            @Parameter(description = "ID da categoria a ser removida")
            @PathVariable("categoryId") long id);
}


