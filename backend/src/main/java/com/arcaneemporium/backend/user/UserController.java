package com.arcaneemporium.backend.user;

import com.arcaneemporium.backend.product.CreateProductDTO;
import com.arcaneemporium.backend.product.ProductDTO;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService service;

    public UserController(UserService service){
        this.service = service;
    }

    @GetMapping("/{id}")
    public UserDTO getUser(@PathVariable int id){
        return service.getUser(id);
    }

    @GetMapping("/list")
    public List<UserDTO> getAllUsers(){
        return service.getAllUsers();
    }

    @PostMapping("/admin")
    public String createAdmin(@RequestBody @Valid RegisterUserDTO registerUserDTO){
        return service.createAdmin(registerUserDTO);
    }

    @PostMapping("/register")
    public String createUser(@RequestBody @Valid RegisterUserDTO registerUserDTO){
        return service.createUser(registerUserDTO);
    }

    @PostMapping("/login")
    public String login(@RequestBody @Valid LoginUserDTO loginUserDTO){
        return service.login(loginUserDTO);
    }
}
