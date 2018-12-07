/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

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
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Iolanda
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(discriminatorType = DiscriminatorType.STRING)
@DiscriminatorValue("ConfigurationModule")
public class ConfigurationModule extends Module implements Serializable {

    @NotNull
    private String dbServerIp;

    @NotNull
    private String applicationServerIp;

    @NotNull
    @OneToMany(mappedBy = "configurationModule", cascade = CascadeType.REMOVE)
    private List<License> licenses;
    
    @NotNull
    @ManyToMany
    @JoinTable(name = "CONFIGURATIONS_MODULES", joinColumns = @JoinColumn(name = "MODULE_CODE", referencedColumnName = "CODE"),
            inverseJoinColumns = @JoinColumn(name = "CONFIG_CODE", referencedColumnName = "CODE"))
    private List<Configuration> configurations;

    public ConfigurationModule() {
        this.licenses = new LinkedList<>();
        this.configurations=new LinkedList<>();
    }

    public ConfigurationModule(int code, String description, Software software, String dbServerIp, String applicationServerIp) {
        super(code, description, software);
        this.applicationServerIp = applicationServerIp;
        this.dbServerIp = dbServerIp;
        this.configurations=new LinkedList<>();
        this.licenses = new LinkedList<>();
    }

    public String getDbServerIp() {
        return dbServerIp;
    }

    public void setDbServerIp(String dbServerIp) {
        this.dbServerIp = dbServerIp;
    }

    public String getApplicationServerIp() {
        return applicationServerIp;
    }

    public void setApplicationServerIp(String applicationServerIp) {
        this.applicationServerIp = applicationServerIp;
    }

    public List<License> getLicenses() {
        return licenses;
    }

    public void setLicenses(List<License> licenses) {
        this.licenses = licenses;
    }

    public List<Configuration> getConfigurations() {
        return configurations;
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
