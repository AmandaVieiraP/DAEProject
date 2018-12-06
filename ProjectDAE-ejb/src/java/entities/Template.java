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
@Table(name = "TEMPLATES")
@NamedQueries({
    @NamedQuery(name = "getAllTemplates", query = "SELECT t FROM Template t"),
})
public class Template implements Serializable {
    
    public static enum STATE {
        Active,Inactive,Suspended
    }
    
    @Id
    private int code;
    
    @NotNull
    private String description;
    
    @NotNull
    private STATE state;
    
    @NotNull
    @ManyToOne
    @JoinColumn(name = "SOFTWARE_CODE")
    private Software software;
    
    @NotNull
    @ManyToOne
    @JoinColumn(name = "CONTRACT_CODE")
    private Contract contract;
    
    @NotNull
    private String version;
    
    @NotNull
    @ManyToMany(mappedBy = "templates")
    private List<Module> modules;
    
    //entidade???
    @NotNull
    private List<String> services;
    
    //entidade???
    @NotNull
    private List<String> licenses;
    
    //entidade???
    @NotNull
    private List<String> parameters;
    
    @NotNull
    private String artefactsRepository;
    

    public Template() {
        this.modules=new LinkedList<>();
        this.services=new LinkedList<>();
        this.licenses=new LinkedList<>();
        this.parameters=new LinkedList<>();
    }

    public Template(int code, String description, STATE state, Software software, String version, Contract contract, String repository) {
        this.code = code;
        this.description = description;
        this.state = state;
        this.software = software;
        this.version = version;
        this.contract=contract;
        this.artefactsRepository=repository;
        this.modules=new LinkedList<>();
        this.services=new LinkedList<>();
        this.licenses=new LinkedList<>();
        this.parameters=new LinkedList<>();
        
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

    public STATE getState() {
        return state;
    }

    public void setState(STATE state) {
        this.state = state;
    }

    public Software getSoftware() {
        return software;
    }

    public void setSoftware(Software software) {
        this.software = software;
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

    public List<String> getServices() {
        return services;
    }

    public void setServices(List<String> services) {
        this.services = services;
    }

    public List<String> getLicenses() {
        return licenses;
    }

    public void setLicenses(List<String> licenses) {
        this.licenses = licenses;
    }

    public List<String> getParameters() {
        return parameters;
    }

    public void setParameters(List<String> parameters) {
        this.parameters = parameters;
    }

    public Contract getContract() {
        return contract;
    }

    public void setContract(Contract contract) {
        this.contract = contract;
    }

    public String getArtefactsRepository() {
        return artefactsRepository;
    }

    public void setArtefactsRepository(String artefactsRepository) {
        this.artefactsRepository = artefactsRepository;
    }
    
    public void addModule(Module moduleToAdd) {

        if (moduleToAdd != null && !modules.contains(moduleToAdd)) {
            modules.add(moduleToAdd);
        }
    }

    public void removeModule(Module moduleToRemove) {

        if (moduleToRemove != null && modules.contains(moduleToRemove)) {
            modules.remove(moduleToRemove);
        }
    }
}
