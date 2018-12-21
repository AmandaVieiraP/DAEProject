/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web;

import dtos.AdministratorDTO;
import dtos.ArtefactDTO;
import dtos.ClientDTO;
import dtos.ConfigurationDTO;
import dtos.ConfigurationModuleDTO;
import dtos.ContractDTO;
import dtos.ContractParameterDTO;
import dtos.ExtensionDTO;
import dtos.HelpMaterialDTO;
import dtos.LicenseDTO;
import dtos.ServiceDTO;
import dtos.SoftwareDTO;
import dtos.SoftwareModuleDTO;
import dtos.TemplateDTO;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.enterprise.context.Dependent;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UIParameter;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author Amanda
 */
//@Named(value = "administratorManager")
@ManagedBean(name = "administratorManager")
@SessionScoped
@Dependent
public class AdministratorManager implements Serializable {

    private static final Logger logger = Logger.getLogger("web.AdministratorManager");

    private AdministratorDTO newAdministratorDTO;
    private ClientDTO newClientDTO;

    private TemplateDTO newTemplateDTO;
    private List<SoftwareModuleDTO> softwareModules;
    private List<String> selectedSoftwareModules;
    private List<ExtensionDTO> softwareExtensions;
    private List<String> selectedSoftwareExtensions;
    private ExtensionDTO selectedExtension;
    private int moduleCode;

    private ConfigurationDTO newConfigurationDTO;

    private UIComponent component;

    private List<TemplateDTO> filteredTemplates;

    private Client client;

    private final String baseUri = "http://localhost:8080/ProjectDAE-war/webapi";

    private AdministratorDTO currentAdminLogged;
    private AdministratorDTO currentAdmin;
    private TemplateDTO currentTemplate;
    private ClientDTO currentClient;
    private ConfigurationDTO currentConfiguration;
    private int code;
    
    private UploadedFile file;
    private String path;

    @ManagedProperty("#{userManager}")
    private UserManager userManager;

    public AdministratorManager() {
        // acrescencar posteriormente
        this.newAdministratorDTO = new AdministratorDTO();
        this.newClientDTO = new ClientDTO();
        this.newTemplateDTO = new TemplateDTO();
        this.currentAdminLogged = new AdministratorDTO();
        this.newConfigurationDTO = new ConfigurationDTO();
        client = ClientBuilder.newClient();

    }

    @PostConstruct
    public void init() {
        HttpAuthenticationFeature feature = HttpAuthenticationFeature.basic(userManager.getUsername(), userManager.getPassword());
        client.register(feature);
        this.getAdministratorLogged();
    }

    //**** Métodos de Criar ****//
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

