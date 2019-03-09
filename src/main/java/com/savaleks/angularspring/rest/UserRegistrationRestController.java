package com.savaleks.angularspring.rest;

import com.savaleks.angularspring.dto.UserDTO;
import com.savaleks.angularspring.exception.CustomErrorType;
import com.savaleks.angularspring.repo.UserJpaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
        if (users.isEmpty()){
            return new ResponseEntity<List<UserDTO>>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<UserDTO>>(users, HttpStatus.OK);
    }

    @PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDTO> createUser(@Valid @RequestBody final UserDTO user){
        LOGGER.info("Creating user: {}", user);
        if (userJpaRepository.findByName(user.getName()) != null){
            return new ResponseEntity<UserDTO>(new CustomErrorType(
                    "Unable to create new user. A user " + user.getName() + " already exists"
            ), HttpStatus.CONFLICT);
        }
        userJpaRepository.save(user);
        return new ResponseEntity<UserDTO>(user, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable("id") final Long id){
        UserDTO user = userJpaRepository.findById(id).orElse(null);
        if (user == null){
            return new ResponseEntity<UserDTO>(
                    new CustomErrorType("User with id " + id + " not found"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<UserDTO>(user, HttpStatus.OK);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDTO> updateUser(@PathVariable("id") final Long id, @RequestBody UserDTO user){

        // retrieve user data based on id and set it to user object of type UserDTO
        UserDTO currentUser = userJpaRepository.findById(id).orElse(null);

        if (currentUser == null){
            return new ResponseEntity<UserDTO>(
                    new CustomErrorType("Unable to update. User with id " + id + " not found."),
                    HttpStatus.NOT_FOUND
            );
        }

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
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<UserDTO> deleteUser(@PathVariable("id") final Long id){
        // find user in database by id, if not exists throw exception
        UserDTO user = userJpaRepository.findById(id).orElse(null);
        if (user == null){
            return new ResponseEntity<UserDTO>(
                    new CustomErrorType("Unable to delete. User with id " + id + " not found."),
                    HttpStatus.NOT_FOUND);
        }
        userJpaRepository.deleteById(id);
        return new ResponseEntity<UserDTO>(
                new CustomErrorType("Deleted user with id " + id + "."),
                HttpStatus.NO_CONTENT);
    }
}
