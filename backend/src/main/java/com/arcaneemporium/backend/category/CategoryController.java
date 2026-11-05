package com.arcaneemporium.backend.category;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService service;

    public CategoryController(CategoryService service){
        this.service = service;
    }

    @GetMapping("/{id}")
    public CategoryDTO getCategory(@PathVariable int id){
        return service.getCategory(id);
    }

    @GetMapping("/list")
    public List<CategoryDTO> getCategoryList(){
        return service.getCategories();
    }

    @PostMapping("/add_category")
    public Category createCategory(@RequestBody @Valid CategoryDTO categoryDTO){
        return service.createCategory(categoryDTO);
    }

    @PutMapping("/{id}")
    public Category updateCategory(@PathVariable int id, @RequestBody @Valid CategoryDTO categoryDTO){
        return service.updateCategory(id, categoryDTO);
    }

    @DeleteMapping("/{id}")
    public Category deleteCategory(@PathVariable int id){
        return service.deleteCategory(id);
    }
}
