/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import entities.UserGroup.GROUP;
import java.io.Serializable;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;


/**
 *
 * @author Amanda
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(discriminatorType = DiscriminatorType.STRING)
@DiscriminatorValue("Administrator")
public class Administrator extends User implements Serializable {
    private String name;
    private String email;
    private String jobRole;

    public Administrator() {
    }

    public Administrator(String username, String password, String name, String email, String jobRole) {
        super(username, password, GROUP.Administrator);
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
