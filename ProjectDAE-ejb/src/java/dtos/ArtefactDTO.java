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
@XmlRootElement(name = "Artefact")
@XmlAccessorType(XmlAccessType.FIELD)
public class ArtefactDTO extends FileSuperDTO implements Serializable{

    public ArtefactDTO() {
    }

    public ArtefactDTO(String filename, String mimetype) {
        super(filename, mimetype);
    }
}
