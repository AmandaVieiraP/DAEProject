/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejbs;

import dtos.AdministratorDTO;
import dtos.ConfigurationDTO;
import entities.Administrator;
import entities.Artefact;
import entities.Client;
import entities.Configuration;
import entities.Contract;
import entities.Extension;
import entities.HelpMaterial;
import entities.Parameter;
import entities.Software;
import exceptions.EntityDoesNotExistsException;
import java.util.LinkedList;
import java.util.List;
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

    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
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
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void createREST(ConfigurationDTO configurationDTO) {
        try {
            this.create(configurationDTO.getCode(), configurationDTO.getDescription(), configurationDTO.getSoftwareCode(),
                    configurationDTO.getContractCode(), configurationDTO.getVersion(), configurationDTO.getClientUsername());

        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

    @DELETE
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void remove(@PathParam("id") int code) {
        try {
            Configuration configuration = em.find(Configuration.class, code);

            if (configuration == null) {
                return;
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
            
            //Vai buscar software antigo para trocar pelo novo
            Software oldSoftware=c.getSoftware();
            oldSoftware.removeConfiguration(c);
            
            c.setSoftware(newSoftware);
            
            newSoftware.addConfiguration(c);
            
            //Vai buscar o contrato antigo para trocar pelo novo
            Contract oldContract=c.getContract();
            
            oldContract.removeConfiguration(c);
            
            newContract.addConfiguration(c);
            
            c.setContract(newContract);

        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

    public void create(int code, String description, int software_code, int contract_code, String version, String client_username) {
        try {
            Configuration configuration = em.find(Configuration.class, code);

            if (configuration != null) {
                return;
            }

            Software software = em.find(Software.class, software_code);

            if (software == null) {
                return;
            }

            Contract contract = em.find(Contract.class, contract_code);
            if (contract == null) {
                return;
            }

            Client client = em.find(Client.class, client_username);
            if (client == null) {
                return;
            }

            configuration = new Configuration(code, description, software, contract, version, client);

            software.addConfiguration(configuration);

            contract.addConfiguration(configuration);

            client.addConfiguration(configuration);

            em.persist(configuration);

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
                    c.getClient().getUsername());
        } catch (Exception ex) {
            throw new EJBException(ex.getMessage());
        }
    }
}
