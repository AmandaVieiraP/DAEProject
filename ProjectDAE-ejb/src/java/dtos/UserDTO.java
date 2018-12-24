/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtos;

import java.io.Serializable;

/**
 *
 * @author Amanda
 */
public class UserDTO implements Serializable {

    private String username;
    private String password;
    private String email;

    public UserDTO() {
    }

    public UserDTO(String username, String email, String password) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public void reset() {
        setUsername(null);
        setPassword(null);
        setEmail(null);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    

}
