/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejbs;

import dtos.SoftwareDTO;
import entities.Software;
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
@Path("/softwares")
public class SoftwareBean {

    @PersistenceContext
    EntityManager em;

    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("/{id}")
    public SoftwareDTO getSoftwareByCode(@PathParam("id") int software_code) {
        try {
            Software software = em.find(Software.class, software_code);
            if (software == null) {
                return null;
            }

            return softwaretToSoftwareDTO(software);

        } catch (Exception ex) {
            throw new EJBException(ex.getMessage());
        }
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Path("versions/{id}")
    public String getSoftwareVersions(@PathParam("id") int software_code) {
        try {
            Software software = em.find(Software.class, software_code);
            if (software == null) {
                return null;
            }

            return software.getVersions().toString();

        } catch (Exception ex) {
            throw new EJBException(ex.getMessage());
        }
    }    

    public void create(int code, String name, String description) {
        try {
            Software software = em.find(Software.class, code);
            if (software != null) {
                return;
                //throw new EntityExistsException("Can't create student. The username already exists on database");
            }

            software = new Software(code, name, description);

            em.persist(software);

        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

    public void addVersionToSoftware(int software_code, String version) {
        try {

            Software software = em.find(Software.class, software_code);

            if (software == null || software.getVersions().contains(version)) {
                return;
            }

            software.addVersion(version);

            em.merge(software);


        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

    private SoftwareDTO softwaretToSoftwareDTO(Software s) {
        try {
            return new SoftwareDTO(s.getCode(),
                    s.getName(),
                    s.getDescription());
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
}
