/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejbs;

import dtos.SoftwareModuleDTO;
import entities.Software;
import entities.SoftwareModule;
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
@Path("/softwareModules")
public class SoftwareModuleBean {

    @PersistenceContext
    EntityManager em;

    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("softwares/{id}")
    public List<SoftwareModuleDTO> getSoftwareModuleBySoftwareCode(@PathParam("id") int software_code) {
        try {
            Query query = em.createNamedQuery("getAllSoftwareModulesBySoftware");

            query.setParameter(1, software_code);

            List<SoftwareModule> softwareModules = query.getResultList();

            return softwareModuleListToSoftwareModuleDTOList(softwareModules);

        } catch (Exception ex) {
            throw new EJBException(ex.getMessage());
        }
    }

    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("templates/{id}")
    public List<SoftwareModuleDTO> getSoftwareModuleByTemplateCode(@PathParam("id") int template_code) {
        try {
            Template template = em.find(Template.class, template_code);

            if (template == null) {
                return null;
            }

            return softwareModuleListToSoftwareModuleDTOList(template.getModules());

        } catch (Exception ex) {
            throw new EJBException(ex.getMessage());
        }
    }

    private List<SoftwareModuleDTO> softwareModuleListToSoftwareModuleDTOList(List<SoftwareModule> softwareModules) {
        try {
            List<SoftwareModuleDTO> softwareModulesDTO = new LinkedList<>();

            for (SoftwareModule m : softwareModules) {
                softwareModulesDTO.add(softwareModuleToSoftwareModuletDTO(m));
            }

            return softwareModulesDTO;
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

    private SoftwareModuleDTO softwareModuleToSoftwareModuletDTO(SoftwareModule m) {
        try {
            return new SoftwareModuleDTO(m.getCode(),
                    m.getDescription(),
                    m.getSoftware().getCode(),
                    m.getSoftware().getName());
        } catch (Exception ex) {
            throw new EJBException(ex.getMessage());
        }
    }

    public void create(int code, String description, int softwareCode, String softwareName) {
        try {
            SoftwareModule softwareModule = em.find(SoftwareModule.class, code);
            if (softwareModule != null) {
                return;
                //throw new EntityExistsException("Can't create student. The username already exists on database");
            }

            Software software = em.find(Software.class, softwareCode);

            if (software == null) {
                return;
                // throw new EntityDoesNotExistException("The course does not exists");
            }

            softwareModule = new SoftwareModule(code, description, software);

            software.addModule(softwareModule);

            em.persist(softwareModule);

        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
}
