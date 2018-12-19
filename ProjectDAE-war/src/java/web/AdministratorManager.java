/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web;

import dtos.AdministratorDTO;
import dtos.ClientDTO;
import dtos.ContractDTO;
import dtos.ContractParameterDTO;
import dtos.ExtensionDTO;
import dtos.SoftwareDTO;
import dtos.SoftwareModuleDTO;
import dtos.TemplateDTO;
import ejbs.AdministratorBean;
import java.io.Serializable;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UIParameter;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.apache.jasper.tagplugins.jstl.ForEach;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;

/**
 *
 * @author Amanda
 */
//@Named(value = "administratorManager")
@ManagedBean(name = "administratorManager")
@SessionScoped
public class AdministratorManager implements Serializable {

    private static final Logger logger = Logger.getLogger("web.AdministratorManager");

    private AdministratorDTO newAdministratorDTO;
    private ClientDTO newClientDTO;
    
    private TemplateDTO newTemplateDTO;
    private List<SoftwareModuleDTO> softwareModules;
    private List<String> selectedSoftwareModules;
    private List<ExtensionDTO> softwareExtensions;
    private List<String> selectedSoftwareExtensions;
    
    private UIComponent component;
    
    private List<TemplateDTO> filteredTemplates;

    private Client client;

    private final String baseUri = "http://localhost:8080/ProjectDAE-war/webapi";

    private AdministratorDTO currentAdminLogged;
    private AdministratorDTO currentAdmin;
    private TemplateDTO currentTemplate;

    private ClientDTO currentClient;

    @ManagedProperty("#{userManager}")
    private UserManager userManager;

