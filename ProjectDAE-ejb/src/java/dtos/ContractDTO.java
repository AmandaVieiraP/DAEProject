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
@XmlRootElement(name = "Contract")
@XmlAccessorType(XmlAccessType.FIELD)
public class ContractDTO implements Serializable{
    
    private int code;

    public ContractDTO() {
    }

    public ContractDTO(int code) {
        this.code = code; 
    }
    
    public void reset () {
        setCode(0);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
