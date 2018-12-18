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
public class FileSuperDTO implements Serializable{
    private String filename;
    private String mimetype;

    public FileSuperDTO() {
    }

    public FileSuperDTO(String filename, String mimetype) {
        this.filename = filename;
        this.mimetype = mimetype;
    }
    
    public void reset(){
        setFilename(null);
        setMimetype(null);
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getMimetype() {
        return mimetype;
    }

    public void setMimetype(String mimetype) {
        this.mimetype = mimetype;
    }
}
