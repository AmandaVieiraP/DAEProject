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
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Iolanda
 */
@Entity
@Table(name = "SERVICES")
public class Service implements Serializable {

    @Id
    private int code;

    @NotNull
    private String name;

    @NotNull
    private String description;

    @NotNull
    private String version;

    @NotNull
    @ManyToMany
    @JoinTable(name = "SERVICES_MODULES", joinColumns = @JoinColumn(name = "SERVICE_CODE", referencedColumnName = "CODE"),
            inverseJoinColumns = @JoinColumn(name = "MODULES_CODE", referencedColumnName = "CODE"))
    private List<Module> modules;

    public Service() {
        this.modules = new LinkedList<>();
    }

    public Service(int code, String name, String description, String version) {
        this.code = code;
        this.name = name;
        this.description = description;
        this.version = version;
        this.modules = new LinkedList<>();
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

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public List<Module> getModules() {
        return modules;
    }

    public void setModules(List<Module> modules) {
        this.modules = modules;
    }

    public void addModule(Module module) {
        if (module != null && !modules.contains(module)) {
            modules.add(module);
        }
    }

    public void removeModule(Module module) {
        if (module != null && modules.contains(module)) {
            modules.remove(module);
        }
    }

}
