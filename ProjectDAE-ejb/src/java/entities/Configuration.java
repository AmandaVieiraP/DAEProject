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
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Iolanda
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(discriminatorType = DiscriminatorType.STRING)
@DiscriminatorValue("Configuration")
@NamedQueries({
    @NamedQuery(name = "getAllConfigurations", query = "SELECT c FROM Configuration c"),})
public class Configuration extends ConfigurationSuper implements Serializable {

    private String dbServerIp;

    private String applicationServerIp;

    @NotNull
    @ManyToMany(mappedBy = "configurations", cascade = CascadeType.ALL)
    private List<ConfigurationModule> modules;

    @NotNull
    @ManyToMany
    @JoinTable(name = "CONFIGURATIONS_PARAMETERS", joinColumns = @JoinColumn(name = "CONFIGURATION_CODE", referencedColumnName = "CODE"),
            inverseJoinColumns = @JoinColumn(name = "PARAMETER_NAME", referencedColumnName = "NAME"))
    private List<Parameter> configurationParameters;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "CLIENT_USERNAME")
    private Client client;

    
    @OneToMany(mappedBy = "configuration", cascade = CascadeType.ALL)
    private List<Question> questions;

    public Configuration() {
        this.modules = new LinkedList<>();
        this.configurationParameters = new LinkedList<>();
        this.questions = new LinkedList<>();
    }

    public Configuration(int code, String description, Software software, Contract contract, String version, Client client, String dbServerIp, String appServerIp) {
        super(code, description, software, contract, version);

        this.modules = new LinkedList<>();
        this.configurationParameters = new LinkedList<>();
        this.client = client;
        this.dbServerIp = dbServerIp;
        this.applicationServerIp = appServerIp;
        this.questions = new LinkedList<>();
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public String getDbServerIp() {
        return dbServerIp;
    }

    public void setDbServerIp(String dbServerIp) {
        this.dbServerIp = dbServerIp;
    }

    public String getApplicationServerIp() {
        return applicationServerIp;
    }

    public void setApplicationServerIp(String applicationServerIp) {
        this.applicationServerIp = applicationServerIp;
    }

    public List<ConfigurationModule> getModules() {
        return modules;
    }

    public void setModules(List<ConfigurationModule> modules) {
        this.modules = modules;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public List<Parameter> getConfigurationParameters() {
        return configurationParameters;
    }

    public void setConfigurationParameters(List<Parameter> configurationParameters) {
        this.configurationParameters = configurationParameters;
    }

    public void addParameters(Parameter parameter) {
        if (parameter != null && !configurationParameters.contains(parameter)) {
            configurationParameters.add(parameter);
        }
    }

    public void addQuestions(Question question) {
        if (question != null && !questions.contains(question)) {
            questions.add(question);
        }
    }

    public void removeQuestions(Question question) {
        if (question != null && questions.contains(question)) {
            questions.remove(question);
        }
    }

    public void removeParameter(Parameter parameters) {
        if (parameters != null && configurationParameters.contains(parameters)) {
            configurationParameters.remove(parameters);
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

}
