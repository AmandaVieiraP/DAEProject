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
@DiscriminatorValue("SoftwareModule")
@NamedQueries({
    @NamedQuery(name = "getAllSoftwareModulesBySoftware", query = "SELECT m FROM SoftwareModule m WHERE m.software.code=?1")
    ,
    @NamedQuery(name = "getAllSoftwareModules", query = "SELECT m FROM SoftwareModule m")
})
public class SoftwareModule extends Module implements Serializable {

    @NotNull
    @ManyToMany
    @JoinTable(name = "TEMPLATES_MODULES", joinColumns = @JoinColumn(name = "MODULE_CODE", referencedColumnName = "CODE"),
            inverseJoinColumns = @JoinColumn(name = "TEMPLATE_CODE", referencedColumnName = "CODE"))
    private List<Template> templates;

    public SoftwareModule() {
        this.templates = new LinkedList<>();
    }

    public SoftwareModule(int code, String description, Software software, String version) {
        super(code, description, software, version);
        this.templates = new LinkedList<>();
    }

    public List<Template> getTemplates() {
        return templates;
    }

    public void setTemplates(List<Template> templates) {
        this.templates = templates;
    }

    public void addTemplate(Template template) {
        if (template != null && !templates.contains(template)) {
            templates.add(template);
        }
    }

    public void removeTemplate(Template template) {
        if (template != null && templates.contains(template)) {
            templates.remove(template);
        }
    }
}
