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
@XmlRootElement(name = "Extension")
@XmlAccessorType(XmlAccessType.FIELD)
public class ExtensionDTO implements Serializable {

    private int code;
    private String name;
    private String description;
    private int softwareCode;
    private String softwareName;

    public ExtensionDTO() {
    }

    public ExtensionDTO(int code, String name, String description, int softwareCode, String softwareName) {
        this.code = code;
        this.name = name;
        this.description = description;
        this.softwareCode = softwareCode;
        this.softwareName = softwareName;
    }

    public void reset() {
        setCode(0);
        setName(null);
        setDescription(null);
        setSoftwareCode(0);
        setSoftwareName(null);

    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
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

}
