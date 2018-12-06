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
@Table(name = "MODULES")
public class Module implements Serializable {
    @Id
    private int code;
    
    @NotNull
    private String description;
    
    @NotNull
    @ManyToMany
    @JoinTable(name = "MODULES_TEMPLATES", joinColumns = @JoinColumn(name = "MODULE_CODE", referencedColumnName = "CODE"),
            inverseJoinColumns = @JoinColumn(name = "TEMPLATE_CODE", referencedColumnName = "CODE"))
    private List<Template> templates;

    public Module() {
        this.templates=new LinkedList<>();
    }
    
    public Module(int code, String description) {
        this.code = code;
        this.description = description;
        this.templates=new LinkedList<>();
    }

    public Module(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
    
    public void addTemplate(Template templateToAdd) {

        if (templateToAdd != null && !templates.contains(templateToAdd)) {
            templates.add(templateToAdd);
        }
    }

    public void removeTemplate(Template templateToRemove) {

        if (templateToRemove != null && templates.contains(templateToRemove)) {
            templates.remove(templateToRemove);
        }
    }
}
