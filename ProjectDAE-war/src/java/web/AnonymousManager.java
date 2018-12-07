/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web;

import dtos.ContractDTO;
import dtos.TemplateDTO;
import ejbs.TemplateBean;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author Iolanda
 */
@ManagedBean(name = "anonymousManager")
@SessionScoped
public class AnonymousManager implements Serializable {

    private static final Logger logger = Logger.getLogger("web.AdministratorManager");

    private TemplateDTO currentTemplate;

    private int contractCode;

    private Client client;
    private final String baseUri = "http://localhost:8080/ProjectDAE-war/webapi";

    @EJB
    private TemplateBean templateBean;

    /**
     * Creates a new instance of AnonymousManager
     */
    public AnonymousManager() {
        this.client = ClientBuilder.newClient();
    }

    public List<TemplateDTO> getAllTemplates() {
        //logger.log(Level.WARNING, "init REST");
        List<TemplateDTO> returnedTemplates = null;

        try {
            returnedTemplates = client.target(baseUri).path("/templates/all")
                    .request(MediaType.APPLICATION_XML)
                    .get(new GenericType<List<TemplateDTO>>() {
                    });
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage());
            //FacesExceptionHandler.handleException(e, "Unexpected error! Try again latter!", component, logger);
        }

        return returnedTemplates;
    }

    public ContractDTO getContractByCode() {
        ContractDTO contract = null;

        try {
            String code = String.valueOf(contractCode);

            contract = client.target(baseUri).path("/contracts").path(code)
                    .request(MediaType.APPLICATION_XML).get(new GenericType<ContractDTO>() {
            });
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage());
            //FacesExceptionHandler.handleException(e, "Unexpected error! Try again latter!", component, logger);
        }
        return contract;
    }
    
    public List<String> getAllCurrentTemplateServices(){
        List<String> services=new LinkedList<>();
        
        try {
            String code = String.valueOf(currentTemplate.getCode());
            
            Response serviceResponse = client.target(baseUri).path("/templates/services").path(code).
                    request(MediaType.APPLICATION_JSON).get(Response.class);
            String list = serviceResponse.readEntity(String.class);
            
            list=list.replace("[", "");
            
            list=list.replace("]","");
            
            String[]servicesList=list.split(",");
            
            for(String s:servicesList){
                services.add(s);
            }
            
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage());
            //FacesExceptionHandler.handleException(e, "Unexpected error! Try again latter!", component, logger);
        }
        
        return services;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public TemplateDTO getCurrentTemplate() {
        return currentTemplate;
    }

    public void setCurrentTemplate(TemplateDTO currentTemplate) {
        this.currentTemplate = currentTemplate;
    }

    public int getContractCode() {
        return contractCode;
    }

    public void setContractCode(int contractCode) {
        this.contractCode = contractCode;
    }
}
