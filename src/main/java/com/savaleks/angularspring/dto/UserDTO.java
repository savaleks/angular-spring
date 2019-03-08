package com.savaleks.angularspring.dto;

import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "users")
public class UserDTO {

    @Id
    @GeneratedValue
    @Column(name = "user_id")
    private Long id;

    @NotEmpty(message = "error.name.empty")
    @Length(max = 40, message = "error.name.length")
    private String name;

    @NotEmpty(message = "error.address.empty")
    @Length(max = 120, message = "error.address.length")
    private String address;

    @NotEmpty(message = "error.email.empty")
    @Email(message = "error.email.email")
    @Length(max = 50, message = "error.email.length")
    private String email;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
