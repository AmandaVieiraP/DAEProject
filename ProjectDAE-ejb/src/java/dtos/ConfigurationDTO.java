/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtos;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Iolanda
 */
@XmlRootElement(name = "Configuration")
@XmlAccessorType(XmlAccessType.FIELD)
public class ConfigurationDTO extends ConfigurationSuperDTO implements Serializable {

    private String clientUsername;
    private String dbServerIp;
    private String applicationServerIp;

    public ConfigurationDTO() {
    }

    public ConfigurationDTO(int code, String description, int softwareCode, String softwareName, int contractCode, String version, String username, String dbServerIp, String applicationServerIp) {
        super(code, description, softwareCode, softwareName, contractCode, version);
        this.clientUsername = username;
        this.dbServerIp = dbServerIp;
        this.applicationServerIp = applicationServerIp;
    }

    @Override
    public void reset() {
        super.reset();
        setClientUsername(null);
    }

    public String getClientUsername() {
        return clientUsername;
    }

    public void setClientUsername(String clientUsername) {
        this.clientUsername = clientUsername;
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
}
