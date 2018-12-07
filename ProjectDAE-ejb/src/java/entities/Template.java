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
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Iolanda
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(discriminatorType = DiscriminatorType.STRING)
@DiscriminatorValue("Template")
@NamedQueries({
    @NamedQuery(name = "getAllTemplates", query = "SELECT t FROM Template t"),
})
public class Template extends ConfigurationSuper implements Serializable {

    @NotNull
    @ManyToMany(mappedBy = "templates")
    private List<SoftwareModule> modules;

    public Template() {
        modules = new LinkedList<>();
    }

    public Template(int code, String description, Software software, Contract contract, String version) {
        super(code, description, software, contract, version);
        modules = new LinkedList<>();
    }

    public List<SoftwareModule> getModules() {
        return modules;
    }

    public void setModules(List<SoftwareModule> modules) {
        this.modules = modules;
    }

    public void addModule(SoftwareModule module) {
        if (module != null && !modules.contains(module)) {
            modules.add(module);
        }
    }

    public void removeModule(SoftwareModule module) {
        if (module != null && modules.contains(module)) {
            modules.remove(module);
        }
    }
}
