/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejbs;

import dtos.ExtensionDTO;
import entities.Extension;
import entities.Software;
import java.util.LinkedList;
import java.util.List;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
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
@Path("/extensions")
public class ExtensionBean {

    @PersistenceContext
    EntityManager em;

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
    }

    private List<ExtensionDTO> extensionListToExtensionDTOList(List<Extension> extensions) {
        try {
            List<ExtensionDTO> extensionsDTO = new LinkedList<>();

            for (Extension e : extensions) {
                extensionsDTO.add(extensionToExtensiontDTO(e));
            }

            return extensionsDTO;
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

    private ExtensionDTO extensionToExtensiontDTO(Extension e) {
        try {
            return new ExtensionDTO(e.getCode(),
                    e.getName(),
                    e.getDescription(),
                    e.getSoftware().getCode(),
                    e.getSoftware().getName());
        } catch (Exception ex) {
            throw new EJBException(ex.getMessage());
        }
    }

    public void create(int code, String name, String description, int softwareCode) {
         try {
            Extension extension = em.find(Extension.class, code);
            if (extension != null) {
                return;
                //throw new EntityExistsException("Can't create student. The username already exists on database");
            }
            
            Software software=em.find(Software.class, softwareCode);

            if (software == null) {
                return;
                // throw new EntityDoesNotExistException("The course does not exists");
            }

            extension = new Extension(code, name,description, software);

            software.addExtension(extension);

            em.persist(extension);

        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
}
