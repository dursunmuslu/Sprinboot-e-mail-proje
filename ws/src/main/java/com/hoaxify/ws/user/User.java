package com.hoaxify.ws.user;

import com.hoaxify.ws.user.validation.UniqueEmail;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
@Table(name="users", uniqueConstraints=@UniqueConstraint(columnNames ="email"))
public class User {

    @Id
    @GeneratedValue
    long id;

    public void setId(long id) {
        this.id = id;
    }


    String username;

    String email;

    String password;



    String activitionToken;


    boolean active = false;

    public String getActivitionToken(){
        return activitionToken;
    }
    public void setActivitionToken(String activitionToken){
        this.activitionToken = activitionToken;
    }

    public boolean isActive() {
        return active;
    }
    public void setActive(boolean active) {
        this.active = active;
    }


    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }




    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }



    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
