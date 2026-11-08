package com.arcaneemporium.backend.product;

import com.arcaneemporium.backend.category.CategoryDTO;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ProductService service;


    @Test
    void getProductTest() throws Exception {
        Mockito.when(service.getProduct(1)).thenReturn(new ProductDTO("test", "desc",
                new BigDecimal(1), new CategoryDTO("test")));
        Mockito.when(service.getProduct(2)).thenThrow(new NoSuchElementException());


        mockMvc.perform(
                        MockMvcRequestBuilders.get("/products/1")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.description").value("desc"));

        mockMvc.perform(
                        MockMvcRequestBuilders.get("/products/2")
                )
                .andExpect(status().isNotFound());
    }

    @Test
    void getProductListTest() throws Exception {

        CategoryDTO categoryDTO = new CategoryDTO("test");

        List<ProductDTO> productDTOList = new ArrayList<>(){
            {
                add(new ProductDTO("test", "desc",
                        new BigDecimal(1), categoryDTO));
                add(new ProductDTO("test2", "desc2",
                        new BigDecimal(2), categoryDTO));
            }
        };

        Mockito.when(service.getProducts()).thenReturn(productDTOList);

        mockMvc.perform(
                        MockMvcRequestBuilders.get("/products/list")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].description").value("desc"))
                .andExpect(jsonPath("$[1].description").value("desc2"));
    }

    @Test
    void deleteProductTest() throws Exception {
        Mockito.when(service.deleteProduct(1)).thenReturn(new ProductDTO("test", "desc",
                new BigDecimal(1), new CategoryDTO("test")));
        Mockito.when(service.deleteProduct(2)).thenThrow(new NoSuchElementException());

        mockMvc.perform(
                        MockMvcRequestBuilders.delete("/products/1")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.description").value("desc"));

        mockMvc.perform(
                        MockMvcRequestBuilders.delete("/products/2")
                )
                .andExpect(status().isNotFound());
    }

    @Test
    void createProduct() throws Exception {
        CreateProductDTO createProductDTO = new CreateProductDTO("test", "desc", 2,
                new BigDecimal(1), 1);
        CreateProductDTO createProductDTO2 = new CreateProductDTO("test", "desc", 2,
                new BigDecimal(1), 2);
        Mockito.when(service.createProduct(createProductDTO))
                .thenReturn(createProductDTO);
        Mockito.when(service.createProduct(createProductDTO2))
                .thenThrow(NoSuchElementException.class);

        mockMvc.perform(
                        MockMvcRequestBuilders.post("/products/add_product")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("""
                                {
                                    "name": "test",
                                    "description": "desc",
                                    "stock": 2,
                                    "price": 1,
                                    "categoryId": 1
                                }
                                """)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("test"));

        mockMvc.perform(
                        MockMvcRequestBuilders.post("/products/add_product")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("""
                                {
                                    "name": "test",
                                    "description": "desc",
                                    "stock": 2,
                                    "price": 1,
                                    "categoryId": 2
                                }
                                """)
                )
                .andExpect(status().isNotFound());

        mockMvc.perform(
                        MockMvcRequestBuilders.post("/products/add_product")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("""
                                {
                                    "name": "t",
                                    "description": "desc",
                                    "stock": 2,
                                    "price": 1,
                                    "categoryId": 1
                                }
                                """)
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateProduct() throws Exception {
        CreateProductDTO createProductDTO = new CreateProductDTO("test", "desc", 2,
                new BigDecimal(1), 1);

        Mockito.when(service.updateProduct(1, createProductDTO))
                .thenReturn(createProductDTO);
        Mockito.when(service.updateProduct(2, createProductDTO)).thenThrow(NoSuchElementException.class);

        mockMvc.perform(
                        MockMvcRequestBuilders.put("/products/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("""
                                {
                                    "name": "test",
                                    "description": "desc",
                                    "stock": 2,
                                    "price": 1,
                                    "categoryId": 1
                                }
                                """)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("test"));

        mockMvc.perform(
                        MockMvcRequestBuilders.put("/products/2")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("""
                                {
                                    "name": "test",
                                    "description": "desc",
                                    "stock": 2,
                                    "price": 1,
                                    "categoryId": 1
                                }
                                """)
                )
                .andExpect(status().isNotFound());

        mockMvc.perform(
                        MockMvcRequestBuilders.put("/products/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("""
                                {
                                    "name": "t",
                                    "description": "desc",
                                    "stock": 2,
                                    "price": 1,
                                    "categoryId": 1
                                }
                                """)
                )
                .andExpect(status().isBadRequest());
    }
}
