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
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Iolanda
 */
@Entity
@Table(name = "PARAMETERS")
public class Parameter implements Serializable {

    @Id
    private String name;

    @NotNull
    private String description;

    @NotNull
    private String paramValue;

    @NotNull
    @ManyToMany(mappedBy = "contractParameters")
    private List<Contract> contracts;

    @NotNull
    @ManyToMany(mappedBy = "moduleParameters")
    private List<ConfigurationModule> modules;

    @NotNull
    @ManyToMany(mappedBy = "configurationParameters")
    private List<Configuration> configurations;

    public Parameter() {
        this.contracts = new LinkedList<>();
        this.modules = new LinkedList<>();
        this.configurations = new LinkedList<>();
    }

    public Parameter(String name, String description, String value) {
        this.name = name;
        this.description = description;
        this.paramValue = value;
        this.contracts = new LinkedList<>();
        this.modules = new LinkedList<>();
        this.configurations = new LinkedList<>();
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

    public String getParamValue() {
        return paramValue;
    }

    public void setParamValue(String paramValue) {
        this.paramValue = paramValue;
    }

    public List<Contract> getContracts() {
        return contracts;
    }

    public void setContracts(List<Contract> contracts) {
        this.contracts = contracts;
    }

    public List<ConfigurationModule> getModules() {
        return modules;
    }

    public void setModules(List<ConfigurationModule> modules) {
        this.modules = modules;
    }

    public List<Configuration> getConfigurations() {
        return configurations;
    }

    public void setConfigurations(List<Configuration> configurations) {
        this.configurations = configurations;
    }

    public void addContract(Contract contract) {

        if (contract != null && !contracts.contains(contract)) {
            contracts.add(contract);
        }
    }

    public void removeContract(Contract contract) {

        if (contract != null && contracts.contains(contract)) {
            contracts.remove(contract);
        }
    }

    public void addModule(ConfigurationModule module) {

        if (module != null && !modules.contains(module)) {
            modules.add(module);
        }
    }

    public void removeModule(ConfigurationModule module) {

        if (module != null && modules.contains(module)) {
            modules.remove(module);
        }
    }

    public void addConfigurations(Configuration conf) {

        if (conf != null && !configurations.contains(conf)) {
            configurations.add(conf);
        }
    }

    public void removeConfigurations(Configuration config) {

        if (config != null && configurations.contains(config)) {
            configurations.remove(config);
        }
    }

}
