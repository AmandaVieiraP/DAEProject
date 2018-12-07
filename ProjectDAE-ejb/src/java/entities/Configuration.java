/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Iolanda
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(discriminatorType = DiscriminatorType.STRING)
@DiscriminatorValue("Configuration")
public class Configuration extends ConfigurationSuper implements Serializable {

    @NotNull
    private List<String> services;
    
    @NotNull
    @ManyToMany(mappedBy = "configurations")
    private List<ConfigurationModule> modules;
    
    @NotNull
    private List<License> licenses;
    
    @NotNull
    @ManyToMany
    @JoinTable(name = "CONFIGURATIONS_CLIENTS", joinColumns = @JoinColumn(name = "CONFIG_CODE", referencedColumnName = "CODE"),
            inverseJoinColumns = @JoinColumn(name = "CLIENT_USERNAME", referencedColumnName = "USERNAME"))
    private List<Client> clients;

    public Configuration() {
        this.services=new LinkedList<>();
        this.modules=new LinkedList<>();
        this.licenses=new LinkedList<>();
        this.clients=new LinkedList<>();
    }

    public Configuration(int code, String description, Software software, Contract contract, String version) {
        super(code, description, software, contract, version);
        this.services=new LinkedList<>();
        this.modules=new LinkedList<>();
        this.licenses=new LinkedList<>();
        this.clients=new LinkedList<>();
    }

    public List<String> getServices() {
        return services;
    }

    public void setServices(List<String> services) {
        this.services = services;
    }

    public List<ConfigurationModule> getModules() {
        return modules;
    }

    public void setModules(List<ConfigurationModule> modules) {
        this.modules = modules;
    }

    public List<License> getLicenses() {
        return licenses;
    }

    public void setLicenses(List<License> licenses) {
        this.licenses = licenses;
    }

    public List<Client> getClients() {
        return clients;
    }

    public void setClients(List<Client> clients) {
        this.clients = clients;
    }
    
    public void addLicense(License license) {
        if (license != null && !licenses.contains(license)) {
            licenses.add(license);
        }
    }

    public void removeLicense(License license) {
        if (license != null && licenses.contains(license)) {
            licenses.remove(license);
        }
    }
    
    public void addService(String service) {
        if (service != null && !services.contains(service)) {
            services.add(service);
        }
    }

    public void removeService(String service) {
        if (service != null && services.contains(service)) {
            services.remove(service);
        }
    }
    
    public void addClient(Client client) {
        if (client != null && !clients.contains(client)) {
            clients.add(client);
        }
    }

    public void removeClient(Client client) {
        if (client != null && clients.contains(client)) {
            clients.remove(client);
        }
    }
    
    public void addModule(ConfigurationModule module) {
        if (module != null && !modules.contains(module)) {
            modules.add(module);
        }
    }

    public void removeModule(ConfigurationModule module) {
        if (module != null && modules.contains(module)) {
            modules.remove(module);
        }
    }
    
}