            if (!selectedSoftwareExtensions.isEmpty()) {
                for (String extension : selectedSoftwareExtensions) {

                    System.out.println("enviado extensionId: " + extension + " templateId: " + newTemplateDTO.getCode());

                    client.target(baseUri)
                            .path("/templates").path(String.valueOf(newTemplateDTO.getCode())).path("/extension").path(extension)
                            .request(MediaType.APPLICATION_XML);

                }

            }
            if (!selectedSoftwareModules.isEmpty()) {
                for (String module : selectedSoftwareModules) {

                    System.out.println("enviado moduleId: " + module + " templateId: " + newTemplateDTO.getCode());

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

    public String createNewConfiguration() {
        try {
            this.newConfigurationDTO.setClientUsername(this.currentClient.getUsername());

            client.target(baseUri)
                    .path("/configurations/create")
                    .request(MediaType.APPLICATION_XML).post(Entity.xml(newConfigurationDTO));

            // clearConfigurationDTO();
        } catch (Exception e) {
            logger.warning(e.getMessage());
            return null;
        }

        return "configurations_associations?faces-redirect=true";
    }

    //**** Métodos de Listar ****//
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

    public List<ExtensionDTO> getAllExtensions() {
        List<ExtensionDTO> extensionsDTO = new LinkedList<>();

        try {
            extensionsDTO = client.target(baseUri).path("/extensions/all").request(MediaType.APPLICATION_XML)
                    .get(new GenericType<List<ExtensionDTO>>() {
                    });
        } catch (Exception ex) {

            logger.warning("Problem getting all contracts with REST");
            return null;
        }
        return extensionsDTO;
    }

    //allSoftwaresModulesFromASoftware
    public void allModulesFromASoftware() {
        List<SoftwareModuleDTO> returnedSoftwareModules = null;

        try {
            String code = String.valueOf(newTemplateDTO.getSoftwareCode());
            if (!code.isEmpty()) {
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
        List<ExtensionDTO> returnedSoftwareExtensions = null;

        try {
            System.out.println("software code: " + newTemplateDTO.getSoftwareCode());
            String code = String.valueOf(newTemplateDTO.getSoftwareCode());
            if (!code.isEmpty()) {
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
    //Juntar com Iolanda
    public List<ContractParameterDTO> getCurrentContractParameters() {
        return getCurrentContractParametersForAllConfiguration(String.valueOf(currentTemplate.getContractCode()));
        /*List<ContractParameterDTO> contractParameters = new LinkedList<>();

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

        return contractParameters;*/
    }

    public List<ContractParameterDTO> getClientConfigurationsContractParameters() {
        return getCurrentContractParametersForAllConfiguration(String.valueOf(currentConfiguration.getContractCode()));
        /*List<ContractParameterDTO> contractParameters = new LinkedList<>();

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

        return contractParameters;*/
    }

    public List<ContractParameterDTO> getCurrentContractParametersForAllConfiguration(String code) {
        List<ContractParameterDTO> contractParameters = new LinkedList<>();

        try {

            contractParameters = client.target(baseUri).path("/contract_parameters/contracts").path(code)
                    .request(MediaType.APPLICATION_XML)
                    .get(new GenericType<List<ContractParameterDTO>>() {
                    });

        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage());

        }

        return contractParameters;
    }

    public List<ContractParameterDTO> getCurrentConfigurationParameters() {
        List<ContractParameterDTO> contractParameters = new LinkedList<>();

        try {

            String code = String.valueOf(currentConfiguration.getCode());

            contractParameters = client.target(baseUri).path("/contract_parameters/configurations").path(code)
                    .request(MediaType.APPLICATION_XML)
                    .get(new GenericType<List<ContractParameterDTO>>() {
                    });

        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage());

        }

        return contractParameters;
    }

    public List<ContractParameterDTO> getCurrentConfigurationModuleParameters() {
        List<ContractParameterDTO> contractParameters = new LinkedList<>();

        try {

            String code = String.valueOf(this.moduleCode);

            contractParameters = client.target(baseUri).path("/contract_parameters/modules").path(code)
                    .request(MediaType.APPLICATION_XML)
                    .get(new GenericType<List<ContractParameterDTO>>() {
                    });

        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage());

        }

        return contractParameters;
    }

    //Para usar o metodo comum---------------------------------------
    public SoftwareDTO getCurrentSoftware() {
        return getSoftwareDTOByConfigurationCode(String.valueOf(currentTemplate.getSoftwareCode()));
        /*SoftwareDTO softwareDTO = null;

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

        return softwareDTO;*/

    }

    public SoftwareDTO getClientConfigurationSoftware() {

        return getSoftwareDTOByConfigurationCode(String.valueOf(currentConfiguration.getSoftwareCode()));

    }

    private SoftwareDTO getSoftwareDTOByConfigurationCode(String code) {
        try {
            return client.target(baseUri).path("/softwares").path(code)
                    .request(MediaType.APPLICATION_XML)
                    .get(new GenericType<SoftwareDTO>() {
                    });
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage());
            //FacesExceptionHandler.handleException(e, "Unexpected error! Try again latter!", component, logger);
        }
        return null;
    }

    public List<String> getCurrentSoftwareVersions() {
        return getCurrentSoftwareVersionsAllConfigurations(String.valueOf(currentTemplate.getSoftwareCode()));
        /*List<String> versions = new LinkedList<>();

        try {
            String code = String.valueOf(currentTemplate.getSoftwareCode());

            Response serviceResponse = client.target(baseUri).path("/softwares/versions").path(code)
                    .request(MediaType.APPLICATION_JSON).get(Response.class);

            versions = computeJsonResponseToStringList(serviceResponse);

        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage());
            //FacesExceptionHandler.handleException(e, "Unexpected error! Try again latter!", component, logger);
        }

        return versions;*/
    }

    public List<String> getClientConfigurationSoftwareVersions() {
        return getCurrentSoftwareVersionsAllConfigurations(String.valueOf(currentConfiguration.getSoftwareCode()));
    }

    private List<String> getCurrentSoftwareVersionsAllConfigurations(String code) {
        List<String> versions = new LinkedList<>();

        try {
            Response serviceResponse = client.target(baseUri).path("/softwares/versions").path(code)
                    .request(MediaType.APPLICATION_JSON).get(Response.class);

            versions = computeJsonResponseToStringList(serviceResponse);

        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage());
            //FacesExceptionHandler.handleException(e, "Unexpected error! Try again latter!", component, logger);
        }

        return versions;
    }

    //adicionarrrrrrr
    public List<ExtensionDTO> getCurrentSoftwareExtensions() {
        List<ExtensionDTO> extensions = new LinkedList<>();

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

    public List<SoftwareModuleDTO> getCurrentSoftwareModule() {
        List<SoftwareModuleDTO> softwareModules = new LinkedList<>();

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

    public List<ExtensionDTO> getCurrentTemplateExtensions() {
        return getExtensionsDTOByConfigurationCode(String.valueOf(currentTemplate.getCode()));
        /*List<ExtensionDTO> extensions = new LinkedList<>();

        try {
            String code = String.valueOf(currentTemplate.getCode());

            extensions = client.target(baseUri).path("/extensions/configurations").path(code)
                    .request(MediaType.APPLICATION_XML)
                    .get(new GenericType<List<ExtensionDTO>>() {
                    });

        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage());
            //FacesExceptionHandler.handleException(e, "Unexpected error! Try again latter!", component, logger);
        }

        return extensions;*/
    }

    public List<ExtensionDTO> getCurrentConfigurationsExtensions() {
        return getExtensionsDTOByConfigurationCode(String.valueOf(currentConfiguration.getCode()));
    }

    private List<ExtensionDTO> getExtensionsDTOByConfigurationCode(String code) {
        List<ExtensionDTO> extensions = new LinkedList<>();

        try {
            extensions = client.target(baseUri).path("/extensions/configurations").path(code)
                    .request(MediaType.APPLICATION_XML)
                    .get(new GenericType<List<ExtensionDTO>>() {
                    });

        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage());
            //FacesExceptionHandler.handleException(e, "Unexpected error! Try again latter!", component, logger);
        }

        return extensions;
    }

    public List<SoftwareModuleDTO> getCurrentTemplateModule() {
        List<SoftwareModuleDTO> softwareModules = new LinkedList<>();

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

    public List<ConfigurationModuleDTO> getCurrentConfigurationsModules() {
        List<ConfigurationModuleDTO> configurationModulesDTO = new LinkedList<>();

        try {
            String code = String.valueOf(currentConfiguration.getCode());

            configurationModulesDTO = client.target(baseUri).path("/configurationModules").path(code)
                    .request(MediaType.APPLICATION_XML)
                    .get(new GenericType<List<ConfigurationModuleDTO>>() {
                    });

        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage());
        }

        return configurationModulesDTO;
    }

    public List<String> getCurrentTemplateArtefacts() {
        List<String> artefacts = new LinkedList<>();

        try {
            String code = String.valueOf(currentTemplate.getCode());

            Response serviceResponse = client.target(baseUri).path("/configurations/artefacts").path(code)
                    .request(MediaType.APPLICATION_JSON).get(Response.class);

            artefacts = computeJsonResponseToStringList(serviceResponse);

        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage());
            //FacesExceptionHandler.handleException(e, "Unexpected error! Try again latter!", component, logger);
        }

        return artefacts;
    }

    public List<ArtefactDTO> getCurrentArtefacts() {
        List<ArtefactDTO> artefacts = new LinkedList<>();

        try {

            String code = String.valueOf(currentConfiguration.getCode());

            artefacts = client.target(baseUri).path("/artefacts").path(code)
                    .request(MediaType.APPLICATION_XML)
                    .get(new GenericType<List<ArtefactDTO>>() {
                    });

        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage());
            //FacesExceptionHandler.handleException(e, "Unexpected error! Try again latter!", component, logger);
        }

        return artefacts;
    }

    public List<String> getCurrentTemplateHelpMaterials() {
        List<String> helpMaterials = new LinkedList<>();

        try {
            String code = String.valueOf(currentTemplate.getCode());

            Response serviceResponse = client.target(baseUri).path("/configurations/helpMaterials").path(code)
                    .request(MediaType.APPLICATION_JSON).get(Response.class);

            helpMaterials = computeJsonResponseToStringList(serviceResponse);

        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage());
            //FacesExceptionHandler.handleException(e, "Unexpected error! Try again latter!", component, logger);
        }

        return helpMaterials;
    }

