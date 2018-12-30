/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web;

import dtos.AdministratorDTO;
import dtos.AnswerDTO;
import dtos.ArtefactDTO;
import dtos.ClientDTO;
import dtos.ConfigurationDTO;
import dtos.ConfigurationModuleDTO;
import dtos.ContractDTO;
import dtos.ParameterDTO;
import dtos.ExtensionDTO;
import dtos.HelpMaterialDTO;
import dtos.LicenseDTO;
import dtos.QuestionDTO;
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
    private ConfigurationModuleDTO selectedConfigurationModule;
    private SoftwareModuleDTO selectedTemplateConfigurationModule;

    private int moduleCode;

    private ConfigurationDTO newConfigurationDTO;
    private ParameterDTO newParameterDTO;
    private ExtensionDTO newExtensionDTO;
    private ConfigurationModuleDTO newConfigurationModuleDTO;
    private LicenseDTO newLinceseDTO;
    private ServiceDTO newServiceDTO;

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
    private String paramName;
    private String usernameClientSelectedToClone;
    private int codeConfigurationSelectedToClone;
    private ClientDTO currentClientLogged;

    private List<QuestionDTO> currentQuestions;
    private QuestionDTO currentQuestion;
    private QuestionDTO newQuestionDTO;

    private List<AnswerDTO> currentAnswers;
    private AnswerDTO currentAnswer;
    private AnswerDTO newAnswerDTO;

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
        this.currentClientLogged = new ClientDTO();
        this.newConfigurationDTO = new ConfigurationDTO();
        this.newParameterDTO = new ParameterDTO();
        this.newExtensionDTO = new ExtensionDTO();
        this.newConfigurationModuleDTO = new ConfigurationModuleDTO();
        this.newLinceseDTO = new LicenseDTO();
        this.newServiceDTO = new ServiceDTO();
        this.newQuestionDTO = new QuestionDTO();
        this.currentQuestion = new QuestionDTO();
        this.currentAnswer = new AnswerDTO();
        this.newAnswerDTO = new AnswerDTO();
        client = ClientBuilder.newClient();

    }

    @PostConstruct
    public void init() {
        HttpAuthenticationFeature feature = HttpAuthenticationFeature.basic(userManager.getUsername(), userManager.getPassword());
        client.register(feature);

        if (userManager.isAdmin()) {
            this.getAdministratorLogged();
        } else {
            this.getClientLogged();
        }

    }

    //**** Métodos de Criar ****//
    public String createNewAdministrator() {

        try {
            client.target(baseUri)
                    .path("/administrators/create")
                    .request(MediaType.APPLICATION_XML).post(Entity.xml(newAdministratorDTO));

            clearNewAdministrator();
        } catch (Exception e) {
            System.err.println(e.getMessage());
            FacesExceptionHandler.handleException(e, "Unexpected error. Try again latter!", logger);
            FacesExceptionHandler.handleException(e, "Unexpected error. Try again latter!", component, logger);
            return null;
        }
        return "administrators_list?faces-redirect=true"; // é redirecionado para está página  
    }

    public String createNewTemplateSoftware() {
        try {

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

            clearNewTemplate();
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return null;
        }
        return "templates_list?faces-redirect=true"; // é redirecionado para esta página  
    }

    public String createNewClient() {

        try {
            client.target(baseUri)
                    .path("/clients/create")
                    .request(MediaType.APPLICATION_XML).post(Entity.xml(newClientDTO));

            clearNewClient();
        } catch (Exception e) {
            System.err.println(e.getMessage());
            FacesExceptionHandler.handleException(e, "Unexpected error. Try again latter!", logger);
            FacesExceptionHandler.handleException(e, "Unexpected error. Try again latter!", component, logger);
            return null;
        }
        return "clients_list?faces-redirect=true"; // é redirecionado para está página  
    }

    public void createNewParameterAndAssociateToConfig() {
        createNewParameterAndAssociateToConfigGeral(String.valueOf(this.currentConfiguration.getCode()));
    }

    public void createNewParameterAndAssociateToConfigOnCreate() {
        createNewParameterAndAssociateToConfigGeral(String.valueOf(this.newConfigurationDTO.getCode()));
    }

    public void createNewParameterAndAssociateToConfigGeral(String code) {
        try {
            client.target(baseUri)
                    .path("/contract_parameters/create")
                    .path(code)
                    .request(MediaType.APPLICATION_XML).post(Entity.xml(this.newParameterDTO));

            clearNewParameterDTO();
        } catch (Exception e) {
            logger.warning(e.getMessage());
        }
    }

    public void createNewExtensionAndAssociateToConfig() {
        createNewExtensionAndAssociateToConfigGeral(String.valueOf(this.currentConfiguration.getCode()));
    }

    public void createNewExtensionAndAssociateToConfigOnCreate() {
        createNewExtensionAndAssociateToConfigGeral(String.valueOf(this.newConfigurationDTO.getCode()));
    }

    public void createNewExtensionAndAssociateToConfigGeral(String code) {
        try {
            client.target(baseUri)
                    .path("/extensions/createAndAssociateConfig")
                    .path(code)
                    .request(MediaType.APPLICATION_XML).post(Entity.xml(this.newExtensionDTO));

            clearNewExtensionDTO();
        } catch (Exception e) {
            logger.warning(e.getMessage());
        }
    }

    public void createNewConfigurationModuleAndAssociateToConfig() {
        createNewConfigurationModuleAndAssociateToConfigGeral(String.valueOf(this.currentConfiguration.getCode()));
    }

    public void createNewConfigurationModuleAndAssociateToConfigOnCreate() {
        createNewConfigurationModuleAndAssociateToConfigGeral(String.valueOf(this.newConfigurationDTO.getCode()));
    }

    public void createNewConfigurationModuleAndAssociateToConfigGeral(String code) {
        try {
            client.target(baseUri)
                    .path("/configurationModules/createAndAssociateConfig")
                    .path(code)
                    .request(MediaType.APPLICATION_XML).post(Entity.xml(this.newConfigurationModuleDTO));

            clearNewExtensionDTO();
        } catch (Exception e) {
            logger.warning(e.getMessage());
        }
    }

    public String createNewConfiguration() {
        try {
            this.newConfigurationDTO.setClientUsername(this.currentClient.getUsername());

            client.target(baseUri)
                    .path("/configurations/create")
                    .request(MediaType.APPLICATION_XML).post(Entity.xml(newConfigurationDTO));

        } catch (Exception e) {
            logger.warning(e.getMessage());
            return null;
        }

        return "configurations_associations?faces-redirect=true";
    }

    public String createNewQuestion() {
        try {
            this.newQuestionDTO.setQuestionSender(this.userManager.getUsername());
            this.newQuestionDTO.setConfigurationCode(this.currentConfiguration.getCode());
            this.newQuestionDTO.setId(0);

            System.out.println("question: " + this.newQuestionDTO.getQuestion());
            client.target(baseUri)
                    .path("/questions/create")
                    .request(MediaType.APPLICATION_XML).post(Entity.xml(newQuestionDTO));

            this.currentQuestions.add(newQuestionDTO);
            clearQuestionDTO();
        } catch (Exception e) {
            logger.warning(e.getMessage());
            return null;
        }

        return "questions_answers_list?faces-redirect=true";
    }

    public String createNewAnswer() {
        try {
            this.newAnswerDTO.setAnswerSender(this.userManager.getUsername());
            this.newAnswerDTO.setQuestionCode(this.currentQuestion.getId());

            client.target(baseUri)
                    .path("/answers/create")
                    .request(MediaType.APPLICATION_XML).post(Entity.xml(newAnswerDTO));

            // clearConfigurationDTO();
            this.currentAnswers.add(newAnswerDTO);
            clearAnswerDTO();
        } catch (Exception e) {
            logger.warning(e.getMessage());
            return null;
        }

        return "answers_list?faces-redirect=true";
    }

    public String createNewConfigurationFromTemplate() {
        try {
            this.newConfigurationDTO = new ConfigurationDTO(0, null, 0, null, 0, null, this.currentClient.getUsername(), null, null);

            client.target(baseUri)
                    .path("/configurations/createByTemplate")
                    .path(String.valueOf(this.code))
                    .request(MediaType.APPLICATION_XML).post(Entity.xml(newConfigurationDTO));

        } catch (Exception e) {
            logger.warning(e.getMessage());
            return null;
        }

        return "configurations_list?faces-redirect=true";
    }

    public String createNewLicenseForModule() {
        try {
            client.target(baseUri)
                    .path("/licenses/create")
                    .path(String.valueOf(this.moduleCode))
                    .request(MediaType.APPLICATION_XML).post(Entity.xml(this.newLinceseDTO));

            clearLicenseDTO();
        } catch (Exception e) {
            logger.warning(e.getMessage());
            return null;
        }

        return "configurations_modules_details?faces-redirect=true";
    }

    public String createNewParameterForModule() {
        try {
            client.target(baseUri)
                    .path("/contract_parameters/createForModule")
                    .path(String.valueOf(this.moduleCode))
                    .request(MediaType.APPLICATION_XML).post(Entity.xml(this.newParameterDTO));

            clearNewParameterDTO();
        } catch (Exception e) {
            logger.warning(e.getMessage());
            return null;
        }

        return "configurations_modules_details?faces-redirect=true";
    }

    public String createNewServiceForModule() {
        try {
            client.target(baseUri)
                    .path("/services/create")
                    .path(String.valueOf(this.moduleCode))
                    .request(MediaType.APPLICATION_XML).post(Entity.xml(this.newServiceDTO));

            this.clearServiceDTO();
        } catch (Exception e) {
            logger.warning(e.getMessage());
            return null;
        }

        return "configurations_modules_details?faces-redirect=true";
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
            FacesExceptionHandler.handleException(e, "Unexpected error. Try again latter!", logger);
            FacesExceptionHandler.handleException(e, "Unexpected error. Try again latter!", component, logger);
        }
    }

    public void getClientLogged() {

        ClientDTO c;
        try {
            c = client.target(baseUri)
                    .path("/clients")
                    .path(userManager.getUsername())
                    .request(MediaType.APPLICATION_XML)
                    .get(ClientDTO.class);
            this.setCurrentClientLogged(c);

        } catch (Exception e) {
            System.err.println(e.getMessage());
            FacesExceptionHandler.handleException(e, "Unexpected error. Try again latter!", logger);
            FacesExceptionHandler.handleException(e, "Unexpected error. Try again latter!", component, logger);
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
        return getAllExtensionsGeral(String.valueOf(this.newConfigurationDTO.getCode()));
    }
    
    public List<ExtensionDTO> getAllExtensionsFromTemplate() {
        return getAllExtensionsGeral(String.valueOf(this.newTemplateDTO.getCode()));
    }

    public List<ExtensionDTO> getAllExtensionsOnUpdate() {
        return getAllExtensionsGeral(String.valueOf(this.currentConfiguration.getCode()));
    }

    public List<ExtensionDTO> getAllExtensionsGeral(String code) {
        List<ExtensionDTO> extensionsDTO = new LinkedList<>();

        try {
            extensionsDTO = client.target(baseUri).path("/extensions/all")
                    .path(code)
                    .request(MediaType.APPLICATION_XML)
                    .get(new GenericType<List<ExtensionDTO>>() {
                    });
        } catch (Exception ex) {
            logger.warning(ex.getMessage());
        }
        return extensionsDTO;
    }

    public List<ConfigurationModuleDTO> getAllModulesFromConfiguration() {
        return getAllModulesFromConfigurationGeral(String.valueOf(this.newConfigurationDTO.getCode()));
    }

    public List<ConfigurationModuleDTO> getAllModulesFromConfigurationOnUpdate() {
        return getAllModulesFromConfigurationGeral(String.valueOf(this.currentConfiguration.getCode()));
    }

    public List<ConfigurationModuleDTO> getAllModulesFromConfigurationGeral(String code) {
        List<ConfigurationModuleDTO> modulesDTO = new LinkedList<>();

        try {
            modulesDTO = client.target(baseUri).path("/configurationModules/all")
                    .path(code)
                    .request(MediaType.APPLICATION_XML)
                    .get(new GenericType<List<ConfigurationModuleDTO>>() {
                    });
        } catch (Exception ex) {
            logger.warning(ex.getMessage());
        }

        return modulesDTO;
    }

    public List<SoftwareModuleDTO> getAllModulesFromTemplate() {
        List<SoftwareModuleDTO> modulesDTO = new LinkedList<>();

        try {
            modulesDTO = client.target(baseUri).path("/softwareModules/softwares").path(String.valueOf(this.currentTemplate.getSoftwareCode())).request(MediaType.APPLICATION_XML)
                    .get(new GenericType<List<SoftwareModuleDTO>>() {
                    });
        } catch (Exception ex) {
            logger.warning(ex.getMessage());
        }

        return modulesDTO;
    }

    public List<ParameterDTO> getAllParametersFromConfiguration() {
        List<ParameterDTO> parameter = new LinkedList<>();

        try {
            parameter = client.target(baseUri).path("/contract_parameters/all/configurations").request(MediaType.APPLICATION_XML)
                    .get(new GenericType<List<ParameterDTO>>() {
                    });
        } catch (Exception ex) {
            logger.warning(ex.getMessage());
        }

        return parameter;
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
        }

        this.softwareExtensions = returnedSoftwareExtensions;

    }

    public List<ParameterDTO> getCurrentContractParameters() {
        return getCurrentContractParametersForAllConfiguration(String.valueOf(currentTemplate.getContractCode()));
    }

    public List<ParameterDTO> getClientConfigurationsContractParameters() {
        return getCurrentContractParametersForAllConfiguration(String.valueOf(currentConfiguration.getContractCode()));
    }

    public List<ParameterDTO> getCurrentContractParametersForAllConfiguration(String code) {
        List<ParameterDTO> contractParameters = new LinkedList<>();

        try {

            contractParameters = client.target(baseUri).path("/contract_parameters/contracts").path(code)
                    .request(MediaType.APPLICATION_XML)
                    .get(new GenericType<List<ParameterDTO>>() {
                    });

        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage());

        }

        return contractParameters;
    }

    public List<ParameterDTO> getCurrentConfigurationParameters() {
        List<ParameterDTO> contractParameters = new LinkedList<>();

        try {

            String code = String.valueOf(currentConfiguration.getCode());

            contractParameters = client.target(baseUri).path("/contract_parameters/configurations").path(code)
                    .request(MediaType.APPLICATION_XML)
                    .get(new GenericType<List<ParameterDTO>>() {
                    });

        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage());

        }

        return contractParameters;
    }

    public List<ParameterDTO> getCurrentConfigurationModuleParameters() {
        List<ParameterDTO> contractParameters = new LinkedList<>();

        try {

            String code = String.valueOf(this.moduleCode);

            contractParameters = client.target(baseUri).path("/contract_parameters/modules").path(code)
                    .request(MediaType.APPLICATION_XML)
                    .get(new GenericType<List<ParameterDTO>>() {
                    });

        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage());

        }

        return contractParameters;
    }

    public SoftwareDTO getCurrentSoftware() {
        return getSoftwareDTOByConfigurationCode(String.valueOf(currentTemplate.getSoftwareCode()));

    }

    public SoftwareDTO getClientConfigurationSoftware() {

        return getSoftwareDTOByConfigurationCode(String.valueOf(currentConfiguration.getSoftwareCode()));

    }

    public SoftwareDTO getSoftwareDTOByConfigurationCode(String code) {
        try {
            return client.target(baseUri).path("/softwares").path(code)
                    .request(MediaType.APPLICATION_XML)
                    .get(new GenericType<SoftwareDTO>() {
                    });
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage());
        }
        return null;
    }

    public List<String> getCurrentSoftwareVersions() {
        return getCurrentSoftwareVersionsAllConfigurations(String.valueOf(currentTemplate.getSoftwareCode()));
    }

    public List<String> getClientConfigurationSoftwareVersions() {
        return getCurrentSoftwareVersionsAllConfigurations(String.valueOf(currentConfiguration.getSoftwareCode()));
    }

    public List<String> getCurrentSoftwareVersionsAllConfigurations(String code) {
        List<String> versions = new LinkedList<>();

        try {
            Response serviceResponse = client.target(baseUri).path("/softwares/versions").path(code)
                    .request(MediaType.APPLICATION_JSON).get(Response.class);

            versions = computeJsonResponseToStringList(serviceResponse);

        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage());
        }

        return versions;
    }

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
        }

        return softwareModules;
    }

    public List<ExtensionDTO> getCurrentTemplateExtensions() {
        return getExtensionsDTOByConfigurationCode(String.valueOf(currentTemplate.getCode()));
    }

    public List<ExtensionDTO> getCurrentConfigurationsExtensions() {
        return getExtensionsDTOByConfigurationCode(String.valueOf(currentConfiguration.getCode()));
    }

    public List<ExtensionDTO> getCurrentTemplateConfigurationsExtensions() {
        return getExtensionsDTOByConfigurationCode(String.valueOf(currentTemplate.getCode()));
    }

    public List<ExtensionDTO> getExtensionsDTOByConfigurationCode(String code) {
        List<ExtensionDTO> extensions = new LinkedList<>();

        try {
            extensions = client.target(baseUri).path("/extensions/configurations").path(code)
                    .request(MediaType.APPLICATION_XML)
                    .get(new GenericType<List<ExtensionDTO>>() {
                    });

        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage());
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

    public List<ConfigurationModuleDTO> getCurrentTemplateConfigurationsModules() {
        List<ConfigurationModuleDTO> configurationModulesDTO = new LinkedList<>();

        try {
            String code = String.valueOf(currentTemplate.getCode());

            configurationModulesDTO = client.target(baseUri).path("/configurationModules").path(code)
                    .request(MediaType.APPLICATION_XML)
                    .get(new GenericType<List<ConfigurationModuleDTO>>() {
                    });

        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage());
        }

        return configurationModulesDTO;
    }

    public List<ArtefactDTO> getCurrentTemplateArtefacts() {
        List<ArtefactDTO> artefacts = new LinkedList<>();

        try {

            String code = String.valueOf(currentTemplate.getCode());

            artefacts = client.target(baseUri).path("/artefacts").path(code)
                    .request(MediaType.APPLICATION_XML)
                    .get(new GenericType<List<ArtefactDTO>>() {
                    });

        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage());
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
        }

        return artefacts;
    }

    public List<HelpMaterialDTO> getCurrentTemplateHelpMaterials() {

        List<HelpMaterialDTO> helpMaterials = new LinkedList<>();

        try {
            String code = String.valueOf(currentTemplate.getCode());

            helpMaterials = client.target(baseUri).path("/helpMaterials").path(code)
                    .request(MediaType.APPLICATION_XML)
                    .get(new GenericType<List<HelpMaterialDTO>>() {
                    });

        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage());
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
        }

        return helpMaterials;
    }

    public List<QuestionDTO> getAllQuestionsFromConfiguration() {

        List<QuestionDTO> questionDTOs = new LinkedList<>();

        try {

            questionDTOs = client.target(baseUri).path("/questions/configuration").path(String.valueOf(currentConfiguration.getCode()))
                    .request(MediaType.APPLICATION_XML)
                    .get(new GenericType<List<QuestionDTO>>() {
                    });

        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage());
        }
        this.currentQuestions = questionDTOs;
        return this.currentQuestions;
    }

    public List<AnswerDTO> getAllAnswerFromQuestion() {

        List<AnswerDTO> answerDTOs = new LinkedList<>();

        try {

            answerDTOs = client.target(baseUri).path("/answers/question").path(String.valueOf(currentQuestion.getId()))
                    .request(MediaType.APPLICATION_XML)
                    .get(new GenericType<List<AnswerDTO>>() {
                    });

        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage());
        }
        this.currentAnswers = answerDTOs;
        return this.currentAnswers;
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

    public List<ConfigurationDTO> getCurrentLoggedClientConfigurations() {

        return this.getClientConfigurations(currentClientLogged.getUsername());
    }

    public List<ConfigurationDTO> getClientConfigurations(String username) {

        List<ConfigurationDTO> configurationsDTO = new LinkedList<>();

        try {
            configurationsDTO = client.target(baseUri).path("/configurations").path(username)
                    .request(MediaType.APPLICATION_XML)
                    .get(new GenericType<List<ConfigurationDTO>>() {
                    });

        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage());
        }

        return configurationsDTO;
    }

    public List<ClientDTO> getClientsToCloneConfiguration() {
        List<ClientDTO> clients = new LinkedList();
        for (ClientDTO c : this.getAllClients()) {
            List<ConfigurationDTO> confs = this.getClientConfigurations(c.getUsername());
            if (!c.getUsername().equalsIgnoreCase(currentClient.getUsername()) && confs.size() != 0) {
                clients.add(c);
            }
        }

        return clients;
    }

    public List<ConfigurationDTO> getClientSelectToCloneConfigurations() {

        List<ConfigurationDTO> configurationsDTO = new LinkedList<>();

        try {
            String username = this.usernameClientSelectedToClone;

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
        }

        return servicesDTO;
    }

    public List<String> computeJsonResponseToStringList(Response serviceResponse) {
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

    //******************* Remove Methods
    public String removeClient(ActionEvent event) {
        try {
            UIParameter param = (UIParameter) event.getComponent().findComponent("deleteClientUsername");
            String username = param.getValue().toString();

            client.target(baseUri).path("/clients")
                    .path(username)
                    .request(MediaType.APPLICATION_XML)
                    .delete();

        } catch (Exception e) {
            logger.warning("Problem removing the client");
            return null;
        }
        return "clients_list?faces-redirect=true";
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
            logger.warning("Problem removing the administrator");
            return null;
        }
        return "clients_list?faces-redirect=true";
    }

    public String removeConfiguration(ActionEvent event) {
        try {
            UIParameter param = (UIParameter) event.getComponent().findComponent("deleteConfigurationCode");
            String code = param.getValue().toString();

            client.target(baseUri).path("/configurations")
                    .path(code)
                    .request(MediaType.APPLICATION_XML)
                    .delete();

        } catch (Exception e) {
            logger.warning(e.getMessage());
            return null;
        }
        return "configurations_list?faces-redirect=true";
    }

    public String removeLicense(ActionEvent event) {
        try {
            UIParameter param = (UIParameter) event.getComponent().findComponent("deleteLicenseCode");
            String code = param.getValue().toString();

            client.target(baseUri).path("/licenses")
                    .path(code)
                    .request(MediaType.APPLICATION_XML)
                    .delete();

        } catch (Exception e) {
            logger.warning(e.getMessage());
            return null;
        }
        return "configurations_modules_details?faces-redirect=true";
    }

    public String removeParameter(ActionEvent event) {
        try {
            UIParameter param = (UIParameter) event.getComponent().findComponent("deleteParameterCode");
            String name = param.getValue().toString();

            client.target(baseUri).path("/contract_parameters")
                    .path(name)
                    .request(MediaType.APPLICATION_XML)
                    .delete();

        } catch (Exception e) {
            logger.warning(e.getMessage());
            return null;
        }
        return "configurations_modules_details?faces-redirect=true";
    }

    public String removeService(ActionEvent event) {
        try {
            UIParameter param = (UIParameter) event.getComponent().findComponent("deleteServiceCode");
            String code = param.getValue().toString();

            client.target(baseUri).path("/services")
                    .path(code)
                    .request(MediaType.APPLICATION_XML)
                    .delete();

        } catch (Exception e) {
            logger.warning(e.getMessage());
            return null;
        }
        return "configurations_modules_details?faces-redirect=true";
    }

    public String removeConfigurationTemplate(ActionEvent event) {
        try {
            UIParameter param = (UIParameter) event.getComponent().findComponent("deletedTemplateCode");
            String code = param.getValue().toString();

            client.target(baseUri).path("/templates")
                    .path(code)
                    .request(MediaType.APPLICATION_XML)
                    .delete();

        } catch (Exception e) {
            logger.warning(e.getMessage());
            return null;
        }
        return "templates_list?faces-redirect=true";
    }

    //******************* Associate Methods
    public void associateExtensionsToConfiguration() {
        associateExtension(String.valueOf(this.newConfigurationDTO.getCode()));
    }

    public void associateExtensionsToTemplateConfigurationOnUpdate() {
        associateExtension(String.valueOf(this.currentTemplate.getCode()));
    }

    public void associateExtensionsToConfigurationOnUpdate() {
        associateExtension(String.valueOf(this.currentConfiguration.getCode()));
    }

    public void associateExtension(String configCode) {
        try {
            selectedExtension = new ExtensionDTO(this.code, null, null, 0, null, null);

            client.target(baseUri)
                    .path("/configurationsSuper/associateExtensions").path(configCode)
                    .request(MediaType.APPLICATION_XML).put(Entity.xml(this.selectedExtension));

        } catch (Exception e) {
            logger.warning(e.getMessage());
        }
    }

    public void associateConfigurationModuleToConfiguration() {
        associateConfigurationModule(String.valueOf(this.newConfigurationDTO.getCode()));
    }

    public void associateConfigurationModuleToConfigurationOnUpdate() {
        associateConfigurationModule(String.valueOf(this.currentConfiguration.getCode()));
    }

    public void associateConfigurationModule(String configCode) {
        try {
            selectedConfigurationModule = new ConfigurationModuleDTO(this.code, null, 0, null, null);

            client.target(baseUri)
                    .path("/configurationModules/associateModuleConfigurations").path(configCode)
                    .request(MediaType.APPLICATION_XML).put(Entity.xml(this.selectedConfigurationModule));

        } catch (Exception e) {
            logger.warning(e.getMessage());
        }
    }

    public void associateParameterToConfiguration() {
        associateParameter(String.valueOf(this.newConfigurationDTO.getCode()));

    }

    public void associateConfigurationModuleToTemplateConfigurationOnUpdate() {
        associateTemplateConfigurationModule(String.valueOf(this.currentTemplate.getCode()));
    }

    public void associateParameterToConfigurationOnUpdate() {
        associateParameter(String.valueOf(this.currentConfiguration.getCode()));
    }

    public void associateTemplateConfigurationModule(String tempCode) {
        try {
            selectedTemplateConfigurationModule = new SoftwareModuleDTO(this.moduleCode, null, 0, null, null);

            client.target(baseUri)
                    .path("/softwareModules/associateModuleConfigurations").path(tempCode)
                    .request(MediaType.APPLICATION_XML).put(Entity.xml(this.selectedTemplateConfigurationModule));

        } catch (Exception e) {
            logger.warning(e.getMessage());
        }
    }

    public void associateParameter(String configCode) {
        try {
            ParameterDTO parameterDTO = new ParameterDTO(this.paramName, null, null);

            client.target(baseUri)
                    .path("/contract_parameters/associateConfigurations").path(configCode)
                    .request(MediaType.APPLICATION_XML).put(Entity.xml(parameterDTO));

        } catch (Exception e) {
            logger.warning(e.getMessage());
        }
    }

    //******************* Dissociate Methods
    public void dissociateExtensionFromConfiguration(ActionEvent event) {
        try {
            String configCode = String.valueOf(this.currentConfiguration.getCode());

            UIParameter param = (UIParameter) event.getComponent().findComponent("extensionnCode");
            int extensionCode = Integer.parseInt(param.getValue().toString());

            selectedExtension = new ExtensionDTO(extensionCode, null, null, 0, null, null);

            client.target(baseUri)
                    .path("/configurationsSuper/dissociateExtensions").path(configCode)
                    .request(MediaType.APPLICATION_XML).put(Entity.xml(this.selectedExtension));

        } catch (Exception e) {
            logger.warning(e.getMessage());
        }
    }

    public void dissociateExtensionFromTemplateConfiguration(ActionEvent event) {
        try {
            String configCode = String.valueOf(this.currentTemplate.getCode());

            UIParameter param = (UIParameter) event.getComponent().findComponent("extensionnCode");
            int extensionCode = Integer.parseInt(param.getValue().toString());

            selectedExtension = new ExtensionDTO(extensionCode, null, null, 0, null, null);

            client.target(baseUri)
                    .path("/configurationsSuper/dissociateExtensions").path(configCode)
                    .request(MediaType.APPLICATION_XML).put(Entity.xml(this.selectedExtension));

        } catch (Exception e) {
            logger.warning(e.getMessage());
        }
    }

    public void dissociateModuleFromConfiguration(ActionEvent event) {
        try {
            String configCode = String.valueOf(this.currentConfiguration.getCode());

            UIParameter param = (UIParameter) event.getComponent().findComponent("moduleCode");
            int moduleCode = Integer.parseInt(param.getValue().toString());

            selectedConfigurationModule = new ConfigurationModuleDTO(moduleCode, null, 0, null, null);

            client.target(baseUri)
                    .path("/configurationModules/dissociateModuleConfigurations").path(configCode)
                    .request(MediaType.APPLICATION_XML).put(Entity.xml(this.selectedConfigurationModule));

        } catch (Exception e) {
            logger.warning(e.getMessage());
        }
    }

    public void dissociateModuleFromTemplateConfiguration(ActionEvent event) {
        try {
            String templateCode = String.valueOf(this.currentTemplate.getCode());

            UIParameter param = (UIParameter) event.getComponent().findComponent("moduleCode");
            int moduleCode = Integer.parseInt(param.getValue().toString());

            selectedTemplateConfigurationModule = new SoftwareModuleDTO(moduleCode, null, 0, null, null);

            client.target(baseUri)
                    .path("/softwareModules/dissociateModuleConfigurations").path(templateCode)
                    .request(MediaType.APPLICATION_XML).put(Entity.xml(this.selectedTemplateConfigurationModule));

        } catch (Exception e) {
            logger.warning(e.getMessage());
        }
    }

    public void dissociateParameterFromConfiguration(ActionEvent event) {
        try {
            String configCode = String.valueOf(this.currentConfiguration.getCode());

            UIParameter param = (UIParameter) event.getComponent().findComponent("paramName");
            String name = param.getValue().toString();

            ParameterDTO parameterDTO = new ParameterDTO(name, null, null);

            client.target(baseUri)
                    .path("/contract_parameters/dissociateConfigurations").path(configCode)
                    .request(MediaType.APPLICATION_XML).put(Entity.xml(parameterDTO));

        } catch (Exception e) {
            logger.warning(e.getMessage());
        }
    }

    public void dissociateArtefactFromConfiguration(ActionEvent event) {
        try {

            String configCode = String.valueOf(this.currentConfiguration.getCode());

            UIParameter param = (UIParameter) event.getComponent().findComponent("filenameArt");
            String filename = param.getValue().toString();

            ArtefactDTO artefactDTO = new ArtefactDTO(filename, null);

            client.target(baseUri)
                    .path("/configurationsSuper/dissociateArtefacts").path(configCode)
                    .request(MediaType.APPLICATION_XML).put(Entity.xml(artefactDTO));

        } catch (Exception e) {
            logger.warning(e.getMessage());
        }
    }

    public void dissociateArtefactFromTemplateConfiguration(ActionEvent event) {
        try {

            String configCode = String.valueOf(this.currentTemplate.getCode());

            UIParameter param = (UIParameter) event.getComponent().findComponent("filenameArt");
            String filename = param.getValue().toString();

            ArtefactDTO artefactDTO = new ArtefactDTO(filename, null);

            client.target(baseUri)
                    .path("/configurationsSuper/dissociateArtefacts").path(configCode)
                    .request(MediaType.APPLICATION_XML).put(Entity.xml(artefactDTO));

        } catch (Exception e) {
            logger.warning(e.getMessage());
        }
    }

    public void dissociateHelpMaterialFromConfiguration(ActionEvent event) {
        try {

            String configCode = String.valueOf(this.currentConfiguration.getCode());

            UIParameter param = (UIParameter) event.getComponent().findComponent("filenameHelp");
            String filename = param.getValue().toString();

            HelpMaterialDTO helpMaterialDTO = new HelpMaterialDTO(filename, null);

            client.target(baseUri)
                    .path("/configurationsSuper/dissociateHelpMaterial").path(configCode)
                    .request(MediaType.APPLICATION_XML).put(Entity.xml(helpMaterialDTO));

        } catch (Exception e) {
            logger.warning(e.getMessage());
        }
    }

    public void dissociateHelpMaterialFromTemplateConfiguration(ActionEvent event) {
        try {

            String configCode = String.valueOf(this.currentTemplate.getCode());

            UIParameter param = (UIParameter) event.getComponent().findComponent("filenameHelp");
            String filename = param.getValue().toString();

            HelpMaterialDTO helpMaterialDTO = new HelpMaterialDTO(filename, null);

            client.target(baseUri)
                    .path("/configurationsSuper/dissociateHelpMaterial").path(configCode)
                    .request(MediaType.APPLICATION_XML).put(Entity.xml(helpMaterialDTO));

        } catch (Exception e) {
            logger.warning(e.getMessage());
        }
    }

    //******************* Clone configuration
    public String cloneClientConfiguration() {
        try {
            client.target(baseUri)
                    .path("/configurations/clone")
                    .path(String.valueOf(this.codeConfigurationSelectedToClone))
                    .path(this.currentClient.getUsername())
                    .request(MediaType.APPLICATION_XML).post(Entity.xml(newConfigurationDTO));
        } catch (Exception e) {
            logger.warning("Problem cloning client's configuration");
            logger.warning(e.getMessage());
            return null;
        }

        return "configurations_list?faces-redirect=true";
    }

    //******************* Update Methods
    public String updateAdministrator() {
        try {
            client.target(baseUri)
                    .path("/administrators/update")
                    .request(MediaType.APPLICATION_XML).put(Entity.xml(currentAdmin));

        } catch (Exception e) {
            logger.warning("Problem updating the administrator");
            return "update_admin";
        }
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
        return "clients_list?faces-redirect=true";
    }

    public String updateConfiguration() {
        try {
            client.target(baseUri)
                    .path("/configurations/update")
                    .request(MediaType.APPLICATION_XML).put(Entity.xml(this.currentConfiguration));

        } catch (Exception e) {
            logger.warning("Problem updating the client");
            return "configurations_list?faces-redirect=true";
        }
        return "configurations_list?faces-redirect=true";
    }

    public String updateLicenseForModule() {
        try {
            client.target(baseUri)
                    .path("/licenses/update")
                    .path(String.valueOf(this.moduleCode))
                    .request(MediaType.APPLICATION_XML).put(Entity.xml(this.newLinceseDTO));

            clearLicenseDTO();
        } catch (Exception e) {
            logger.warning(e.getMessage());
            return "configurations_modules_details?faces-redirect=true";
        }

        return "configurations_modules_details?faces-redirect=true";
    }

    public String updateParameterForModule() {
        try {
            client.target(baseUri)
                    .path("/contract_parameters/update")
                    .path(String.valueOf(this.moduleCode))
                    .request(MediaType.APPLICATION_XML).put(Entity.xml(this.newParameterDTO));

            this.clearNewParameterDTO();
        } catch (Exception e) {
            logger.warning(e.getMessage());
            return "configurations_modules_details?faces-redirect=true";
        }

        return "configurations_modules_details?faces-redirect=true";
    }

    public String updateServiceForModule() {
        try {
            client.target(baseUri)
                    .path("/services/update")
                    .path(String.valueOf(this.moduleCode))
                    .request(MediaType.APPLICATION_XML).put(Entity.xml(this.newServiceDTO));

            this.clearServiceDTO();
        } catch (Exception e) {
            logger.warning(e.getMessage());
            return "configurations_modules_details?faces-redirect=true";
        }

        return "configurations_modules_details?faces-redirect=true";
    }

    public String updateTemplateConfiguration() {
        try {
            client.target(baseUri)
                    .path("/templates/update")
                    .request(MediaType.APPLICATION_XML).put(Entity.xml(this.currentTemplate));

        } catch (Exception e) {
            logger.warning("Problem updating the template configuration");
            return "templates_list";
        }
        return "templates_list?faces-redirect=true";
    }

    public void upload(boolean isArtefact, boolean isTemplate) {
        if (file != null) {
            try {
                String filename = file.getFileName().substring(file.getFileName().lastIndexOf("\\") + 1);
                String mimetype = FacesContext.getCurrentInstance().getExternalContext().getMimeType(filename);

                InputStream in = file.getInputstream();

                //com este path ele coloca dentro de C:\Users\Iolanda\Documents\DAE\PL\DAEProject\dist\gfdeploy\ProjectDAE\ProjectDAE-war_war\resources\files
                path = FacesContext.getCurrentInstance().getExternalContext().getRealPath("resources/files/");
                //com este path ele coloca dentro de \dist\gfdeploy\ProjectDAE\ProjectDAE-war_war\resources\files
                FileOutputStream out = new FileOutputStream(path + "/" + filename);
                //Se quiserem ver na pasta files colocar caminho à mão
                //FileOutputStream out = new FileOutputStream("C:/Users/Iolanda/Documents/DAE/PL/DAEProject/ProjectDAE-war/web/resources/files/" + filename);

                byte[] b = new byte[1024];
                int readBytes = in.read(b);
                while (readBytes != -1) {
                    out.write(b, 0, readBytes);
                    readBytes = in.read(b);
                }
                in.close();
                out.close();

                if (isArtefact) {
                    addArtefactToConfiguration(filename, mimetype, isTemplate);
                } else {
                    addHelpMaterialToConfiguration(filename, mimetype, isTemplate);
                }

            } catch (IOException e) {
                logger.warning(e.getMessage() + "file" + file);
            }
        }
    }

    public void addArtefactToConfiguration(String filename, String mimetype, boolean isTemplate) {
        try {

            String codeC = null;
            if (isTemplate) {
                if (this.currentTemplate != null) {
                    codeC = String.valueOf(this.currentTemplate.getCode());
                } else {
                    codeC = String.valueOf(this.newTemplateDTO.getCode());
                }

            } else {
                if (this.currentConfiguration != null) {
                    codeC = String.valueOf(this.currentConfiguration.getCode());
                } else {
                    codeC = String.valueOf(this.newConfigurationDTO.getCode());
                }
            }

            ArtefactDTO artefactDTO = new ArtefactDTO(filename, mimetype);

            client.target(baseUri)
                    .path("/configurationsSuper/associateArtefacts").path(codeC)
                    .request(MediaType.APPLICATION_XML).put(Entity.xml(artefactDTO));

        } catch (Exception e) {
            logger.warning(e.getMessage());
        }
    }

    public void addHelpMaterialToConfiguration(String filename, String mimetype, boolean isTemplate) {
        try {

            String codeC = null;
            if (isTemplate) {
                if (this.currentTemplate != null) {
                    codeC = String.valueOf(this.currentTemplate.getCode());
                } else {
                    codeC = String.valueOf(this.newTemplateDTO.getCode());
                }
            } else {
                if (this.currentConfiguration != null) {
                    codeC = String.valueOf(this.currentConfiguration.getCode());
                } else {
                    codeC = String.valueOf(this.newConfigurationDTO.getCode());
                }
            }

            HelpMaterialDTO helpMaterialDTO = new HelpMaterialDTO(filename, mimetype);

            client.target(baseUri)
                    .path("/configurationsSuper/associateHelpMaterials").path(codeC)
                    .request(MediaType.APPLICATION_XML).put(Entity.xml(helpMaterialDTO));

        } catch (Exception e) {
            logger.warning(e.getMessage());
        }
    }

    // **************************** GET's e SET's
    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public void clearNewParameterDTO() {
        this.newParameterDTO.reset();
    }

    public void clearNewExtensionDTO() {
        this.newExtensionDTO.reset();
    }

    public void clearNewConfigurationModuleDTO() {
        this.newConfigurationModuleDTO.reset();
    }

    public void clearLicenseDTO() {
        this.newLinceseDTO.reset();
    }

    public void clearQuestionDTO() {
        this.newQuestionDTO.reset();
    }

    public void clearAnswerDTO() {
        this.newAnswerDTO.reset();
    }

    public void clearServiceDTO() {
        this.newServiceDTO.reset();
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

    public String getParamName() {
        return paramName;
    }

    public void setParamName(String paramName) {
        this.paramName = paramName;
    }

    public ParameterDTO getNewParameterDTO() {
        return newParameterDTO;
    }

    public void setNewParameterDTO(ParameterDTO newParameterDTO) {
        this.newParameterDTO = newParameterDTO;
    }

    public ExtensionDTO getNewExtensionDTO() {
        return newExtensionDTO;
    }

    public void setNewExtensionDTO(ExtensionDTO newExtensionDTO) {
        this.newExtensionDTO = newExtensionDTO;
    }

    public ConfigurationModuleDTO getNewConfigurationModuleDTO() {
        return newConfigurationModuleDTO;
    }

    public void setNewConfigurationModuleDTO(ConfigurationModuleDTO newConfigurationModuleDTO) {
        this.newConfigurationModuleDTO = newConfigurationModuleDTO;
    }

    public LicenseDTO getNewLinceseDTO() {
        return newLinceseDTO;
    }

    public void setNewLinceseDTO(LicenseDTO newLinceseDTO) {
        this.newLinceseDTO = newLinceseDTO;
    }

    public ServiceDTO getNewServiceDTO() {
        return newServiceDTO;
    }

    public void setNewServiceDTO(ServiceDTO newServiceDTO) {
        this.newServiceDTO = newServiceDTO;
    }

    public String getUsernameClientSelectedToClone() {
        return usernameClientSelectedToClone;
    }

    public void setUsernameClientSelectedToClone(String usernameClientSelectedToClone) {
        this.usernameClientSelectedToClone = usernameClientSelectedToClone;
    }

    public int getCodeConfigurationSelectedToClone() {
        return codeConfigurationSelectedToClone;
    }

    public void setCodeConfigurationSelectedToClone(int codeConfigurationSelectedToClone) {
        this.codeConfigurationSelectedToClone = codeConfigurationSelectedToClone;
    }

    public ClientDTO getCurrentClientLogged() {
        return currentClientLogged;
    }

    public void setCurrentClientLogged(ClientDTO currentClientLogged) {
        this.currentClientLogged = currentClientLogged;
    }

    public QuestionDTO getNewQuestionDTO() {
        return newQuestionDTO;
    }

    public void setNewQuestionDTO(QuestionDTO newQuestionDTO) {
        this.newQuestionDTO = newQuestionDTO;
    }

    public QuestionDTO getCurrentQuestion() {
        return currentQuestion;
    }

    public void setCurrentQuestion(QuestionDTO currentQuestion) {
        this.currentQuestion = currentQuestion;
    }

    public AnswerDTO getCurrentAnswer() {
        return currentAnswer;
    }

    public void setCurrentAnswer(AnswerDTO currentAnswer) {
        this.currentAnswer = currentAnswer;
    }

    public List<AnswerDTO> getCurrentAnswers() {
        return currentAnswers;
    }

    public void setCurrentAnswers(List<AnswerDTO> currentAnswers) {
        this.currentAnswers = currentAnswers;
    }

    public List<QuestionDTO> getCurrentQuestions() {
        return currentQuestions;
    }

    public void setCurrentQuestions(List<QuestionDTO> currentQuestions) {
        this.currentQuestions = currentQuestions;
    }

    public AnswerDTO getNewAnswerDTO() {
        return newAnswerDTO;
    }

    public void setNewAnswerDTO(AnswerDTO newAnswerDTO) {
        this.newAnswerDTO = newAnswerDTO;
    }

}
