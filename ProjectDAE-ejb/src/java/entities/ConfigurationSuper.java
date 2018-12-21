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
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
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
@Table(name = "CONFIGURATIONS")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "CONFIG_TYPE", discriminatorType = DiscriminatorType.STRING)
public class ConfigurationSuper implements Serializable {

    @Id
    private int code;

    @NotNull
    private String description;

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
    @ManyToMany(mappedBy = "configurations")
    private List<Extension> extensions;

    @NotNull
    @ManyToMany(mappedBy = "configurations")
    private List<Artefact> artefactsRepository;

    @NotNull
    @ManyToMany(mappedBy = "configurations")
    private List<HelpMaterial> helpMaterials;

    public ConfigurationSuper() {
        this.extensions = new LinkedList<>();
        this.artefactsRepository = new LinkedList<>();
        this.helpMaterials = new LinkedList<>();
    }

    public ConfigurationSuper(int code, String description, Software software, Contract contract, String version) {
        this.code = code;
        this.description = description;
        this.software = software;
        this.contract = contract;
        this.version = version;
        this.extensions = new LinkedList<>();
        this.artefactsRepository = new LinkedList<>();
        this.helpMaterials = new LinkedList<>();
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

    public Software getSoftware() {
        return software;
    }

    public void setSoftware(Software software) {
        this.software = software;
    }

    public Contract getContract() {
        return contract;
    }

    public void setContract(Contract contract) {
        this.contract = contract;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public List<Artefact> getArtefactsRepository() {
        return artefactsRepository;
    }

    public void setArtefactsRepository(List<Artefact> artefactsRepository) {
        this.artefactsRepository = artefactsRepository;
    }

    public List<HelpMaterial> getHelpMaterials() {
        return helpMaterials;
    }

    public void setHelpMaterials(List<HelpMaterial> helpMaterials) {
        this.helpMaterials = helpMaterials;
    }

    public List<Extension> getExtensions() {
        return extensions;
    }

    public void setExtensions(List<Extension> extensions) {
        this.extensions = extensions;
    }

    public void addArtefact(Artefact artefact) {
        if (artefact != null && !artefactsRepository.contains(artefact)) {
            artefactsRepository.add(artefact);
        }
    }

    public void removeArtefact(Artefact artefact) {
        if (artefact != null && artefactsRepository.contains(artefact)) {
            artefactsRepository.remove(artefact);
        }
    }

    public void addHelpMaterial(HelpMaterial material) {
        if (material != null && !helpMaterials.contains(material)) {
            helpMaterials.add(material);
        }
    }

    public void removeHelpMaterial(HelpMaterial material) {
        if (material != null && helpMaterials.contains(material)) {
            helpMaterials.remove(material);
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

}
