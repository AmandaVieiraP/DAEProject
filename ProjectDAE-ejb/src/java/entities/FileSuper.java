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
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Iolanda
 */
@Entity
@Table(name = "FILES")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "FILE_TYPE", discriminatorType = DiscriminatorType.STRING)
public class FileSuper implements Serializable {

    @Id
    private String filename;

    @NotNull
    private String mimetype;
    
    @NotNull
     @JoinTable(name = "FILES_CONFIGURATIONS", joinColumns = @JoinColumn(name = "FILENAME", referencedColumnName = "FILENAME"),
            inverseJoinColumns = @JoinColumn(name = "CONFIGURATION_CODE", referencedColumnName = "CODE"))
    private List<ConfigurationSuper> configurations;

    public FileSuper() {
        this.configurations=new LinkedList<>();
    }

    public FileSuper(String filename, String mimetype) {
        this.filename = filename;
        this.mimetype = mimetype;
        this.configurations=new LinkedList<>();
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getMimetype() {
        return mimetype;
    }

    public void setMimetype(String mimetype) {
        this.mimetype = mimetype;
    }

    public List<ConfigurationSuper> getConfigurations() {
        return configurations;
    }

    public void setConfigurations(List<ConfigurationSuper> configurations) {
        this.configurations = configurations;
    }
    
    public void addConfigurations(ConfigurationSuper conf) {

        if (conf != null && !configurations.contains(conf)) {
            configurations.add(conf);
        }
    }

    public void removeConfigurations(ConfigurationSuper config) {

        if (config != null && configurations.contains(config)) {
            configurations.remove(config);
        }
    }
}