    public AdministratorManager() {
        // acrescencar posteriormente
        this.newAdministratorDTO = new AdministratorDTO();
        this.newClientDTO = new ClientDTO();
        this.newTemplateDTO = new TemplateDTO();
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
            client.target(baseUri)
                    .path("/administrators/create")
                    .request(MediaType.APPLICATION_XML).post(Entity.xml(newAdministratorDTO));

            clearNewAdministrator();
        } /*catch (EntityExistsException e) {
            FacesExceptionHandler.handleException(e, e.getMessage(), component, logger);
            System.err.println(e.getMessage());
            return null;
        } */ catch (Exception e) {
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
    
    public String createNewTemplateSoftware() {

        try {
            
            System.out.println("selected extensions: " + this.selectedSoftwareExtensions.toString());
            

            SoftwareDTO soft = new SoftwareDTO();
            String code = String.valueOf(newTemplateDTO.getSoftwareCode());
            
            soft = client.target(baseUri).path("/softwares").path(code)
                    .request(MediaType.APPLICATION_XML)
                    .get(new GenericType<SoftwareDTO>() {
                    });

           newTemplateDTO.setSoftwareName(soft.getName());
            
           client.target(baseUri)
                    .path("/templates/create")
                    .request(MediaType.APPLICATION_XML).post(Entity.xml(newTemplateDTO));
           
           
           
           if(!selectedSoftwareExtensions.isEmpty())
            {
                for (String extension: selectedSoftwareExtensions) {
                    
                    System.out.println("enviado extensionId: "+extension + " templateId: " + newTemplateDTO.getCode());

                    client.target(baseUri)
                    .path("/templates").path(String.valueOf(newTemplateDTO.getCode())).path("/extension").path(extension)
                    .request(MediaType.APPLICATION_XML);
                    
                }

                
            }
            if(!selectedSoftwareModules.isEmpty())
            {
                for (String module: selectedSoftwareModules) {
                    
                    System.out.println("enviado moduleId: "+module + " templateId: " + newTemplateDTO.getCode());

                    client.target(baseUri)
                    .path("/templates/").path(String.valueOf(newTemplateDTO.getCode())).path("/module/").path(module)
                    .request(MediaType.APPLICATION_XML);
                    
                }
                
            }
           
           
            
            clearNewTemplate();
        } /*catch (EntityExistsException e) {
            FacesExceptionHandler.handleException(e, e.getMessage(), component, logger);
            System.err.println(e.getMessage());
            return null;
        } */ catch (Exception e) {
            System.err.println(e.getMessage());
            //logger.warning("Unexpected error. Try again latter!");
            // logger.warning("Problem creating student in method creatStudent.");   
            //return "admin_students_create?faces-redirect=true";
            return null;
        }
        return "admin_index?faces-redirect=true"; // é redirecionado para esta página  
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
        } */ catch (Exception e) {
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
    
    public List<TemplateDTO> getAllTemplates() {
        List<TemplateDTO> returnedTemplates = null;

        try {
            returnedTemplates = client.target(baseUri)
                    .path("/templates/all")
                    .request(MediaType.APPLICATION_XML)
                    .get(new GenericType<List<TemplateDTO>>() {
                    });
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            logger.warning("Problem getting all templates with REST");
            return null;
        }
        return returnedTemplates;
    }

    public List<ClientDTO> getAllClients() {
        List<ClientDTO> returnedClients = null;

        try {
            returnedClients = client.target(baseUri)
                    .path("/clients/all")
                    .request(MediaType.APPLICATION_XML)
                    .get(new GenericType<List<ClientDTO>>() {
                    });
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            logger.warning("Problem getting all administrators with REST");
            return null;
        }
        return returnedClients;
    }
    
    
    public List<SoftwareDTO> getAllSoftwares() {
        List<SoftwareDTO> returnedSoftwares = null;

        try {
            returnedSoftwares = client.target(baseUri)
                    .path("/softwares/all")
                    .request(MediaType.APPLICATION_XML)
                    .get(new GenericType<List<SoftwareDTO>>() {
                    });
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            logger.warning("Problem getting all softwares with REST");
            return null;
        }
        return returnedSoftwares;
    }
    
    public List<ContractDTO> getAllContracts() {
        List<ContractDTO> returnedContracts = null;

        try {
            returnedContracts = client.target(baseUri)
                    .path("/contracts/all")
                    .request(MediaType.APPLICATION_XML)
                    .get(new GenericType<List<ContractDTO>>() {
                    });
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            logger.warning("Problem getting all contracts with REST");
            return null;
        }
        return returnedContracts;
    }
    //allSoftwaresModulesFromASoftware
    public void allModulesFromASoftware() {
        List<SoftwareModuleDTO> returnedSoftwareModules= null;

        try {
            String code = String.valueOf(newTemplateDTO.getSoftwareCode());
            if(!code.isEmpty())
            {
               returnedSoftwareModules = client.target(baseUri)
                    .path("/softwareModules/softwares").path(code)
                    .request(MediaType.APPLICATION_XML)
                    .get(new GenericType<List<SoftwareModuleDTO>>() {
                    }); 
            }
            
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            logger.warning("Problem getting all software modules with REST");
            //return null;
        }
        
        this.softwareModules = returnedSoftwareModules;
       // return returnedSoftwareModules;
       
    }
    
    public void allExtentionsFromASoftware() {
        List<ExtensionDTO> returnedSoftwareExtensions= null;

        try {
            System.out.println("software code: " + newTemplateDTO.getSoftwareCode());
            String code = String.valueOf(newTemplateDTO.getSoftwareCode());
            if(!code.isEmpty())
            {
               returnedSoftwareExtensions = client.target(baseUri)
                    .path("/extensions/softwares").path(code)
                    .request(MediaType.APPLICATION_XML)
                    .get(new GenericType<List<ExtensionDTO>>() {
                    }); 
            }
            
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            logger.warning("Problem getting all software extensions with REST");
            //return null;
        }
        
        this.softwareExtensions = returnedSoftwareExtensions;
       // return returnedSoftwareModules;
       
    }
    
    
    //todo ADICIONARRRRR metod é exatamente igualk ao do anonymousamanger
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
    //todo ADICIONARRRRRR
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
    //ADICIONARRRRRR
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
    
    //adicionarrrrrrr
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
    
    
    
    //adicionar
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
    
    
    public String removeClient(ActionEvent event) {
        try {
            UIParameter param = (UIParameter) event.getComponent().findComponent("deleteClientUsername");
            String username = param.getValue().toString();

            System.out.println("????????????? " + username);

            client.target(baseUri).path("/clients")
                    .path(username)
                    .request(MediaType.APPLICATION_XML)
                    .delete();

        } catch (Exception e) {
            logger.warning("Problem removing the client");
            return null;
        }
        return "admin_index?faces-redirect=true";
    }

    public String removeAdministrator(ActionEvent event) {
        try {
            UIParameter param = (UIParameter) event.getComponent().findComponent("deleteAdminUsername");
            String username = param.getValue().toString();

            client.target(baseUri).path("/administrators")
                    .path(username)
                    .request(MediaType.APPLICATION_XML)
                    .delete();

        } catch (Exception e) {
            logger.warning("Problem removing the client");
            return null;
        }
        return "admin_index?faces-redirect=true";
    }

    public String updateAdministrator() {
        try {
            client.target(baseUri)
                    .path("/administrators/update")
                    .request(MediaType.APPLICATION_XML).put(Entity.xml(currentAdmin));

        } catch (Exception e) {
            logger.warning("Problem updating the administrator");
            return "update_admin";
        }
        // return "index?faces-redirect=true";
        return "administrators_list?faces-redirect=true";
    }

    public String updateClient() {
        try {
            client.target(baseUri)
                    .path("/clients/update")
                    .request(MediaType.APPLICATION_XML).put(Entity.xml(currentClient));

        } catch (Exception e) {
            logger.warning("Problem updating the client");
            return "update_client";
        }
        // return "index?faces-redirect=true";
        return "clients_list?faces-redirect=true";
    }

    public void clearNewTemplate() {
        //todo limnpar melhor
        this.selectedSoftwareExtensions = null;
        this.selectedSoftwareModules = null;
        this.softwareExtensions = null;
        this.softwareModules = null;
        newTemplateDTO = new TemplateDTO();
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

    public TemplateDTO getNewTemplateDTO() {
        return newTemplateDTO;
    }
    
    public ClientDTO getNewClientDTO() {
        return newClientDTO;
    }

    public void setNewTemplateDTO(TemplateDTO newTemplateDTO) {
        this.newTemplateDTO = newTemplateDTO;
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

    public AdministratorDTO getCurrentAdmin() {
        return currentAdmin;
    }

    public void setCurrentAdmin(AdministratorDTO currentAdmin) {
        this.currentAdmin = currentAdmin;
    }

    public ClientDTO getCurrentClient() {
        return currentClient;
    }

    public void setCurrentClient(ClientDTO currentClient) {
        this.currentClient = currentClient;
    }
    public List<TemplateDTO> getFilteredTemplates() {
        return filteredTemplates;
    }

    public void setFilteredTemplates(List<TemplateDTO> filteredTemplates) {
        this.filteredTemplates = filteredTemplates;
    }
    
    public TemplateDTO getCurrentTemplate() {
        return currentTemplate;
    }

    public void setCurrentTemplate(TemplateDTO currentTemplate) {
        this.currentTemplate = currentTemplate;
    }

    public List<SoftwareModuleDTO> getSoftwareModules() {
        return softwareModules;
    }

    public void setSoftwareModules(List<SoftwareModuleDTO> softwareModules) {
        this.softwareModules = softwareModules;
    }

    public List<String> getSelectedSoftwareModules() {
        return selectedSoftwareModules;
    }

    public void setSelectedSoftwareModules(List<String> selectedSoftwareModules) {
        this.selectedSoftwareModules = selectedSoftwareModules;
    }

    public List<String> getSelectedSoftwareExtensions() {
        return selectedSoftwareExtensions;
    }

    public List<ExtensionDTO> getSoftwareExtensions() {
        return softwareExtensions;
    }

    public void setSelectedSoftwareExtensions(List<String> selectedSoftwareExtensions) {
        this.selectedSoftwareExtensions = selectedSoftwareExtensions;
    }

    public void setSoftwareExtensions(List<ExtensionDTO> softwareExtensions) {
        this.softwareExtensions = softwareExtensions;
    }
    
    
    
    
}
