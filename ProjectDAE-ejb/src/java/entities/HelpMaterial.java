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
@DiscriminatorValue("HelpMaterial")
public class HelpMaterial extends FileSuper implements Serializable {
    
    @NotNull
    @ManyToMany
    @JoinTable(name = "HELPMATERIALS_CONFIGURATIONS", joinColumns = @JoinColumn(name = "HELP_FILENAME", referencedColumnName = "FILENAME"),
            inverseJoinColumns = @JoinColumn(name = "CONFIGURATION_CODE", referencedColumnName = "CODE"))
    private List<ConfigurationSuper> configurations;

    public HelpMaterial() {
        this.configurations=new LinkedList<>();
    }

    public HelpMaterial(String filename, String mimetype) {
        super(filename, mimetype);
        this.configurations=new LinkedList<>();
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
