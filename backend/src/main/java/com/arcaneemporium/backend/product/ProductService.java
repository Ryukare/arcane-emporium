package com.arcaneemporium.backend.product;

import com.arcaneemporium.backend.category.Category;
import com.arcaneemporium.backend.category.CategoryService;
import com.arcaneemporium.backend.utils.ProductMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ProductService {
    private final ProductRepository repository;
    private final CategoryService categoryService;

    public ProductService(ProductRepository repository, CategoryService categoryService){
        this.repository = repository;
        this.categoryService = categoryService;
    }

    public CreateProductDTO createProduct(CreateProductDTO createProductDTO){
        Category category = categoryService.getCategoryEntity(createProductDTO.categoryId());
        repository.save(ProductMapper.mapToProduct(createProductDTO, category));
        return createProductDTO;
    }

    private Product getProductEntity(int id){
        return repository.findById(id).orElseThrow(() -> new NoSuchElementException(
                "Product with id %d not found".formatted(id)
        ));
    }

    public ProductDTO getProduct(int id){
        return ProductMapper.mapToProductDTO(getProductEntity(id));
    }

    public List<ProductDTO> getProducts(){
        return repository.findAll().stream().map(ProductMapper::mapToProductDTO)
                .toList();
    }

    public CreateProductDTO updateProduct(int id, CreateProductDTO createProductDTO){
        Product product = getProductEntity(id);
        product.setName(createProductDTO.name());
        product.setDescription(createProductDTO.description());
        product.setPrice(createProductDTO.price());
        product.setStock(createProductDTO.stock());

        Category category = categoryService.getCategoryEntity(createProductDTO.categoryId());
        product.setCategory(category);
        repository.save(product);
        return createProductDTO;
    }

    public ProductDTO deleteProduct(int id){
        Product product = getProductEntity(id);
        repository.delete(product);
        return ProductMapper.mapToProductDTO(product);
    }

}
