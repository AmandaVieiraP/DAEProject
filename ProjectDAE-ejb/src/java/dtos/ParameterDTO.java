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
@XmlRootElement(name = "ContractParameters")
@XmlAccessorType(XmlAccessType.FIELD)
public class ParameterDTO implements Serializable{
    
    private String name;
    private String description;
    private String paramValue;

    public ParameterDTO(String name, String description, String paramValue) {
        this.name = name;
        this.description = description;
        this.paramValue = paramValue;
    }

    public ParameterDTO() {
    }
    
    public void reset(){
        this.name=null;
        this.description=null;
        this.paramValue=null;
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

    public String getParamValue() {
        return paramValue;
    }

    public void setParamValue(String paramValue) {
        this.paramValue = paramValue;
    }

}
