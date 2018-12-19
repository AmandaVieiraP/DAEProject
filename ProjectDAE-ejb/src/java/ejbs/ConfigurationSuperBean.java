/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejbs;

import dtos.ArtefactDTO;
import dtos.HelpMaterialDTO;
import entities.Artefact;
import entities.ConfigurationSuper;
import entities.HelpMaterial;
import java.util.LinkedList;
import java.util.List;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.GET;
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
public class ConfigurationSuperBean {

    @PersistenceContext
    EntityManager em;

    /*@GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("artefacts/{id}")
    public List<ArtefactDTO> getConfigurationArtefactsRepository(@PathParam("id") int configCode) {
        try {
            ConfigurationSuper configurationSuper = em.find(ConfigurationSuper.class, configCode);
            if (configurationSuper == null) {
                return null;
            }

            return configurationSuper.getArtefactsRepository().toString();

        } catch (Exception ex) {
            throw new EJBException(ex.getMessage());
        }
    }*/
 /*
    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("softwares/{id}")
    public List<ExtensionDTO> getExtensionBySoftwareCode(@PathParam("id") int software_code) {
        try {
            Query query = em.createNamedQuery("getAllExtensionsBySoftware");

            query.setParameter(1, software_code);

            List<Extension> extensions = query.getResultList();

            return extensionListToExtensionDTOList(extensions);

        } catch (Exception ex) {
            throw new EJBException(ex.getMessage());
        }
    }*/
    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("helpMaterials/{id}")
    public List<HelpMaterialDTO> getConfigurationHelpMaterials(@PathParam("id") int configCode) {
        try {
            ConfigurationSuper configurationSuper = em.find(ConfigurationSuper.class, configCode);
            if (configurationSuper == null) {
                return null;
            }

            return helpMaterialListToHelpMaterialDTOList(configurationSuper.getHelpMaterials());

        } catch (Exception ex) {
            throw new EJBException(ex.getMessage());
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

    private List<HelpMaterialDTO> helpMaterialListToHelpMaterialDTOList(List<HelpMaterial> helpMaterials) {
        try {
            List<HelpMaterialDTO> helpMaterialsDTO = new LinkedList<>();

            for (HelpMaterial h : helpMaterials) {
                helpMaterialsDTO.add(helpMaterialToHelpMaterialDTO(h));
            }

            return helpMaterialsDTO;
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

    private HelpMaterialDTO helpMaterialToHelpMaterialDTO(HelpMaterial h) {
        try {
            return new HelpMaterialDTO(h.getFilename(),h.getMimetype());
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
}
