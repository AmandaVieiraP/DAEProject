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
    private int key;

    @NotNull
    private String value;
    
    @NotNull
    @ManyToOne
    @JoinColumn(name = "CONFIGURATION_CODE")
    private ConfigurationModule configurationModule;

    public License() {
    }

    public License(int key, String value, ConfigurationModule config) {
        this.key = key;
        this.value = value;
        this.configurationModule=config;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
