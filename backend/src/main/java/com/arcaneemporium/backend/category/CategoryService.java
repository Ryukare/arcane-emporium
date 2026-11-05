package com.arcaneemporium.backend.category;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class CategoryService {
    private final CategoryRepository repository;

    public CategoryService(CategoryRepository repository){
        this.repository = repository;
    }

    public Category createCategory(CategoryDTO categoryDTO){
        return repository.save(new Category(categoryDTO.name()));
    }

    private Category getCategoryEntity(int id){
        return repository.findById(id).orElseThrow(() -> new NoSuchElementException(
                "Category with id %d not found".formatted(id)
        ));
    }

    public CategoryDTO getCategory(int id){
        return new CategoryDTO(getCategoryEntity(id).getName());
    }

    public List<CategoryDTO> getCategories(){
        return repository.findAll().stream().map((category) -> new CategoryDTO(category.getName()))
                .toList();
    }

    public Category updateCategory(int id, CategoryDTO categoryDTO){
        Category category = getCategoryEntity(id);
        category.setName(categoryDTO.name());
        return repository.save(category);
    }

    public Category deleteCategory(int id){
        Category category = getCategoryEntity(id);

        if(!category.getProducts().isEmpty()){
            throw new IllegalStateException("Category with id %d is used by products".formatted(id));
        }

        repository.delete(category);

        return category;
    }
}
