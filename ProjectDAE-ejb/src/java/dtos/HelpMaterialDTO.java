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
@XmlRootElement(name = "HelpMaterial")
@XmlAccessorType(XmlAccessType.FIELD)
public class HelpMaterialDTO extends FileSuperDTO implements Serializable{

    public HelpMaterialDTO() {
    }

    public HelpMaterialDTO(String filename, String mimetype) {
        super(filename, mimetype);
    }
    
}
