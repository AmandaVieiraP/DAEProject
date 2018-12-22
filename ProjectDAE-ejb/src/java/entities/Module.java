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
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
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
@Table(name = "MODULES")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "MODULE_TYPE", discriminatorType = DiscriminatorType.STRING)
@NamedQueries({
    @NamedQuery(name = "getMaxModulesCode", query = "SELECT MAX(c.code) FROM Module c"),
})
public class Module implements Serializable {
    
    @Id
    private int code;

    @NotNull
    private String description;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "SOFTWARE_CODE")
    private Software software;

    @NotNull
    @ManyToMany(mappedBy = "modules", cascade = CascadeType.ALL)
    private List<Service> services;

    @NotNull
    private String version;

    public Module() {
        this.services = new LinkedList<>();
    }

    public Module(int code, String description, Software software, String version) {
        this.code = code;
        this.description = description;
        this.software = software;
        this.version = version;
        this.services = new LinkedList<>();
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Software getSoftware() {
        return software;
    }

    public void setSoftware(Software software) {
        this.software = software;
    }

    public List<Service> getServices() {
        return services;
    }

    public void setServices(List<Service> services) {
        this.services = services;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public void addService(Service service) {
        if (service != null && !services.contains(service)) {
            services.add(service);
        }
    }

    public void removeService(Service service) {
        if (service != null && services.contains(service)) {
            services.remove(service);
        }
    }

}
