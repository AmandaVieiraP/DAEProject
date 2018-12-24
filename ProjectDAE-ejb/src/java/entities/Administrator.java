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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

/**
 *
 * @author Amanda
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(discriminatorType = DiscriminatorType.STRING)
@DiscriminatorValue("Administrator")
@NamedQueries({
    @NamedQuery(name = "getAllAdministrators",
            query = "SELECT a from Administrator a ORDER BY a.name") // é uma query à entidade não à tabela
})
public class Administrator extends User implements Serializable {

    private String name;
    private String jobRole;

    public Administrator() {
        super();
    }

    public Administrator(String username, String password, String name, String email, String jobRole) {
        super(username, email, password, GROUP.Administrator);
        this.name = name;
        this.jobRole = jobRole;
    }

    public String getName() {
        return name;
    }

    public String getJobRole() {
        return jobRole;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setJobRole(String jobRole) {
        this.jobRole = jobRole;
    }
}
