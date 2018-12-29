/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtos;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Amanda
 */
@XmlRootElement(name = "Administrator")
@XmlAccessorType(XmlAccessType.FIELD)
public class AdministratorDTO extends UserDTO implements Serializable {

    private String name;
    private String jobRole;

    public AdministratorDTO() {
    }

    public AdministratorDTO(String name, String email, String jobRole, String username, String password) {
        super(username, email, password);
        this.name = name;
        this.jobRole = jobRole;
    }

    @Override
    public void reset() {
        super.reset();
        setName(null);
        setJobRole(null);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getJobRole() {
        return jobRole;
    }

    public void setJobRole(String jobRole) {
        this.jobRole = jobRole;
    }

}
