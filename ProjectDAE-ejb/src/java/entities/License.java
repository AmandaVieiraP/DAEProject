/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Iolanda
 */
@Entity
@Table(name = "LICENSES")
public class License implements Serializable {

    @Id
    private int code;

    @NotNull
    private String licenceValue;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "CONFIGURATION_CODE")
    private ConfigurationModule configurationModule;

    public License() {
    }

    public License(int key, String value, ConfigurationModule config) {
        this.code = key;
        this.licenceValue = value;
        this.configurationModule = config;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getLicenceValue() {
        return licenceValue;
    }

    public void setLicenceValue(String licenceValue) {
        this.licenceValue = licenceValue;
    }

    public ConfigurationModule getConfigurationModule() {
        return configurationModule;
    }

    public void setConfigurationModule(ConfigurationModule configurationModule) {
        this.configurationModule = configurationModule;
    }
}
