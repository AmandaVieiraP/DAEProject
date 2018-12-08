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
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Iolanda
 */
@Entity
@Table(name = "SOFTWARES")
public class Software implements Serializable {
    
    @Id
    private int code;
    
    @NotNull
    private String name;
    
    @NotNull
    private String description;
    
    @NotNull
    private List<String> versions;
    
    @NotNull
    @OneToMany(mappedBy = "software", cascade = CascadeType.REMOVE)
    private List<ConfigurationSuper> templates;
    
    @NotNull
    @OneToMany(mappedBy = "software", cascade = CascadeType.REMOVE)
    private List<Extension> extensions;
    
    @NotNull
    @OneToMany(mappedBy = "software", cascade = CascadeType.REMOVE)
    private List<Module> modules;

    public Software() {
        this.templates=new LinkedList<>();
        this.versions=new LinkedList<>();
        this.extensions=new LinkedList<>();
        this.modules=new LinkedList<>();
    }

    public Software(int code, String name, String description) {
        this.code = code;
        this.name = name;
        this.description = description;
        this.templates=new LinkedList<>();
        this.versions=new LinkedList<>();
        this.extensions=new LinkedList<>();
        this.modules=new LinkedList<>();
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

    public List<Extension> getExtensions() {
        return extensions;
    }

    public void setExtensions(List<Extension> extensions) {
        this.extensions = extensions;
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

    public List<ConfigurationSuper> getTemplates() {
        return templates;
    }

    public void setTemplates(List<ConfigurationSuper> templates) {
        this.templates = templates;
    }

    public List<Module> getModules() {
        return modules;
    }

    public void setModules(List<Module> modules) {
        this.modules = modules;
    }
    
    public void addConfiguration(ConfigurationSuper configuration) {

        if (configuration != null && !templates.contains(configuration)) {
            templates.add(configuration);
        }
    }

    public void removeConfiguration(ConfigurationSuper configuration) {

        if (configuration != null && templates.contains(configuration)) {
            templates.remove(configuration);
        }
    }
    
     public void addExtension(Extension extension) {
        if (extension != null && !extensions.contains(extension)) {
            extensions.add(extension);
        }
    }

    public void removeExtension(Extension extension) {
        if (extension != null && extensions.contains(extension)) {
            extensions.remove(extension);
        }
    }
    
    public void addVersion(String version) {
        if (version != null && !versions.contains(version)) {
            versions.add(version);
        }
    }
    
    public void removeModule(Module module) {
        if (module != null && modules.contains(module)) {
            modules.remove(module);
        }
    }
    
    public void addModule(Module module) {
        if (module != null && !modules.contains(module)) {
            modules.add(module);
        }
    }
    
}
