/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejbs;

import dtos.ArtefactDTO;
import entities.Artefact;
import entities.ConfigurationSuper;
import java.util.LinkedList;
import java.util.List;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author Iolanda
 */
@Stateless
@Path("/artefacts")
public class ArtefactBean {

    @PersistenceContext
    EntityManager em;

    public void create(String filename, String mimetype) {
        try {
            Artefact artefact = em.find(Artefact.class, filename);
            if (artefact != null) {
                return;
                //throw new EntityExistsException("Can't create student. The username already exists on database");
            }

            artefact = new Artefact(filename, mimetype);

            em.persist(artefact);

        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("{id}")
    public List<ArtefactDTO> getConfigurationArtefactsRepository(@PathParam("id") int configCode) {
        try {
            ConfigurationSuper configurationSuper = em.find(ConfigurationSuper.class, configCode);
            if (configurationSuper == null) {
                return null;
            }

            return artefactListToArtefactsDTOList(configurationSuper.getArtefactsRepository());

        } catch (Exception ex) {
            throw new EJBException(ex.getMessage());
        }
    }

    private List<ArtefactDTO> artefactListToArtefactsDTOList(List<Artefact> artefacts) {
        try {
            List<ArtefactDTO> artefactsDTO = new LinkedList<>();

            for (Artefact a : artefacts) {
                artefactsDTO.add(artefactToArtefactDTO(a));
            }

            return artefactsDTO;
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

    private ArtefactDTO artefactToArtefactDTO(Artefact a) {
        try {
            return new ArtefactDTO(a.getFilename(),
                    a.getMimetype());
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

}
