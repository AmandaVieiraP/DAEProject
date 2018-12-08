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
@Table(name = "CONTRACTPARAMETERS")
public class ContractParameter implements Serializable {

    @Id
    private String name;
    
    @NotNull
    private String description;
    
    @NotNull
    private String paramValue;
    
    /*@NotNull
    @ManyToMany
    @JoinTable(name = "CONTRACTPARAMETERS_CONTRACTS", joinColumns = @JoinColumn(name = "PARAMETER_NAME", referencedColumnName = "NAME"),
            inverseJoinColumns = @JoinColumn(name = "CONTRACT_CODE", referencedColumnName = "CODE"))
    private List<Contract> contracts;*/
    
    @NotNull
    @ManyToMany(mappedBy = "contractParameters")
    private List<Contract> contracts;
    
    public ContractParameter() {
        contracts=new LinkedList<>();
    }

    public ContractParameter(String name, String description, String value) {
        this.name = name;
        this.description = description;
        this.paramValue = value;
        contracts=new LinkedList<>();
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
    
}
