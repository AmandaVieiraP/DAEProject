/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web;


import dtos.AdministratorDTO;
import dtos.ClientDTO;
import ejbs.AdministratorBean;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;

/**
 *
 * @author Amanda
 */
//@Named(value = "administratorManager")
@ManagedBean(name="administratorManager")
@SessionScoped
public class AdministratorManager implements Serializable {
    // também tem propriedades de controlador 
    
    /**
     * Creates a new instance of AdministratorManager
     */
    private static final Logger logger = Logger.getLogger("web.AdministratorManager");
    
   
    
    private AdministratorDTO newAdministratorDTO;
    private ClientDTO newClientDTO;
    private UIComponent component;
    
    private Client client;
    
    private final String baseUri = "http://localhost:8080/ProjectDAE-war/webapi";
    
    private AdministratorDTO currentAdminLogged; 
    
    private List<AdministratorDTO> administratorsFiltered;
    
    
    @ManagedProperty("#{userManager}")
    private UserManager userManager; 

    public AdministratorManager() {
        // acrescencar posteriormente
        this.newAdministratorDTO = new AdministratorDTO();
        this.newClientDTO = new ClientDTO();
        this.currentAdminLogged = new AdministratorDTO();
        client = ClientBuilder.newClient();
        
    }
    
    @PostConstruct
    public void init() {
        HttpAuthenticationFeature feature = HttpAuthenticationFeature.basic(userManager.getUsername(), userManager.getPassword());
        client.register(feature);
        this.getAdministratorLogged();
    }
    
    public String createNewAdministrator() {
        
         try {
           // administratorBean.create(newAdministratorDTO.getUsername(), newAdministratorDTO.getPassword(), newAdministratorDTO.getName(), newAdministratorDTO.getEmail(), newAdministratorDTO.getJobRole());
           client.target(baseUri)
                   .path("/administrators/create")
                   .request(MediaType.APPLICATION_XML).post(Entity.xml(newAdministratorDTO));
           
           
           clearNewAdministrator(); 
        } /*catch (EntityExistsException e) {
            FacesExceptionHandler.handleException(e, e.getMessage(), component, logger);
            System.err.println(e.getMessage());
            return null;
        } */  catch (Exception e) {
            System.err.println(e.getMessage());
            //logger.warning("Unexpected error. Try again latter!");
            FacesExceptionHandler.handleException(e, "Unexpected error. Try again latter!", logger);
            FacesExceptionHandler.handleException(e, "Unexpected error. Try again latter!", component, logger);
            // logger.warning("Problem creating student in method creatStudent.");   
            //return "admin_students_create?faces-redirect=true";
            return null;
        }
        return "admin_index?faces-redirect=true"; // é redirecionado para está página  
    }
    
    
    public String createNewClient() {
        
         try {
           client.target(baseUri)
                   .path("/clients/create")
                   .request(MediaType.APPLICATION_XML).post(Entity.xml(newClientDTO));
           
           
           clearNewClient(); 
        } /*catch (EntityExistsException e) {
            FacesExceptionHandler.handleException(e, e.getMessage(), component, logger);
            System.err.println(e.getMessage());
            return null;
        } */  catch (Exception e) {
            System.err.println(e.getMessage());
            //logger.warning("Unexpected error. Try again latter!");
            FacesExceptionHandler.handleException(e, "Unexpected error. Try again latter!", logger);
            FacesExceptionHandler.handleException(e, "Unexpected error. Try again latter!", component, logger);
            // logger.warning("Problem creating student in method creatStudent.");   
            //return "admin_students_create?faces-redirect=true";
            return null;
        }
        return "admin_index?faces-redirect=true"; // é redirecionado para está página  
    }
    
    public void getAdministratorLogged() {

        AdministratorDTO admin;
        try {
            admin = client.target(baseUri)
                    .path("/administrators")
                    .path(userManager.getUsername())
                    .request(MediaType.APPLICATION_XML)
                    .get(AdministratorDTO.class);
            this.setCurrentAdminLogged(admin);
            
        } catch (Exception e) {
            System.err.println(e.getMessage());
            //logger.warning("Unexpected error. Try again latter!");
            FacesExceptionHandler.handleException(e, "Unexpected error. Try again latter!", logger);
            FacesExceptionHandler.handleException(e, "Unexpected error. Try again latter!", component, logger);
            // logger.warning("Problem creating student in method creatStudent.");   
            //return "admin_students_create?faces-redirect=true";
        }   
    }
    
    public List<AdministratorDTO> getAllAdministrators() {
        List<AdministratorDTO> returnedAdministrators = null;
        
        try {
            returnedAdministrators = client.target(baseUri)
                    .path("/administrators/all")
                    .request(MediaType.APPLICATION_XML)
                    .get(new GenericType<List<AdministratorDTO>>() {
                    });
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            logger.warning("Problem getting all administrators with REST");
            return null;
        }
        return returnedAdministrators;
    }
    
    public void clearNewAdministrator() {
        newAdministratorDTO = new AdministratorDTO();
    }

    public void clearNewClient() {
        newClientDTO = new ClientDTO();
    }
  
    public AdministratorDTO getNewAdministratorDTO() {
        return newAdministratorDTO;
    }

    public void setNewAdministratorDTO(AdministratorDTO newAdministratorDTO) {
        this.newAdministratorDTO = newAdministratorDTO;
    }

    public UIComponent getComponent() {
        return component;
    }

    public void setComponent(UIComponent component) {
        this.component = component;
    }

    public ClientDTO getNewClientDTO() {
        return newClientDTO;
    }

    public void setNewClientDTO(ClientDTO newClientDTO) {
        this.newClientDTO = newClientDTO;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

  
    
    public UserManager getUserManager() {
        return userManager;
    }

    public void setUserManager(UserManager userManager) {
        this.userManager = userManager;
    }

    public AdministratorDTO getCurrentAdminLogged() {
        return currentAdminLogged;
    }

    public void setCurrentAdminLogged(AdministratorDTO currentAdminLogged) {
        this.currentAdminLogged = currentAdminLogged;
    }

    public List<AdministratorDTO> getAdministratorsFiltered() {
        return administratorsFiltered;
    }

    public void setAdministratorsFiltered(List<AdministratorDTO> administratorsFiltered) {
        this.administratorsFiltered = administratorsFiltered;
    }
    
    


    
    
                
    
}