    public List<HelpMaterialDTO> getCurrentHelpMaterials() {

        List<HelpMaterialDTO> helpMaterials = new LinkedList<>();

        try {
            String code = String.valueOf(currentConfiguration.getCode());

            helpMaterials = client.target(baseUri).path("/helpMaterials").path(code)
                    .request(MediaType.APPLICATION_XML)
                    .get(new GenericType<List<HelpMaterialDTO>>() {
                    });

        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage());
            //FacesExceptionHandler.handleException(e, "Unexpected error! Try again latter!", component, logger);
        }

        return helpMaterials;
    }

    public List<ConfigurationDTO> getClientConfigurations() {

        List<ConfigurationDTO> configurationsDTO = new LinkedList<>();

        try {
            String username = this.currentClient.getUsername();

            configurationsDTO = client.target(baseUri).path("/configurations").path(username)
                    .request(MediaType.APPLICATION_XML)
                    .get(new GenericType<List<ConfigurationDTO>>() {
                    });

        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage());
        }

        return configurationsDTO;
    }

    public List<LicenseDTO> getCurrentConfigurationsModuleLicenses() {
        List<LicenseDTO> licensesDTO = new LinkedList<>();

        try {
            String code = String.valueOf(this.moduleCode);

            licensesDTO = client.target(baseUri).path("/licenses").path(code)
                    .request(MediaType.APPLICATION_XML)
                    .get(new GenericType<List<LicenseDTO>>() {
                    });

        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage());
        }

        return licensesDTO;
    }

    public List<ServiceDTO> getCurrentConfigurationModuleServices() {
        List<ServiceDTO> servicesDTO = new LinkedList<>();

        try {
            String code = String.valueOf(moduleCode);

            servicesDTO = client.target(baseUri).path("/services").path(code)
                    .request(MediaType.APPLICATION_XML)
                    .get(new GenericType<List<ServiceDTO>>() {
                    });

        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage());
            //FacesExceptionHandler.handleException(e, "Unexpected error! Try again latter!", component, logger);
        }

        return servicesDTO;
    }

    //adicionar
    private List<String> computeJsonResponseToStringList(Response serviceResponse) {
        List<String> response = new LinkedList();
        try {
            String list = serviceResponse.readEntity(String.class);

            list = list.replace("[", "");

            list = list.replace("]", "");

            String[] servicesList = list.split(",");

            response.addAll(Arrays.asList(servicesList));

            return response;
        } catch (Exception e) {
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

    //******************* Update Methods
    public void associateExtensionsToConfiguration() {
        try {

            String codeC = String.valueOf(this.newConfigurationDTO.getCode());

            selectedExtension = new ExtensionDTO(this.code, null, null, 0, null, null);

            client.target(baseUri)
                    .path("/configurations/associateExtensions").path(codeC)
                    .request(MediaType.APPLICATION_XML).put(Entity.xml(this.selectedExtension));

        } catch (Exception e) {
            logger.warning(e.getMessage());
        }
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
                    addArtefactToConfiguration(filename, mimetype);
                }
                else{
                    addHelpMaterialToConfiguration(filename, mimetype);
                }
                
            } catch (IOException e) {
                FacesMessage message = new FacesMessage("ERROR :: File: " + file.getFileName() + " not uploaded!");
                FacesContext.getCurrentInstance().addMessage(null, message);
            }
        }
    } 
    
    public void addArtefactToConfiguration(String filename, String mimetype){
        try {
            
            String codeC = String.valueOf(this.newConfigurationDTO.getCode());

            ArtefactDTO artefactDTO = new ArtefactDTO(filename, mimetype);

            client.target(baseUri)
                    .path("/configurations/associateArtefacts").path(codeC)
                    .request(MediaType.APPLICATION_XML).put(Entity.xml(artefactDTO));

        } catch (Exception e) {
            logger.warning(e.getMessage());
        }
    }
    
    public void addHelpMaterialToConfiguration(String filename, String mimetype){
        try {
            
            String codeC = String.valueOf(this.newConfigurationDTO.getCode());

            HelpMaterialDTO helpMaterialDTO = new HelpMaterialDTO(filename, mimetype);

            client.target(baseUri)
                    .path("/configurations/associateHelpMaterials").path(codeC)
                    .request(MediaType.APPLICATION_XML).put(Entity.xml(helpMaterialDTO));

        } catch (Exception e) {
            logger.warning(e.getMessage());
        }
    }
    

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
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

    public void clearConfigurationDTO() {
        newConfigurationDTO = new ConfigurationDTO();
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

    public ConfigurationDTO getCurrentConfiguration() {
        return currentConfiguration;
    }

    public void setCurrentConfiguration(ConfigurationDTO currentConfiguration) {
        this.currentConfiguration = currentConfiguration;
    }

    public int getModuleCode() {
        return moduleCode;
    }

    public void setModuleCode(int moduleCode) {
        this.moduleCode = moduleCode;
    }

    public ConfigurationDTO getNewConfigurationDTO() {
        return newConfigurationDTO;
    }

    public void setNewConfigurationDTO(ConfigurationDTO newConfigurationDTO) {
        this.newConfigurationDTO = newConfigurationDTO;
    }

    public ExtensionDTO getSelectedExtension() {
        return selectedExtension;
    }

    public void setSelectedExtension(ExtensionDTO selectedExtension) {
        this.selectedExtension = selectedExtension;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

}
