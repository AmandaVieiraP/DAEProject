/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Iolanda
 */
@Entity
@Table(name = "EXTENSIONS")
@NamedQueries({
    @NamedQuery(name = "getAllExtensionsBySoftware", query = "SELECT e FROM Extension e WHERE e.software.code=?1"),
})
public class Extension implements Serializable {

    @Id
    private int code;

    @NotNull
    private String name;

    private String description;

    @NotNull
    private List<String> versions;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "SOFTWARE_CODE")
    private Software software;
    
    @NotNull
    @ManyToMany
    @JoinTable(name = "CONFIGURATIONS_EXTENSIONS", joinColumns = @JoinColumn(name = "EXTENSION_CODE", referencedColumnName = "CODE"),
            inverseJoinColumns = @JoinColumn(name = "CONFIGURATION_CODE", referencedColumnName = "CODE"))
    private List<ConfigurationSuper> configurations;

    public Extension() {
        this.versions = new LinkedList<>();
        this.configurations=new LinkedList<>();
    }

    public Extension(int code, String name, String description, Software software) {
        this.code = code;
        this.name = name;
        this.description = description;
        this.software = software;
        this.versions = new LinkedList<>();
        this.configurations=new LinkedList<>();
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getVersions() {
        return versions;
    }

    public void setVersions(List<String> versions) {
        this.versions = versions;
    }

    public Software getSoftware() {
        return software;
    }

    public List<ConfigurationSuper> getConfigurations() {
        return configurations;
    }

    public void setConfigurations(List<ConfigurationSuper> configurations) {
        this.configurations = configurations;
    }

    public void setSoftware(Software software) {
        this.software = software;
    }

    public void addVersion(String version) {
        if (version != null && !versions.contains(version)) {
            versions.add(version);
        }
    }

    public void addConfiguration(ConfigurationSuper config) {
        if (config != null && !configurations.contains(config)) {
            configurations.add(config);
        }
    }
    
    public void removeConfiguration(ConfigurationSuper config) {
        if (config != null && configurations.contains(config)) {
            configurations.remove(config);
        }
    }

}
