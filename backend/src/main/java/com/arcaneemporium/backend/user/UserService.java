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
        return repository.findById(id).orElseThrow(() ->
                new NoSuchElementException(STR."User with \{id} not found"));
    }

    private User getUserEntity(String username){
        return repository.findByUsername(username).orElseThrow(() ->
                new NoSuchElementException("Wrong login or password"));
    }

    public UserDTO getUser(int id){
        return UserMapper.mapToUserDTO(getUserEntity(id));
    }

    public String login(LoginUserDTO loginUserDTO){
        User user = getUserEntity(loginUserDTO.username());

        if(passwordEncoder.matches(loginUserDTO.password(), user.getPassword())){
            return "Login confirmed";
        }
        else{
            throw new IllegalArgumentException("Wrong login or password");
        }
    }

    public List<UserDTO> getAllUsers(){
        return repository.findAll().stream().map(UserMapper::mapToUserDTO)
                .toList();
    }

    private void checkUniqueConstraint(RegisterUserDTO registerUserDTO){
        if(repository.existsByEmail(registerUserDTO.email())){
            throw new IllegalArgumentException("This email is already in use");
        }
        if(repository.existsByUsername(registerUserDTO.username())){
            throw new IllegalArgumentException("This username is already in use");
        }
    }

    public String createUser(RegisterUserDTO registerUserDTO){
        checkUniqueConstraint(registerUserDTO);
        String encodedPassword = passwordEncoder.encode(registerUserDTO.password());
        repository.save(UserMapper.mapToUser(registerUserDTO, encodedPassword, Role.USER));
        return STR."account \{registerUserDTO.username()} has been created";
    }

    public String createAdmin(RegisterUserDTO registerUserDTO){
        checkUniqueConstraint(registerUserDTO);
        String encodedPassword = passwordEncoder.encode(registerUserDTO.password());
        repository.save(UserMapper.mapToUser(registerUserDTO, encodedPassword, Role.ADMIN));
        return STR."account \{registerUserDTO.username()} has been created";
    }

}
