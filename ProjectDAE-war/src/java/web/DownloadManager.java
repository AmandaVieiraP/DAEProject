/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web;

import java.io.InputStream;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author Iolanda
 */
@ManagedBean(name = "downloadManager")
public class DownloadManager {

    private static final Logger logger = Logger.getLogger("web.downloadManager");
    private StreamedContent file;

    public DownloadManager() {
        
    }
    
    public StreamedContent downloadFile(String mimetype, String filename){
        try {
            InputStream stream = FacesContext.getCurrentInstance().getExternalContext().getResourceAsStream("/resources/files/"+filename);
            
            file = new DefaultStreamedContent(stream, mimetype, filename);
            
            return file;
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Can't download the file!",
                    logger);
            return null;
        }
    }

    public StreamedContent getFile() {
        return file;
    }
}
