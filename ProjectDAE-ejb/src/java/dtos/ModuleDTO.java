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
import javax.xml.bind.annotation.XmlSeeAlso;

/**
 *
 * @author Iolanda
 */
@XmlRootElement(name = "Module")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlSeeAlso(SoftwareModuleDTO.class)
public class ModuleDTO implements Serializable {

    private int code;
    private String description;
    private int softwareCode;
    private String softwareName;
    private String version;

    public ModuleDTO() {
    }

    public ModuleDTO(int code, String description, int softwareCode, String softwareName, String version) {
        this.code = code;
        this.description = description;
        this.softwareCode = softwareCode;
        this.softwareName = softwareName;
        this.version = version;
    }

    public void reset() {
        setCode(0);
        setDescription(null);
        setSoftwareCode(0);
        setSoftwareName(null);
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

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

}
