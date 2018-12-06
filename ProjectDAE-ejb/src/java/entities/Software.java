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
    @OneToMany(mappedBy = "software", cascade = CascadeType.REMOVE)
    private List<Template> templates;

    public Software() {
        this.templates=new LinkedList<>();
    }

    public Software(int code, String name) {
        this.code = code;
        this.name = name;
        this.templates=new LinkedList<>();
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

    public List<Template> getTemplates() {
        return templates;
    }

    public void setTemplates(List<Template> templates) {
        this.templates = templates;
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
