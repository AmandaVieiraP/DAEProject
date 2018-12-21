/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejbs;

import dtos.ArtefactDTO;
import dtos.ExtensionDTO;
import dtos.HelpMaterialDTO;
import entities.Artefact;
import entities.ConfigurationSuper;
import entities.Extension;
import entities.HelpMaterial;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author Iolanda
 */
@Stateless
@Path("/configurationsSuper")
public class ConfigurationSuperBean {

    @PersistenceContext
    EntityManager em;

    @PUT
    @Path("/associateExtensions/{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void associateExtensionRest(@PathParam("id") int code, ExtensionDTO extension) {
        try {

            associateExtensionToConfiguration(extension.getCode(), code);

        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    @PUT
    @Path("/dissociateExtensions/{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void dissociateExtensionRest(@PathParam("id") int code, ExtensionDTO extension) {
        try {

            dissociateExtensionToConfiguration(extension.getCode(), code);

        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

    @PUT
    @Path("/associateArtefacts/{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void associateArtefactRest(@PathParam("id") int code, ArtefactDTO artefactDTO) {
        try {
            Artefact artefact = em.find(Artefact.class, artefactDTO.getFilename());

            if (artefact != null) {
                addArtefactsToConfiguration(code, artefactDTO.getFilename());
                return;
            }

            artefact = new Artefact(artefactDTO.getFilename(), artefactDTO.getMimetype());

            em.persist(artefact);

            addArtefactsToConfiguration(code, artefactDTO.getFilename());

        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

    @PUT
    @Path("/associateHelpMaterials/{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void associateHelpMaterialsRest(@PathParam("id") int code, HelpMaterialDTO helpMaterialDTO) {
        try {
            HelpMaterial helpMaterial = em.find(HelpMaterial.class, helpMaterialDTO.getFilename());

            if (helpMaterial != null) {
                addHelpMaterialToConfiguration(code, helpMaterialDTO.getFilename());
                return;
            }

            helpMaterial = new HelpMaterial(helpMaterialDTO.getFilename(), helpMaterialDTO.getMimetype());

            em.persist(helpMaterial);

            addHelpMaterialToConfiguration(code, helpMaterialDTO.getFilename());

        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

    public void addHelpMaterialToConfiguration(int configCode, String filename) {
        try {

            ConfigurationSuper conf = em.find(ConfigurationSuper.class, configCode);
            HelpMaterial helpMaterial = em.find(HelpMaterial.class, filename);

            if (conf == null) {
                return;
            }

            if (helpMaterial == null) {
                return;
            }

            if (conf.getHelpMaterials().contains(helpMaterial)) {
                return;
            }

            conf.addHelpMaterial(helpMaterial);
            helpMaterial.addConfigurations(conf);

            em.merge(conf);
            em.merge(helpMaterial);

        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

    public void addArtefactsToConfiguration(int configCode, String filename) {
        try {
            ConfigurationSuper conf = em.find(ConfigurationSuper.class, configCode);
            Artefact artefact = em.find(Artefact.class, filename);

            if (conf == null) {
                return;
            }

            if (artefact == null) {
                return;
            }

            if (conf.getArtefactsRepository().contains(artefact)) {
                return;
            }

            conf.addArtefact(artefact);
            artefact.addConfigurations(conf);

            em.merge(conf);
            em.merge(artefact);

        } catch (Exception ex) {
            throw new EJBException(ex.getMessage());
        }
    }

    public void associateExtensionToConfiguration(int extensionCode, int configurationCode) {
        try {
            Extension extension = em.find(Extension.class, extensionCode);
            ConfigurationSuper configurationSuper = em.find(ConfigurationSuper.class, configurationCode);

            if (extension == null || configurationSuper == null) {
                return;
            }

            if (configurationSuper.getExtensions().contains(extension)) {
                return;
            }

            configurationSuper.addExtension(extension);
            extension.addConfiguration(configurationSuper);

            em.merge(configurationSuper);
            em.merge(extension);
        } catch (Exception ex) {
            throw new EJBException(ex.getMessage());
        }
    }
    
    public void dissociateExtensionToConfiguration(int extensionCode, int configurationCode) {
        try {
            Extension extension = em.find(Extension.class, extensionCode);
            ConfigurationSuper configurationSuper = em.find(ConfigurationSuper.class, configurationCode);

            if (extension == null || configurationSuper == null) {
                return;
            }

            if (!configurationSuper.getExtensions().contains(extension)) {
                return;
            }

            configurationSuper.removeExtension(extension);
            extension.removeConfiguration(configurationSuper);

            em.merge(configurationSuper);
            em.merge(extension);
        } catch (Exception ex) {
            throw new EJBException(ex.getMessage());
        }
    }
}
