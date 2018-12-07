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
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Iolanda
 */
@Entity
@Table(name = "CONTRACTS")
public class Contract implements Serializable {

    @Id
    private int code;

    @NotNull
    @ManyToMany(mappedBy = "contracts")
    private List<ContractParameter> contractParameters;

    @NotNull
    @OneToMany(mappedBy = "contract", cascade = CascadeType.REMOVE)
    private List<ConfigurationSuper> templates;

    public Contract() {
        this.templates = new LinkedList<>();
        this.contractParameters = new LinkedList<>();
    }

    public Contract(int code) {
        this.code = code;
        this.contractParameters = new LinkedList<>();
        this.templates = new LinkedList<>();
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<ContractParameter> getContractParameters() {
        return contractParameters;
    }

    public List<ConfigurationSuper> getTemplates() {
        return templates;
    }

    public void setTemplates(List<ConfigurationSuper> templates) {
        this.templates = templates;
    }

    public void addConfiguration(ConfigurationSuper templateToAdd) {

        if (templateToAdd != null && !templates.contains(templateToAdd)) {
            templates.add(templateToAdd);
        }
    }

    public void removeConfiguration(ConfigurationSuper templateToRemove) {

        if (templateToRemove != null && templates.contains(templateToRemove)) {
            templates.remove(templateToRemove);
        }
    }

    public void addParameter(ContractParameter contractParameter) {

        if (contractParameter != null && !contractParameters.contains(contractParameter)) {
            contractParameters.add(contractParameter);
        }
    }

    public void removeParameter(ContractParameter contractParameter) {

        if (contractParameter != null && contractParameters.contains(contractParameter)) {
            contractParameters.remove(contractParameter);
        }
    }
}
