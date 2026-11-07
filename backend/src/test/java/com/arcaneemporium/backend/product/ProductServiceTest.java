package com.arcaneemporium.backend.product;

import com.arcaneemporium.backend.category.Category;
import com.arcaneemporium.backend.category.CategoryDTO;
import com.arcaneemporium.backend.category.CategoryService;
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
public class ProductServiceTest {

    @Mock
    ProductRepository repository;
    @Mock
    CategoryService categoryService;
    @InjectMocks
    ProductService service;

    @Test
    void createProductTest() {
        Category category = new Category("test");
        CreateProductDTO productDTO = new CreateProductDTO("test", "desc", 1,
                new BigDecimal(1), 1);

        Mockito.when(categoryService.getCategoryEntity(1)).thenReturn(category);
        ArgumentCaptor<Product> captor = ArgumentCaptor.forClass(Product.class);

        service.createProduct(productDTO);

        Mockito.verify(repository).save(captor.capture());
        Assertions.assertEquals("desc", captor.getValue().getDescription());
    }

    @Test
    void getProductTest() {
        Product product = new Product("test", "desc", 1,
                new BigDecimal(1), new Category("test"));

        Mockito.when(repository.findById(1)).thenReturn(Optional.of(product));
        Mockito.when(repository.findById(2)).thenReturn(Optional.empty());

        ProductDTO productDTO = service.getProduct(1);

        Assertions.assertEquals("desc", productDTO.description());
        Assertions.assertThrows(NoSuchElementException.class, () -> service.getProduct(2));
    }

    @Test
    void updateProductTest() {
        Product product = new Product("test", "desc", 1,
                new BigDecimal(1), new Category("test"));
        CreateProductDTO productDTO = new CreateProductDTO("test", "new desc", 1,
                new BigDecimal(1), 1);

        Category category = new Category("test");
        Mockito.when(categoryService.getCategoryEntity(1)).thenReturn(category);

        Mockito.when(repository.findById(1)).thenReturn(Optional.of(product));
        Mockito.when(repository.findById(2)).thenReturn(Optional.empty());

        ArgumentCaptor<Product> captor = ArgumentCaptor.forClass(Product.class);

        CreateProductDTO updatedProductDTO = service.updateProduct(1, productDTO);

        Mockito.verify(repository).save(captor.capture());
        Assertions.assertEquals("new desc", captor.getValue().getDescription());
        Assertions.assertEquals("new desc", updatedProductDTO.description());
        Assertions.assertThrows(NoSuchElementException.class, () -> service.updateProduct(2, productDTO));
    }

    @Test
    void deleteCategoryTest() {
        Product product = new Product("test", "desc", 1,
                new BigDecimal(1), new Category("test"));

        Mockito.when(repository.findById(1)).thenReturn(Optional.of(product));
        Mockito.when(repository.findById(2)).thenReturn(Optional.empty());

        ProductDTO productDTO = service.deleteProduct(1);

        Mockito.verify(repository).delete(Mockito.any(Product.class));

        Assertions.assertEquals("desc", productDTO.description());
        Assertions.assertThrows(NoSuchElementException.class, () -> service.deleteProduct(2));
    }
}
