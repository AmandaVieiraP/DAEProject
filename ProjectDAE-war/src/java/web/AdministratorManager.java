/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web;

import dtos.TemplateDTO;
import ejbs.AdministratorBean;
import ejbs.TemplateBean;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;

/**
 *
 * @author Amanda
 */
//@Named(value = "administratorManager")
@ManagedBean(name = "administratorManager")
@SessionScoped
public class AdministratorManager implements Serializable {
    // também tem propriedades de controlador 

    /**
     * Creates a new instance of AdministratorManager
     */
    private static final Logger logger = Logger.getLogger("web.AdministratorManager");

    @EJB
    private AdministratorBean administratorBean;

    @EJB
    private TemplateBean templateBean;

    @ManagedProperty("#{userManager}")
    private UserManager userManager;

    private Client client;
    private final String baseUri = "http://localhost:8080/ProjectDAE-war/webapi";

    public AdministratorManager() {
        this.client = ClientBuilder.newClient();
    }

    @PostConstruct
    public void init() {
        HttpAuthenticationFeature feature = HttpAuthenticationFeature.basic(userManager.getUsername(), userManager.getPassword());
        client.register(feature);
        logger.log(Level.WARNING, "init conc");
        logger.log(Level.WARNING, userManager.getUsername());
    }
    
    
    public List<TemplateDTO> getAllTemplatesREST() {
        logger.log(Level.WARNING, "init REST");
        List<TemplateDTO> returnedTemplates=null;
        
        try{
            returnedTemplates = client.target(baseUri).path("/templates/all")
                                    .request(MediaType.APPLICATION_XML)
                                    .get(new GenericType<List<TemplateDTO>>() {
                                    });
        } catch(Exception e){
            logger.log(Level.SEVERE, e.getMessage());
            //FacesExceptionHandler.handleException(e, "Unexpected error! Try again latter!", component, logger);
        }
        
        return returnedTemplates;
    }

    public UserManager getUserManager() {
        return userManager;
    }

    public void setUserManager(UserManager userManager) {
        this.userManager = userManager;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

}
