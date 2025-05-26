package challenge.forumhub.app.controller;

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
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    private final CategoryMapper categoryMapper;

    @PostMapping
    public ResponseEntity<CategoryDetailsDTO> create(@RequestBody @Valid CategoryRequestDTO dto){
        Category categorySaved = categoryService.create(dto);
        return ResponseEntity.ok(categoryMapper.toDetailsDTO(categorySaved));
    }

    @GetMapping
    public ResponseEntity<List<CategorySummaryDTO>> getAllCategories(){
        List<CategorySummaryDTO> categories = categoryService.getAll()
                .stream()
                .map(categoryMapper::toSummaryDTO).toList();
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryDetailsDTO> getCategoryById(@PathVariable("categoryId") long id){
        Category category = categoryService.getCategoryById(id);
        return ResponseEntity.ok(categoryMapper.toDetailsDTO(category));
    }

    @PutMapping("/{categoryId}")
    public ResponseEntity<CategoryDetailsDTO> updateCategory(@PathVariable("categoryId") long id,
                                                             @RequestBody @Valid CategoryUpdateDTO dataToUpdate){;
        Category updatedCategory = categoryService.upadateCategory(id, dataToUpdate);
        return ResponseEntity.ok(categoryMapper.toDetailsDTO(updatedCategory));
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<Void> delete(@PathVariable("categoryId") long id){
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }
}
