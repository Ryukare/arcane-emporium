package com.arcaneemporium.backend.user;

import com.arcaneemporium.backend.utils.UserMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class UserService {

    private final UserRepository repository;

    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository repository, PasswordEncoder passwordEncoder){
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    private User getUserEntity(int id){
        return repository.findById(id).orElseThrow(() -> new NoSuchElementException(
                "User with id %d not found".formatted(id)
        ));
    }

    public UserDTO getUser(int id){
        return UserMapper.mapToUserDTO(getUserEntity(id));
    }

    public List<UserDTO> getAllUsers(){
        return repository.findAll().stream().map(UserMapper::mapToUserDTO)
                .toList();
    }

    public String createAdmin(RegisterUserDTO registerUserDTO){
        String encodedPassword = passwordEncoder.encode(registerUserDTO.password());
        repository.save(UserMapper.mapToUser(registerUserDTO, encodedPassword, Role.ADMIN));
        return registerUserDTO.username();
    }

    public String createUser(RegisterUserDTO registerUserDTO){
        String encodedPassword = passwordEncoder.encode(registerUserDTO.password());
        repository.save(UserMapper.mapToUser(registerUserDTO, encodedPassword, Role.USER));
        return registerUserDTO.username();
    }
}
