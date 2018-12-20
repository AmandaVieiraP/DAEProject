/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejbs;

import dtos.TemplateDTO;
import entities.Contract;
import entities.Extension;
import entities.Software;
import entities.SoftwareModule;
import entities.Template;
import exceptions.EntityExistsException;
import java.util.LinkedList;
import java.util.List;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author Iolanda
 */
@Stateless
@Path("/templates")
public class TemplateBean {

    @PersistenceContext
    EntityManager em;

    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("all")
    public List<TemplateDTO> getAll() {
        try {
            Query query = em.createNamedQuery("getAllTemplates");

            List<Template> templates = query.getResultList();

            return templateListToTemplatesDTOList(templates);

        } catch (Exception ex) {
            throw new EJBException(ex.getMessage());
        }
    }

    @POST
    @Path("/create")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(TemplateDTO temp) throws EntityExistsException {
        try {
            Template template = em.find(Template.class, temp.getCode());
            if (template != null) {
                throw new EntityExistsException("ERROR: Can't create new template because already exists a template with the code: " + temp.getCode());
            }

            Software software = em.find(Software.class, temp.getSoftwareCode());

            if (software == null) {
                return;
                // throw new EntityDoesNotExistException("The course does not exists");
            }

            Contract contract = em.find(Contract.class, temp.getContractCode());
            if (contract == null) {
                return;
            }

            template = new Template(temp.getCode(), temp.getDescription(), software, contract, temp.getVersion());

            software.addConfiguration(template);

            contract.addConfiguration(template);

            em.persist(template);
        } catch (EntityExistsException e) {
            throw e;
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

    @POST
    @Path("/{templateId}/extension/{extensionId}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void associateExtensionToTemplateRest(@PathParam("extensionId") int extensionCode, @PathParam("templateId") int templateCode) {
        try {

            System.out.println("recebido extensionId: " + extensionCode + " templateId: " + templateCode);

            Extension extension = em.find(Extension.class, extensionCode);
            Template template = em.find(Template.class, templateCode);

            if (extension == null || template == null) {
                return;
            }

            if (template.getExtensions().contains(extension)) {
                return;
            }

            template.addExtension(extension);
            extension.addConfiguration(template);

            em.merge(template);
            em.merge(extension);

        } catch (Exception ex) {
            throw new EJBException(ex.getMessage());
        }
    }

    //Para usar no config Bean para popular a BD
    public void create(int code, String description, int software_code, int contract_code, String version) {
        try {
            Template template = em.find(Template.class, code);

            if (template != null) {
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

            template = new Template(code, description, software, contract, version);

            software.addConfiguration(template);

            contract.addConfiguration(template);

            em.persist(template);

        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

    /*public void associateExtensionToTemplate(int extensionCode, int templateCode) {
        try {
            Extension extension = em.find(Extension.class, extensionCode);
            Template template = em.find(Template.class, templateCode);

            if (extension == null || template == null) {
                return;
            }

            if (template.getExtensions().contains(extension)) {
                return;
            }

            template.addExtension(extension);
            extension.addConfiguration(template);

            em.merge(template);
            em.merge(extension);
        } catch (Exception ex) {
            throw new EJBException(ex.getMessage());
        }
    }*/

    public void associateModuleToTemplate(int moduleCode, int templateCode) {
        try {
            SoftwareModule module = em.find(SoftwareModule.class, moduleCode);
            Template template = em.find(Template.class, templateCode);

            if (module == null || template == null) {
                return;
            }

            if (template.getModules().contains(module)) {
                return;
            }

            template.addModule(module);
            module.addTemplate(template);

            em.merge(template);
            em.merge(module);
        } catch (Exception ex) {
            throw new EJBException(ex.getMessage());
        }
    }

    private List<TemplateDTO> templateListToTemplatesDTOList(List<Template> templates) {
        try {
            List<TemplateDTO> templatesDTO = new LinkedList<>();

            for (Template t : templates) {
                templatesDTO.add(templateToTemplatetDTO(t));
            }

            return templatesDTO;
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

    private TemplateDTO templateToTemplatetDTO(Template t) {
        try {
            return new TemplateDTO(t.getCode(),
                    t.getDescription(),
                    t.getSoftware().getCode(),
                    t.getSoftware().getName(),
                    t.getContract().getCode(),
                    t.getVersion());
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

}
