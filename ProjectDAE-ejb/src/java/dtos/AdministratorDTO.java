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
public class AdministratorDTO extends UserDTO implements Serializable  {
    private String name;
    private String email;
    private String jobRole;

    public AdministratorDTO() {
    }

    public AdministratorDTO(String name, String email, String jobRole, String username, String password) {
        super(username, password);
        this.name = name;
        this.email = email;
        this.jobRole = jobRole;
    }
    
    @Override
    public void reset() {
        super.reset();
        setName(null);
        setEmail(null);
        setJobRole(null);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getJobRole() {
        return jobRole;
    }

    public void setJobRole(String jobRole) {
        this.jobRole = jobRole;
    }
    
     
    
}
