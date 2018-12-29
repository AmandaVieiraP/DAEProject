/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejbs;

import dtos.ConfigurationDTO;
import entities.Answer;
import entities.Artefact;
import entities.Client;
import entities.Configuration;
import entities.ConfigurationModule;
import entities.Contract;
import entities.Extension;
import entities.HelpMaterial;
import entities.Parameter;
import entities.Question;
import entities.Software;
import entities.SoftwareModule;
import entities.Template;
import exceptions.EntityDoesNotExistsException;
import java.util.LinkedList;
import java.util.List;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author Iolanda
 */
@Stateless
@Path("/configurations")
public class ConfigurationBean {

    @PersistenceContext
    EntityManager em;

    @EJB
    EmailBean emailBean;

    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @RolesAllowed({"Administrator","Client"})
    @Path("{username}")
    public List<ConfigurationDTO> getConfigurationsByClient(@PathParam("username") String username) {
        try {

            Client client = em.find(Client.class, username);

            if (client == null) {
                return null;
            }

            return configurationsListToConfigurationsListDTO(client.getConfigurations());

        } catch (Exception ex) {
            throw new EJBException(ex.getMessage());
        }
    }

    @POST
    @Path("/create")
    @RolesAllowed({"Administrator"})
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void createREST(ConfigurationDTO configurationDTO) {
        try {
            Configuration config = this.create(configurationDTO.getCode(), configurationDTO.getDescription(), configurationDTO.getSoftwareCode(),
                    configurationDTO.getContractCode(), configurationDTO.getVersion(), configurationDTO.getClientUsername(),
                    configurationDTO.getDbServerIp(), configurationDTO.getApplicationServerIp());
            this.sendEmail(configurationDTO.getClientUsername(), config, "create");

        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

    @POST
    @Path("/createByTemplate/{id}")
    @RolesAllowed({"Administrator"})
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void createByTemplateREST(@PathParam("id") int code, ConfigurationDTO configurationDTO) {
        try {

            Template t = em.find(Template.class, code);

            if (t == null) {
                return;
            }

            Client client = em.find(Client.class, configurationDTO.getClientUsername());
            if (client == null) {
                return;
            }

            int lastCode = (Integer) em.createNamedQuery("getMaxConfigurationsCode").getSingleResult();

            lastCode = lastCode + 1;
            Configuration configuration = new Configuration(lastCode, t.getDescription(), t.getSoftware(), t.getContract(), t.getVersion(), client, null, null);

            t.getSoftware().addConfiguration(configuration);

            t.getContract().addConfiguration(configuration);

            client.addConfiguration(configuration);

            configuration.setExtensions(t.getExtensions());

            for (Extension e : t.getExtensions()) {
                e.addConfiguration(configuration);
            }

            configuration.setArtefactsRepository(t.getArtefactsRepository());

            for (Artefact a : t.getArtefactsRepository()) {
                a.addConfigurations(configuration);
            }

            configuration.setHelpMaterials(t.getHelpMaterials());

            for (HelpMaterial h : t.getHelpMaterials()) {
                h.addConfigurations(configuration);
            }
            em.persist(configuration);

            int modCode = (Integer) em.createNamedQuery("getMaxModulesCode").getSingleResult();

            for (SoftwareModule s : t.getModules()) {
                modCode = modCode + 1;
                ConfigurationModule cm = new ConfigurationModule(modCode, s.getDescription(), t.getSoftware(), t.getVersion());

                em.persist(cm);

                cm.addConfiguration(configuration);
                configuration.addModule(cm);

            }

            this.sendEmail(client.getUsername(), configuration, "create");

        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

    @POST
    @Path("clone/{idConfig}/{idClient}")
    @RolesAllowed({"Administrator"})
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void cloneConfiguration(@PathParam("idConfig") int code, @PathParam("idClient") String username) {
        try {
            Configuration conf = em.find(Configuration.class, code);

            if (conf == null) {
                return;
            }

            Client client = em.find(Client.class, username);

            if (client == null) {
                return;
            }

            int lastCode = (Integer) em.createNamedQuery("getMaxConfigurationsCode").getSingleResult();
            lastCode = lastCode + 1;

            Configuration newConfig = this.create(lastCode, conf.getDescription(), conf.getSoftware().getCode(), conf.getContract().getCode(), conf.getVersion(), client.getUsername(), null, null);

            this.sendEmail(client.getUsername(), newConfig, "create");
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

    @DELETE
    @Path("{id}")
    @RolesAllowed({"Administrator"})
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void remove(@PathParam("id") int code) {
        try {
            Configuration configuration = em.find(Configuration.class, code);

            if (configuration == null) {
                return;
            }
            
            /*for(Question q:configuration.getQuestions()){
               
                for(Answer a: q.getAnswers()){
                    q.removeAnswer(a);
                }
                
            */
            
            
            
            for(Question q:configuration.getQuestions()){
               
                q.getConfiguration().removeQuestions(q);
                
            }
            
            
            

            configuration.getClient().removeConfiguration(configuration);

            configuration.getSoftware().removeConfiguration(configuration);

            configuration.getContract().removeConfiguration(configuration);
            
            
            
            for (Extension e : configuration.getExtensions()) {
                e.removeConfiguration(configuration);
            }

            for (Artefact a : configuration.getArtefactsRepository()) {
                a.removeConfigurations(configuration);
            }

            for (HelpMaterial h : configuration.getHelpMaterials()) {
                h.removeConfigurations(configuration);
            }

            for (Parameter p : configuration.getConfigurationParameters()) {
                p.removeConfigurations(configuration);
            }

            em.remove(configuration);
        } catch (Exception ex) {
            throw new EJBException(ex.getMessage());
        }
    }

    @PUT
    @Path("/update")
    @RolesAllowed({"Administrator"})
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void updateRest(ConfigurationDTO configurationDTO) {
        try {
            Configuration c = em.find(Configuration.class, configurationDTO.getCode());

            if (c == null) {
                return;
            }

            Software newSoftware = em.find(Software.class, configurationDTO.getSoftwareCode());

            if (newSoftware == null) {
                return;
            }

            Contract newContract = em.find(Contract.class, configurationDTO.getContractCode());

            c.setDescription(configurationDTO.getDescription());
            c.setVersion(configurationDTO.getVersion());
            c.setDbServerIp(configurationDTO.getDbServerIp());
            c.setApplicationServerIp(configurationDTO.getApplicationServerIp());

            //Vai buscar software antigo para trocar pelo novo
            Software oldSoftware = c.getSoftware();
            oldSoftware.removeConfiguration(c);

            c.setSoftware(newSoftware);

            newSoftware.addConfiguration(c);

            //Vai buscar o contrato antigo para trocar pelo novo
            Contract oldContract = c.getContract();

            oldContract.removeConfiguration(c);

            newContract.addConfiguration(c);

            c.setContract(newContract);

            if (oldSoftware.getCode() != newSoftware.getCode()) {
                for (Extension e : c.getExtensions()) {
                    e.removeConfiguration(c);
                }

                for (ConfigurationModule m : c.getModules()) {
                    m.removeConfiguration(c);
                }

                c.setExtensions(new LinkedList<>());
                c.setModules(new LinkedList<>());
            }

            this.sendEmail(c.getClient().getUsername(), c, "update");

        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

    public Configuration create(int code, String description, int software_code, int contract_code, String version, String client_username, String dbServerIp, String appServerIp) {
        try {
            Configuration configuration = em.find(Configuration.class, code);

            if (configuration != null) {
                return null;
            }

            Software software = em.find(Software.class, software_code);

            if (software == null) {
                return null;
            }

            Contract contract = em.find(Contract.class, contract_code);
            if (contract == null) {
                return null;
            }

            Client client = em.find(Client.class, client_username);
            if (client == null) {
                return null;
            }

            configuration = new Configuration(code, description, software, contract, version, client, dbServerIp, appServerIp);

            software.addConfiguration(configuration);

            contract.addConfiguration(configuration);

            client.addConfiguration(configuration);

            em.persist(configuration);

            return configuration;

        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

    private List<ConfigurationDTO> configurationsListToConfigurationsListDTO(List<Configuration> configurations) {
        try {
            List<ConfigurationDTO> configurationsDTO = new LinkedList<>();

            configurations.forEach((c) -> {
                configurationsDTO.add(configurationToConfigurationtDTO(c));
            });

            return configurationsDTO;
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

    private ConfigurationDTO configurationToConfigurationtDTO(Configuration c) {
        try {
            return new ConfigurationDTO(c.getCode(),
                    c.getDescription(),
                    c.getSoftware().getCode(),
                    c.getSoftware().getName(),
                    c.getContract().getCode(),
                    c.getVersion(),
                    c.getClient().getUsername(),
                    c.getDbServerIp(),
                    c.getApplicationServerIp());
        } catch (Exception ex) {
            throw new EJBException(ex.getMessage());
        }
    }

    public void sendEmail(String username, Configuration config, String action) throws EntityDoesNotExistsException {
        try {
            Client c = em.find(Client.class, username);

            if (c == null) {
                throw new EntityDoesNotExistsException("ERROR: because doesn't exists any client with the username: " + c.getUsername());
            }

            String msg = "";
            String subject = "";
            if (action.equalsIgnoreCase("create")) {
                subject = "New configuration created";
                msg = "A new configuration with the code: " + config.getCode() + " for the software " + config.getSoftware().getName() + " was add to your list of configurations. \nPlease go to the platform to see more information.";
            } else if (action.equalsIgnoreCase("update")) {
                subject = "Configuration Updated";
                msg = "The configuration with code: " + config.getCode() + " for the software " + config.getSoftware().getName() + " was updated. \nPlease go to the platform to see more information.";
            }

            emailBean.send(
                    c.getEmail(),
                    subject,
                    msg);

        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
}
