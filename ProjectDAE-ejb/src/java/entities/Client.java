/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import entities.UserGroup.GROUP;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Amanda
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(discriminatorType = DiscriminatorType.STRING)
@DiscriminatorValue("Client")
@NamedQueries({
    @NamedQuery(name = "getAllClients",
            query = "SELECT c from Client c ORDER BY c.companyName") // é uma query à entidade não à tabela
})
public class Client extends User implements Serializable {

    private String address;
    private String companyName;
    // perguntar o que quer aqqui exatamente, ter numero?? 
    private String contactPerson;

    @NotNull
    @OneToMany(mappedBy = "client", cascade = CascadeType.REMOVE)
    private List<Configuration> configurations;

    public Client() {
        this.configurations = new LinkedList<>();
    }

    public Client(String username, String email, String password, String address, String companyName, String contactPerson) {
        super(username, email, password, GROUP.Client);
        this.address = address;
        this.companyName = companyName;
        this.contactPerson = contactPerson;
        this.configurations = new LinkedList<>();
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

    public List<Configuration> getConfigurations() {
        return configurations;
    }

    public void setConfigurations(List<Configuration> configurations) {
        this.configurations = configurations;
    }

    public void addConfiguration(Configuration configuration) {
        if (configuration != null && !configurations.contains(configuration)) {
            configurations.add(configuration);
        }
    }

    public void removeConfiguration(Configuration configuration) {
        if (configuration != null && configurations.contains(configuration)) {
            configurations.remove(configuration);
        }
    }

}
