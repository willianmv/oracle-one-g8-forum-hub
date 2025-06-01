package challenge.forumhub.app.controller;

import challenge.forumhub.app.controller.doc.CategoryControllerDoc;
import challenge.forumhub.app.dto.category.CategoryDetailsDTO;
import challenge.forumhub.app.dto.category.CategoryRequestDTO;
import challenge.forumhub.app.dto.category.CategorySummaryDTO;
import challenge.forumhub.app.dto.category.CategoryUpdateDTO;
import challenge.forumhub.app.entity.Category;
import challenge.forumhub.app.mapper.CategoryMapper;
import challenge.forumhub.app.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Comparator;
import java.util.List;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController implements CategoryControllerDoc {

    private final CategoryService categoryService;

    private final CategoryMapper categoryMapper;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CategoryDetailsDTO> create(@RequestBody @Valid CategoryRequestDTO dto){
        Category categorySaved = categoryService.create(dto);
        CategoryDetailsDTO responseDTO = categoryMapper.toDetailsDTO(categorySaved);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(categorySaved.getId())
                .toUri();

        return ResponseEntity.created(location).body(responseDTO);
    }

    @GetMapping
    public ResponseEntity<List<CategorySummaryDTO>> getAllCategories(){
        List<CategorySummaryDTO> categories = categoryService.getAll()
                .stream()
                .map(categoryMapper::toSummaryDTO)
                .sorted(Comparator.comparing(CategorySummaryDTO::id))
                .toList();
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryDetailsDTO> getCategoryById(@PathVariable("categoryId") long id){
        Category category = categoryService.getCategoryById(id);
        return ResponseEntity.ok(categoryMapper.toDetailsDTO(category));
    }

    @PutMapping("/{categoryId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CategoryDetailsDTO> updateCategory(@PathVariable("categoryId") long id,
                                                             @RequestBody @Valid CategoryUpdateDTO dataToUpdate){;
        Category updatedCategory = categoryService.upadateCategory(id, dataToUpdate);
        return ResponseEntity.ok(categoryMapper.toDetailsDTO(updatedCategory));
    }

    @DeleteMapping("/{categoryId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable("categoryId") long id){
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }
}
