/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web;

import com.sun.tools.ws.util.xml.XmlUtil;
import dtos.ArtefactDTO;
import dtos.ExtensionDTO;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import javax.enterprise.context.Dependent;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import org.primefaces.model.UploadedFile;
import util.URILookup;

/**
 *
 * @author Iolanda
 */
@ManagedBean(name = "uploadManager")
@Dependent
public class UploadManager {

    private UploadedFile file;
    private String path;

    public UploadManager() {
    }

    public void upload(boolean isArtefact) {
        if (file != null) {
            try {
                String filename = file.getFileName().substring(file.getFileName().lastIndexOf("\\") + 1);
                String mimetype = FacesContext.getCurrentInstance().getExternalContext().getMimeType(filename);
                
                InputStream in = file.getInputstream();
                
                //com este path ele coloca dentro de C:\Users\Iolanda\Documents\DAE\PL\DAEProject\dist\gfdeploy\ProjectDAE\ProjectDAE-war_war\resources\files
                //path=FacesContext.getCurrentInstance().getExternalContext().getRealPath("resources/files/");
                
                //com este path ele coloca dentro de \dist\gfdeploy\ProjectDAE\ProjectDAE-war_war\resources\files
                //FileOutputStream out = new FileOutputStream(path+"/"+filename);
                
                FileOutputStream out = new FileOutputStream("C:/Users/Iolanda/Documents/DAE/PL/DAEProject/ProjectDAE-war/web/resources/files/" + filename);
                
                byte[] b = new byte[1024];
                int readBytes = in.read(b);
                while (readBytes != -1) {
                    out.write(b, 0, readBytes);
                    readBytes = in.read(b);
                }
                in.close();
                out.close();
                FacesMessage message = new FacesMessage("File: " + file.getFileName()+"uploaded successfully!");
                FacesContext.getCurrentInstance().addMessage(null, message);
                
                if(isArtefact){
                    //addArtefactToConfiguration(filename, mimetype);
                }
                else{
                    
                }
                
            } catch (IOException e) {
                FacesMessage message = new FacesMessage("ERROR :: File: " + file.getFileName() + " not uploaded!");
                FacesContext.getCurrentInstance().addMessage(null, message);
            }
        }
    } 

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

}
