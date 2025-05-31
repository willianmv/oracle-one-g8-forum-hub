package challenge.forumhub.app.service;

import challenge.forumhub.app.dto.category.CategoryRequestDTO;
import challenge.forumhub.app.dto.category.CategoryUpdateDTO;
import challenge.forumhub.app.entity.Category;
import challenge.forumhub.app.entity.User;
import challenge.forumhub.app.exception.ResourceAlreadyExistsException;
import challenge.forumhub.app.exception.ResourceNotFoundException;
import challenge.forumhub.app.repository.CategoryRepository;
import challenge.forumhub.app.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final AuthenticatedUserService authenticatedUserService;

    @Transactional
    public Category create(CategoryRequestDTO dto){
        User user = authenticatedUserService.getAuthenticatedUserEntity();
        validateToCreate(dto.name());
        Category category = new Category();
        category.setName(dto.name());
        category.setCreatedBy(user);
        return categoryRepository.save(category);
    }

    public List<Category> getAll() {
        return categoryRepository.findAllByActiveTrue();
    }

    public Category getCategoryById(long id) {
        return categoryRepository.findByIdAndActiveTrue(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoria não encontrada com ID: "+id));
    }

    @Transactional
    public Category upadateCategory(long id, CategoryUpdateDTO dataToUpdate) {
        Category categoryToUpdate = getCategoryById(id);
        validateToUpdate(dataToUpdate.name(), id);
        categoryToUpdate.setName(dataToUpdate.name());
        return categoryRepository.save(categoryToUpdate);
    }

    @Transactional
    public void deleteCategory(long id) {
        Category category = getCategoryById(id);
        category.setActive(false);
        categoryRepository.save(category);
    }

    private void validateToCreate(String name){
        if(categoryRepository.existsByNameIgnoreCase(name)){
            throw new ResourceAlreadyExistsException("Categoria já registrada com o nome: "+name);
        }
    }

    private void validateToUpdate(String name, long id){
        if(categoryRepository.existsByNameIgnoreCaseAndIdNot(name, id)){
            throw new ResourceAlreadyExistsException("Existe uma categoria com outro ID já registrada com o nome: "+name);
        }
    }

    public Set<Category> findAllByIds(Set<Long> categoryIds) {
        return categoryRepository.findAllByIdInAndActiveTrue(categoryIds);
    }
}

