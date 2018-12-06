/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejbs;

import dtos.TemplateDTO;
import entities.Software;
import entities.Contract;
import entities.Template;
import entities.Template.STATE;
import exceptions.EntityExistsException;
import java.util.LinkedList;
import java.util.List;
import javax.annotation.security.PermitAll;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.validation.ConstraintViolationException;
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

    public void create(int code, String description, STATE state, int software_code, String version, int contract_code, String repository) {
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

            template = new Template(code, description, state, software, version, contract, repository);
            //Adiciona estudante ao curso
            software.addTemplate(template);

            contract.addTemplate(template);

            em.persist(template);

        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

    private List<TemplateDTO> templateListToTemplatesDTOList(List<Template> templates) {
        try {
            List<TemplateDTO> templatesDTO = new LinkedList<>();

            for (Template t : templates) {
                templatesDTO.add(studentToStudentDTO(t));
            }

            return templatesDTO;
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

    private TemplateDTO studentToStudentDTO(Template t) {
        try {
            return new TemplateDTO(t.getCode(),
                    t.getDescription(),
                    t.getState().toString(),
                    t.getSoftware().getCode(),
                    t.getContract().getCode(),
                    t.getVersion(),
                    t.getArtefactsRepository());
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

}
