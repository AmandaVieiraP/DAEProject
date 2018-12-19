/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejbs;

import dtos.HelpMaterialDTO;
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
@Path("/helpMaterials")
public class HelpMaterialBean {

    @PersistenceContext
    EntityManager em;
    
    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("{id}")
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


    public void create(String filename, String mimetype) {
        try {
            HelpMaterial helpMaterial = em.find(HelpMaterial.class, filename);

            if (helpMaterial != null) {
                return;
                //throw new EntityExistsException("Can't create student. The username already exists on database");
            }

            helpMaterial = new HelpMaterial(filename,mimetype);

            em.persist(helpMaterial);

        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
}
