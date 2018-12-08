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
@XmlRootElement(name = "SoftwareModule")
@XmlAccessorType(XmlAccessType.FIELD)
public class SoftwareModuleDTO extends ModuleDTO implements Serializable{

    public SoftwareModuleDTO() {
    }

    public SoftwareModuleDTO(int code, String description, int softwareCode, String softwareName) {
        super(code, description, softwareCode, softwareName);
    }
    
    @Override
    public void reset(){
        super.reset();
    }
}
