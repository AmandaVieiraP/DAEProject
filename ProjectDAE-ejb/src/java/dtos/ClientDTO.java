/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtos;

import java.io.Serializable;
import java.util.logging.Logger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Amanda
 */

@XmlRootElement(name = "Client")
@XmlAccessorType(XmlAccessType.FIELD)
public class ClientDTO extends UserDTO implements Serializable {
    private String address;
    private String companyName; 
    private String contactPerson;

    public ClientDTO() {
    }

    public ClientDTO(String address, String companyName, String contactPerson, String username, String password) {
        super(username, password);
        this.address = address;
        this.companyName = companyName;
        this.contactPerson = contactPerson;
    }

    @Override
    public void reset () {
        super.reset();
        setAddress(null);
        setCompanyName(null);
        setContactPerson(null);
    }
    
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }
    
    
    
}
