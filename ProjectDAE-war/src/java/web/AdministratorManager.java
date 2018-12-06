/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web;


import dtos.AdministratorDTO;
import ejbs.AdministratorBean;
import exceptions.EntityExistsException;
import java.io.Serializable;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
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
    
    @EJB 
    private AdministratorBean administratorBean;
    
    private AdministratorDTO newAdministratorDTO;
    private UIComponent component;
    
    private Client client;
    
    private final String baseUri = "http://localhost:8080/ProjectDAE-war/webapi";
    
    
    @ManagedProperty("#{userManager}")
    private UserManager userManager; 

    public AdministratorManager() {
        // acrescencar posteriormente
        this.newAdministratorDTO = new AdministratorDTO();
        client = ClientBuilder.newClient();
        
    }
    
    @PostConstruct
    public void init() {
        HttpAuthenticationFeature feature = HttpAuthenticationFeature.basic(userManager.getUsername(), userManager.getPassword());
        client.register(feature);
    }
    
    public String createNewAdministrator() {
        
        /* 
         client.target(baseUri)
                  .path("/students/update")
                  .request(MediaType.APPLICATION_XML).put(Entity.xml(currentStudent));
         // GenericEntity<List<SubjectDTO>> list = new GenericEntity<List<SubjectDTO>>(subjectsStudentIsEnrolled) {};
          client.target(baseUri)
                  .path("/students/updateSubjects")
                  .path(currentStudent.getUsername())
                  .request(MediaType.APPLICATION_XML).put(Entity.xml(new GenericEntity<List<SubjectDTO>>(subjectsStudentIsEnrolled) {})); 
       
        */
             
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
    
    public void clearNewAdministrator() {
        newAdministratorDTO = new AdministratorDTO();
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

    
    
    public UserManager getUserManager() {
        return userManager;
    }

    public void setUserManager(UserManager userManager) {
        this.userManager = userManager;
    }
    
    
                
    
}
