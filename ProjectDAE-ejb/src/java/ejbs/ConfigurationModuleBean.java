/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejbs;

import dtos.ConfigurationModuleDTO;
import entities.Configuration;
import entities.ConfigurationModule;
import entities.Software;
import java.util.LinkedList;
import java.util.List;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
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
@Path("/configurationModules")
public class ConfigurationModuleBean {

    @PersistenceContext
    EntityManager em;

    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("all")
    public List<ConfigurationModuleDTO> getAll() {
        try {
            List<ConfigurationModule> configurationModules = em.createNamedQuery("getAllConfigurationModules").getResultList();

            return configurationModuleListToConfigurationModuleDTOList(configurationModules);
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("{id}")
    public List<ConfigurationModuleDTO> getConfigurationModuleByConfigurationCode(@PathParam("id") int configurationCode) {
        try {
            Configuration configuration = em.find(Configuration.class, configurationCode);

            if (configuration == null) {
                return null;
            }

            return configurationModuleListToConfigurationModuleDTOList(configuration.getModules());

        } catch (Exception ex) {
            throw new EJBException(ex.getMessage());
        }
    }

    @PUT
    @Path("/associateModuleConfigurations/{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void associateConfigurationModuleRest(@PathParam("id") int code, ConfigurationModuleDTO configurationModule) {
        try {

            associateModuleToConfiguration(configurationModule.getCode(), code);

        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

    @PUT
    @Path("/dissociateModuleConfigurations/{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void dissociateConfigurationModuleRest(@PathParam("id") int code, ConfigurationModuleDTO configurationModule) {
        try {

            dissociateModuleToConfiguration(configurationModule.getCode(), code);

        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

    private List<ConfigurationModuleDTO> configurationModuleListToConfigurationModuleDTOList(List<ConfigurationModule> modules) {
        try {
            List<ConfigurationModuleDTO> configurationModulesDTO = new LinkedList<>();

            for (ConfigurationModule c : modules) {
                configurationModulesDTO.add(configurationModuleToConfigurationModuletDTO(c));
            }

            return configurationModulesDTO;
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

    private ConfigurationModuleDTO configurationModuleToConfigurationModuletDTO(ConfigurationModule c) {
        try {
            return new ConfigurationModuleDTO(
                    c.getCode(),
                    c.getDescription(),
                    c.getSoftware().getCode(),
                    c.getSoftware().getName(),
                    c.getVersion());
        } catch (Exception ex) {
            throw new EJBException(ex.getMessage());
        }
    }

    public void create(int code, String description, int softwareCode, String version) {
        try {
            ConfigurationModule configurationModule = em.find(ConfigurationModule.class, code);
            if (configurationModule != null) {
                return;
                //throw new EntityExistsException("Can't create student. The username already exists on database");
            }

            Software software = em.find(Software.class, softwareCode);

            if (software == null) {
                return;
                // throw new EntityDoesNotExistException("The course does not exists");
            }

            configurationModule = new ConfigurationModule(code, description, software, version);

            software.addModule(configurationModule);

            em.persist(configurationModule);

        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

    public void associateModuleToConfiguration(int moduleCode, int configCode) {
        try {
            ConfigurationModule module = em.find(ConfigurationModule.class, moduleCode);
            Configuration configuration = em.find(Configuration.class, configCode);

            if (module == null || configuration == null) {
                return;
            }

            if (configuration.getModules().contains(module)) {
                return;
            }

            configuration.addModule(module);
            module.addConfiguration(configuration);

            em.merge(configuration);
            em.merge(module);
        } catch (Exception ex) {
            throw new EJBException(ex.getMessage());
        }
    }

    public void dissociateModuleToConfiguration(int moduleCode, int configCode) {
        try {
            ConfigurationModule module = em.find(ConfigurationModule.class, moduleCode);
            Configuration configuration = em.find(Configuration.class, configCode);

            if (module == null || configuration == null) {
                return;
            }

            if (!configuration.getModules().contains(module)) {
                return;
            }

            configuration.removeModule(module);
            module.removeConfiguration(configuration);

            em.merge(configuration);
            em.merge(module);
        } catch (Exception ex) {
            throw new EJBException(ex.getMessage());
        }
    }
}
