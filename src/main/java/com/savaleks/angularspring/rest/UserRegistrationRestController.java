package com.savaleks.angularspring.rest;

import com.savaleks.angularspring.dto.UserDTO;
import com.savaleks.angularspring.repo.UserJpaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserRegistrationRestController {

    public static final Logger LOGGER = LoggerFactory.getLogger(UserRegistrationRestController.class);

    private UserJpaRepository userJpaRepository;

    @Autowired
    public void setUserJpaRepository(UserJpaRepository userJpaRepository) {
        this.userJpaRepository = userJpaRepository;
    }

    @GetMapping("/")
    public ResponseEntity<List<UserDTO>> listAllUsers(){
        List<UserDTO> users = userJpaRepository.findAll();
        return new ResponseEntity<List<UserDTO>>(users, HttpStatus.OK);
    }

    @PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDTO> createUser(@RequestBody final UserDTO user){
        userJpaRepository.save(user);
        return new ResponseEntity<UserDTO>(user, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable("id") final Long id){
        UserDTO user = userJpaRepository.findById(id).orElse(null);
        return new ResponseEntity<UserDTO>(user, HttpStatus.OK);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDTO> updateUser(@PathVariable("id") final Long id, @RequestBody UserDTO user){

        // retrieve user data based on id and set it to user object of type UserDTO
        UserDTO currentUser = userJpaRepository.findById(id).orElse(null);

        // update user object data with user object data
        currentUser.setName(user.getName());
        currentUser.setAddress(user.getAddress());
        currentUser.setEmail(user.getEmail());

        // save user object
        userJpaRepository.saveAndFlush(currentUser);

        // return ResponseEntity object
        return new ResponseEntity<UserDTO>(currentUser, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<UserDTO> deleteUser(@PathVariable("id") final Long id){
        userJpaRepository.deleteById(id);
        return new ResponseEntity<UserDTO>(HttpStatus.NO_CONTENT);
    }
}