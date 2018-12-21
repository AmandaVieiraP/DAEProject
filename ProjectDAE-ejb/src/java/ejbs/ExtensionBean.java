/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejbs;

import dtos.ExtensionDTO;
import dtos.SoftwareDTO;
import entities.Configuration;
import entities.ConfigurationSuper;
import entities.Extension;
import entities.Software;
import entities.Template;
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
    @Path("all")
    public List<ExtensionDTO> getAll() {
        try {
            List<Extension> extensions = em.createNamedQuery("getAllExtensions").getResultList();
            return extensionListToExtensionDTOList(extensions);
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    
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

    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("configurations/{id}")
    public List<ExtensionDTO> getExtensionByTemplateCode(@PathParam("id") int code) {
        try {
            ConfigurationSuper conf = em.find(ConfigurationSuper.class, code);

            if (conf == null) {
                return null;
            }

            return extensionListToExtensionDTOList(conf.getExtensions());

        } catch (Exception ex) {
            throw new EJBException(ex.getMessage());
        }
    }

    private List<ExtensionDTO> extensionListToExtensionDTOList(List<Extension> extensions) {
        try {
            List<ExtensionDTO> extensionsDTO = new LinkedList<>();

            extensions.forEach((e) -> {
                extensionsDTO.add(extensionToExtensiontDTO(e));
            });

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
                    e.getSoftware().getName(),
                    e.getVersion());
        } catch (Exception ex) {
            throw new EJBException(ex.getMessage());
        }
    }

    public void create(int code, String name, String description, int softwareCode, String version) {
        try {
            Extension extension = em.find(Extension.class, code);
            if (extension != null) {
                return;
            }

            Software software = em.find(Software.class, softwareCode);

            if (software == null) {
                return;
            }

            extension = new Extension(code, name, description, software, version);

            software.addExtension(extension);

            em.persist(extension);

        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
}
