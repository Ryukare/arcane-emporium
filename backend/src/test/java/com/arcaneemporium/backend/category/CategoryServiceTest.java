package com.arcaneemporium.backend.category;

import com.arcaneemporium.backend.product.Product;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.NoSuchElementException;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceTest {

    @Mock
    CategoryRepository repository;
    @InjectMocks
    CategoryService service;

    @Test
    void createCategoryTest() {
        CategoryDTO categoryDTO = new CategoryDTO("test");

        ArgumentCaptor<Category> captor = ArgumentCaptor.forClass(Category.class);

        service.createCategory(categoryDTO);

        Mockito.verify(repository).save(captor.capture());
        Assertions.assertEquals("test", captor.getValue().getName());
    }

    @Test
    void getCategoryTest() {
        Category category = new Category("test");

        Mockito.when(repository.findById(1)).thenReturn(Optional.of(category));
        Mockito.when(repository.findById(2)).thenReturn(Optional.empty());

        CategoryDTO categoryDTO = service.getCategory(1);

        Assertions.assertEquals("test", categoryDTO.name());
        Assertions.assertThrows(NoSuchElementException.class, () -> service.getCategory(2));
    }

    @Test
    void updateCategoryTest() {
        Category category = new Category("test");
        CategoryDTO categoryDTO = new CategoryDTO("update");

        Mockito.when(repository.findById(1)).thenReturn(Optional.of(category));
        Mockito.when(repository.findById(2)).thenReturn(Optional.empty());

        ArgumentCaptor<Category> captor = ArgumentCaptor.forClass(Category.class);

        CategoryDTO updatedCategoryDTO = service.updateCategory(1, categoryDTO);

        Mockito.verify(repository).save(captor.capture());
        Assertions.assertEquals("update", captor.getValue().getName());

        Assertions.assertEquals("update", updatedCategoryDTO.name());
        Assertions.assertThrows(NoSuchElementException.class, () -> service.updateCategory(2, categoryDTO));
    }

    @Test
    void deleteCategoryTest() {
        Category category = new Category("test");

        Category categoryWithProduct = new Category("product");
        Product product = new Product("test", "desc", 1,
                new BigDecimal(1), categoryWithProduct);
        categoryWithProduct.getProducts().add(product);

        Mockito.when(repository.findById(1)).thenReturn(Optional.of(category));
        Mockito.when(repository.findById(2)).thenReturn(Optional.of(categoryWithProduct));
        Mockito.when(repository.findById(3)).thenReturn(Optional.empty());

        CategoryDTO categoryDTO = service.deleteCategory(1);

        Mockito.verify(repository).delete(Mockito.any(Category.class));
        Assertions.assertEquals("test", categoryDTO.name());

        Assertions.assertThrows(IllegalStateException.class, () -> service.deleteCategory(2));
        Assertions.assertThrows(NoSuchElementException.class, () -> service.deleteCategory(3));
    }

}
