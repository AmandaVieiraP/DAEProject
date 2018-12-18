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
import java.util.LinkedList;
import java.util.List;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
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

    public void create(int code, String description, int software_code, int contract_code, String version) {
        try {
            Template template = em.find(Template.class, code);

            if (template != null) {
                return;
                //throw new EntityExistsException("Can't create student. The username already exists on database");
            }

            Software software = em.find(Software.class, software_code);

            if (software == null) {
                return;
                // throw new EntityDoesNotExistException("The course does not exists");
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

    public void associateExtensionToTemplate(int extensionCode, int templateCode) {
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
    }

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