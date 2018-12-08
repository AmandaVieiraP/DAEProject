/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web;

import dtos.AdministratorDTO;
import ejbs.AdministratorBean;
import ejbs.TemplateBean;
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

    private Client client;
    private final String baseUri = "http://localhost:8080/ProjectDAE-war/webapi";

    private AdministratorDTO newAdministratorDTO;
    private UIComponent component;

    @ManagedProperty("#{userManager}")
    private UserManager userManager;

    public AdministratorManager() {
        // acrescencar posteriormente
        this.newAdministratorDTO = new AdministratorDTO();
        this.client = ClientBuilder.newClient();

    }

    @PostConstruct
    public void init() {
        HttpAuthenticationFeature feature = HttpAuthenticationFeature.basic(userManager.getUsername(), userManager.getPassword());
        client.register(feature);
        //logger.log(Level.WARNING, "init conc");
        //logger.log(Level.WARNING, userManager.getUsername());
    }

    public void createNewAdministrator() {

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

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

}
