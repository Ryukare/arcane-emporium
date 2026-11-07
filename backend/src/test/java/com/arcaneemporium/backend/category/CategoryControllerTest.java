package com.arcaneemporium.backend.category;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;


import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CategoryController.class)
public class CategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CategoryService service;

    @Test
    void getCategoryTest() throws Exception {
        Mockito.when(service.getCategory(1)).thenReturn(new CategoryDTO("test"));
        Mockito.when(service.getCategory(2)).thenThrow(new NoSuchElementException());


        mockMvc.perform(
                        MockMvcRequestBuilders.get("/categories/1")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("test"));

        mockMvc.perform(
                        MockMvcRequestBuilders.get("/categories/2")
                )
                .andExpect(status().isNotFound());
    }

    @Test
    void getCategoryListTest() throws Exception {
        List<CategoryDTO> categoryDTOList = new ArrayList<>(){
            {
                add(new CategoryDTO("test1"));
                add(new CategoryDTO("test2"));
            }
        };

        Mockito.when(service.getCategories()).thenReturn(categoryDTOList);

        mockMvc.perform(
                        MockMvcRequestBuilders.get("/categories/list")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("test1"))
                .andExpect(jsonPath("$[1].name").value("test2"));
    }

    @Test
    void deleteCategoryTest() throws Exception {
        Mockito.when(service.deleteCategory(1)).thenReturn(new CategoryDTO("test"));
        Mockito.when(service.deleteCategory(2)).thenThrow(new IllegalStateException());
        Mockito.when(service.deleteCategory(3)).thenThrow(new NoSuchElementException());

        mockMvc.perform(
                        MockMvcRequestBuilders.delete("/categories/1")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("test"));

        mockMvc.perform(
                        MockMvcRequestBuilders.delete("/categories/2")
                )
                .andExpect(status().isConflict());

        mockMvc.perform(
                        MockMvcRequestBuilders.delete("/categories/3")
                )
                .andExpect(status().isNotFound());
    }


    @Test
    void createCategory() throws Exception {
        CategoryDTO categoryDTO = new CategoryDTO("test");
        Mockito.when(service.createCategory(categoryDTO))
                .thenReturn(categoryDTO);

        mockMvc.perform(
                        MockMvcRequestBuilders.post("/categories/add_category")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("""
                                {
                                    "name": "test"
                                }
                                """)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("test"));

        mockMvc.perform(
                        MockMvcRequestBuilders.post("/categories/add_category")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("""
                                {
                                    "name": "t"
                                }
                                """)
                )
                .andExpect(status().isBadRequest());

    }

    @Test
    void updateCategory() throws Exception {
        CategoryDTO categoryDTO = new CategoryDTO("test");

        Mockito.when(service.updateCategory(1, categoryDTO))
                .thenReturn(categoryDTO);
        Mockito.when(service.updateCategory(2, categoryDTO))
                .thenThrow(new NoSuchElementException());

        mockMvc.perform(
                        MockMvcRequestBuilders.put("/categories/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("""
                                {
                                    "name": "test"
                                }
                                """)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("test"));

        mockMvc.perform(
                        MockMvcRequestBuilders.put("/categories/2")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("""
                                {
                                    "name": "test"
                                }
                                """)
                )
                .andExpect(status().isNotFound());
    }
}
