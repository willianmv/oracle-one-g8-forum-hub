package challenge.forumhub.app.controller.doc;

import challenge.forumhub.app.dto.course.CourseDetailsDTO;
import challenge.forumhub.app.dto.course.CourseRequestDTO;
import challenge.forumhub.app.dto.course.CourseSummaryDTO;
import challenge.forumhub.app.dto.course.CourseUpdateDTO;
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

public interface CourseControllerDoc {

    @Operation(summary = "Cria um novo curso (ADMIN)", description = "Cria um novo curso associado a uma ou mais categorias existentes.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Curso criado com sucesso"),

            @ApiResponse(
                    responseCode = "400",
                    description = "Dados inválidos",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class))),

            @ApiResponse(
                    responseCode = "409",
                    description = "Curso com nome já existente",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class))),

            @ApiResponse(
                    responseCode = "422",
                    description = "IDs de categorias inválidos",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class))),

            @ApiResponse(responseCode = "500",
                    description = "Erro interno",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    ResponseEntity<CourseDetailsDTO> create(@RequestBody CourseRequestDTO dto);


    @Operation(summary = "Lista todos os cursos ativos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cursos retornados com sucesso"),

            @ApiResponse(
                    responseCode = "500",
                    description = "Erro interno",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    ResponseEntity<List<CourseSummaryDTO>> getAllCourses();

    @Operation(summary = "Busca um curso por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Curso encontrado"),

            @ApiResponse(
                    responseCode = "404",
                    description = "Curso não encontrado",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class))),

            @ApiResponse(
                    responseCode = "500",
                    description = "Erro interno",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    ResponseEntity<CourseDetailsDTO> getCourseById(
            @Parameter(description = "ID do curso")
            @PathVariable("courseId") long id
    );

    @Operation(summary = "Atualiza um curso existente (ADMIN)", description = "Atualiza o nome e categorias associadas a um curso.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Curso atualizado com sucesso"),

            @ApiResponse(
                    responseCode = "400",
                    description = "Dados inválidos",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class))),

            @ApiResponse(
                    responseCode = "404",
                    description = "Curso não encontrado",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class))),

            @ApiResponse(
                    responseCode = "409",
                    description = "Nome de curso já existente",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class))),

            @ApiResponse(
                    responseCode = "422",
                    description = "IDs de categorias inválidos",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class))),

            @ApiResponse(
                    responseCode = "500",
                    description = "Erro interno",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    ResponseEntity<CourseDetailsDTO> updateCourse(
            @Parameter(description = "ID do curso a ser atualizado")
            @PathVariable("courseId") long id,
            @RequestBody CourseUpdateDTO dto);


    @Operation(summary = "Remove (desativa) um curso (ADMIN)", description = "Desativa logicamente um curso do sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Curso removido com sucesso"),

            @ApiResponse(
                    responseCode = "404",
                    description = "Curso não encontrado",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class))),

            @ApiResponse(
                    responseCode = "500",
                    description = "Erro interno",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    ResponseEntity<Void> deleteCourse(
            @Parameter(description = "ID do curso a ser removido")
            @PathVariable("courseId") long id);

}
