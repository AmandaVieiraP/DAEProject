/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 *
 * @author Amanda
 */
@Entity
@Table(name = "ADMINISTRATORS")
@AttributeOverrides({
    @AttributeOverride(name="username", column=@Column(name="username")),
    @AttributeOverride(name="password", column=@Column(name="password"))
})
public class Administrator extends User implements Serializable {
    private String name;
    private String email;
    private String jobRole;

    public Administrator() {
    }

    public Administrator(String name, String email, String jobRole, String username, String password) {
        super(username, password);
        this.name = name;
        this.email = email;
        this.jobRole = jobRole;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getJobRole() {
        return jobRole;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setJobRole(String jobRole) {
        this.jobRole = jobRole;
    }
    
    
    
}
