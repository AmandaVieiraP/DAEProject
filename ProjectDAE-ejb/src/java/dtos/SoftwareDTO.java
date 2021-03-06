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
@XmlRootElement(name = "Software")
@XmlAccessorType(XmlAccessType.FIELD)
public class SoftwareDTO implements Serializable {

    private int code;
    private String name;
    private String description;

    public SoftwareDTO() {
    }

    public SoftwareDTO(int code, String name, String description) {
        this.code = code;
        this.name = name;
        this.description = description;
    }

    public void reset() {
        setCode(0);
        setName(null);
        setDescription(null);
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

}
