/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web;

import dtos.ContractParameterDTO;
import dtos.ExtensionDTO;
import dtos.TemplateDTO;
import dtos.SoftwareDTO;
import dtos.SoftwareModuleDTO;
import java.io.Serializable;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    private int softwareCode;

    private Client client;
    private final String baseUri = "http://localhost:8080/ProjectDAE-war/webapi";

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
    
    public SoftwareDTO getCurrentSoftware(){
        SoftwareDTO softwareDTO = null;

        try {
            String code = String.valueOf(currentTemplate.getSoftwareCode());
            
            softwareDTO = client.target(baseUri).path("/softwares").path(code)
                    .request(MediaType.APPLICATION_XML)
                    .get(new GenericType<SoftwareDTO>() {
                    });
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage());
            //FacesExceptionHandler.handleException(e, "Unexpected error! Try again latter!", component, logger);
        }

        return softwareDTO;
        
    }
    
    public List<String> getCurrentSoftwareVersions(){
        List<String> versions=new LinkedList<>();
        
        try {
            String code = String.valueOf(currentTemplate.getSoftwareCode());
            
            Response serviceResponse = client.target(baseUri).path("/softwares/versions").path(code)
                    .request(MediaType.APPLICATION_JSON).get(Response.class);
            
            versions=computeJsonResponseToStringList(serviceResponse);
            
            
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage());
            //FacesExceptionHandler.handleException(e, "Unexpected error! Try again latter!", component, logger);
        }
        
        return versions;
    }
    
    public List<ExtensionDTO> getCurrentTemplateExtensions(){
        List<ExtensionDTO> extensions=new LinkedList<>();
        
        try {
            String code = String.valueOf(currentTemplate.getCode());
            
            extensions = client.target(baseUri).path("/extensions/templates").path(code)
                    .request(MediaType.APPLICATION_XML)
                    .get(new GenericType<List<ExtensionDTO>>() {
                    });
            
            
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage());
            //FacesExceptionHandler.handleException(e, "Unexpected error! Try again latter!", component, logger);
        }
        
        return extensions;
    }
    
    public List<ExtensionDTO> getCurrentSoftwareExtensions(){
        List<ExtensionDTO> extensions=new LinkedList<>();
        
        try {
            String code = String.valueOf(currentTemplate.getSoftwareCode());
            
            extensions = client.target(baseUri).path("/extensions/softwares").path(code)
                    .request(MediaType.APPLICATION_XML)
                    .get(new GenericType<List<ExtensionDTO>>() {
                    });
            
            
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage());
            //FacesExceptionHandler.handleException(e, "Unexpected error! Try again latter!", component, logger);
        }
        
        return extensions;
    }
    
    
    public List<SoftwareModuleDTO> getCurrentSoftwareModule(){
        List<SoftwareModuleDTO> softwareModules=new LinkedList<>();
        
        try {
            String code = String.valueOf(currentTemplate.getSoftwareCode());
            
            softwareModules = client.target(baseUri).path("/softwareModules/softwares").path(code)
                    .request(MediaType.APPLICATION_XML)
                    .get(new GenericType<List<SoftwareModuleDTO>>() {
                    });
            
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage());
            //FacesExceptionHandler.handleException(e, "Unexpected error! Try again latter!", component, logger);
        }
        
        return softwareModules;
    }
    
    public List<SoftwareModuleDTO> getCurrentTemplateModule(){
        List<SoftwareModuleDTO> softwareModules=new LinkedList<>();
        
        try {
            String code = String.valueOf(currentTemplate.getCode());
            
            softwareModules = client.target(baseUri).path("/softwareModules/templates").path(code)
                    .request(MediaType.APPLICATION_XML)
                    .get(new GenericType<List<SoftwareModuleDTO>>() {
                    });
            
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage());
            //FacesExceptionHandler.handleException(e, "Unexpected error! Try again latter!", component, logger);
        }
        
        return softwareModules;
    }
    
    public List<ContractParameterDTO> getCurrentContractParameters(){
        List<ContractParameterDTO> contractParameters=new LinkedList<>();
        
        try {
            String code = String.valueOf(currentTemplate.getContractCode());
            
            contractParameters = client.target(baseUri).path("/contract_parameters/contracts").path(code)
                    .request(MediaType.APPLICATION_XML)
                    .get(new GenericType<List<ContractParameterDTO>>() {
                    });
            
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage());
            //FacesExceptionHandler.handleException(e, "Unexpected error! Try again latter!", component, logger);
        }
        
        return contractParameters;
    }
    
    public List<String> getCurrentTemplateArtefacts(){
        List<String> artefacts=new LinkedList<>();
        
        try {
            String code = String.valueOf(currentTemplate.getCode());
            
            Response serviceResponse = client.target(baseUri).path("/configurations/artefacts").path(code)
                    .request(MediaType.APPLICATION_JSON).get(Response.class);
            
            artefacts=computeJsonResponseToStringList(serviceResponse);
            
            
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage());
            //FacesExceptionHandler.handleException(e, "Unexpected error! Try again latter!", component, logger);
        }
        
        return artefacts;
    }
    
    public List<String> getCurrentTemplateHelpMaterials(){
        List<String> helpMaterials=new LinkedList<>();
        
        try {
            String code = String.valueOf(currentTemplate.getCode());
            
            Response serviceResponse = client.target(baseUri).path("/configurations/helpMaterials").path(code)
                    .request(MediaType.APPLICATION_JSON).get(Response.class);
            
            helpMaterials=computeJsonResponseToStringList(serviceResponse);
            
            
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage());
            //FacesExceptionHandler.handleException(e, "Unexpected error! Try again latter!", component, logger);
        }
        
        return helpMaterials;
    }
    
    /**
    * Auxiliary Functions
    **/
    private List<String> computeJsonResponseToStringList(Response serviceResponse) {
        List<String> response = new LinkedList();
        try{
            String list = serviceResponse.readEntity(String.class);
            
            list=list.replace("[", "");
            
            list=list.replace("]","");
            
            String[]servicesList=list.split(",");
            
            response.addAll(Arrays.asList(servicesList));
            
            return response;
        }
        catch(Exception e){
            logger.log(Level.SEVERE, e.getMessage());
            return null;
        }
    }
    
    /**
    * Getters & Setters
    **/
    
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

    public int getSoftwareCode() {
        return softwareCode;
    }

    public void setSoftwareCode(int softwareCode) {
        this.softwareCode = softwareCode;
    }
}
