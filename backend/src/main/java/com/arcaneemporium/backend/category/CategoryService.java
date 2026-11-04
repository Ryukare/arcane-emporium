package com.arcaneemporium.backend.category;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    private CategoryRepository repository;

    public CategoryService(CategoryRepository repository){
        this.repository = repository;
    }

    public List<CategoryDTO> getCategories(){
        return repository.findAll().stream().map((category) -> new CategoryDTO(category.getName()))
                .toList();
    }

    public Category createCategory(CategoryDTO categoryDTO){
        return repository.save(new Category(categoryDTO.name()));
    }

}
