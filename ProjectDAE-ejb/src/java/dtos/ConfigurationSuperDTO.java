/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtos;

import java.io.Serializable;


/**
 *
 * @author Iolanda
 */

public class ConfigurationSuperDTO implements Serializable{
    private int code;
    private String description;
    private int softwareCode;
    private String softwareName;
    private int contractCode;
    private String version;

    public ConfigurationSuperDTO() {
    }

    public ConfigurationSuperDTO(int code, String description, int softwareCode, String softwareName, int contractCode, String version) {
        this.code = code;
        this.description = description;
        this.softwareCode = softwareCode;
        this.softwareName = softwareName;
        this.contractCode = contractCode;
        this.version = version;
    }
    
    public void reset() {
        setCode(0);
        setDescription(null);
        setSoftwareCode(0);
        setSoftwareName(null);
        setContractCode(0);
        setVersion(null);
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

    public int getSoftwareCode() {
        return softwareCode;
    }

    public void setSoftwareCode(int softwareCode) {
        this.softwareCode = softwareCode;
    }

    public String getSoftwareName() {
        return softwareName;
    }

    public void setSoftwareName(String softwareName) {
        this.softwareName = softwareName;
    }

    public int getContractCode() {
        return contractCode;
    }

    public void setContractCode(int contractCode) {
        this.contractCode = contractCode;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
    
    
    
}
