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
@XmlRootElement(name = "ConfigurationModule")
@XmlAccessorType(XmlAccessType.FIELD)
public class ConfigurationModuleDTO extends ModuleDTO implements Serializable{
    private String dbServerIp;
    private String applicationServerIp;

    public ConfigurationModuleDTO() {

    }

    public ConfigurationModuleDTO(String dbServerIp, String applicationServerIp, int code, String description, int softwareCode, String softwareName, String version) {
        super(code, description, softwareCode, softwareName, version);
        this.dbServerIp = dbServerIp;
        this.applicationServerIp = applicationServerIp;
    }
    
    @Override
    public void reset(){
        super.reset();
        setDbServerIp(null);
        setApplicationServerIp(null);
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
