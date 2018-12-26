/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejbs;

import dtos.ClientDTO;
import dtos.ExtensionDTO;
import dtos.QuestionDTO;
import dtos.TemplateDTO;
import entities.Client;
import entities.Configuration;
import entities.Extension;
import entities.Question;
import entities.Template;
import exceptions.EntityDoesNotExistsException;
import exceptions.EntityExistsException;
import java.util.LinkedList;
import java.util.List;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author IvoSantos
 */
@Stateless
@Path("/questions")
public class QuestionBean {

    @PersistenceContext
    EntityManager em;
    
    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("all")
    public List<QuestionDTO> getAll() {
        try {
            Query query = em.createNamedQuery("getAllQuestions");

            List<Question> questions = query.getResultList();

            return questionsToDTOs(questions);

        } catch (Exception ex) {
            throw new EJBException(ex.getMessage());
        }
    }
    
    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("configuration/{id}")
    public List<QuestionDTO> getAllFromOneConfiguration(@PathParam("id") int code) {
        try {
            Configuration c = em.find(Configuration.class, code);
            
            if(c == null)
                return null;
            
            Query query = em.createNamedQuery("getAllQuestionsByConfiguration");

            query.setParameter(1, c.getCode());

            List<Question> questions = query.getResultList();
            
            return questionsToDTOs(questions);
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    @POST
    @Path("create")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void createREST( QuestionDTO questionDTO) {
        try {
            
            Configuration c = em.find(Configuration.class, questionDTO.getConfigurationCode());

            if (c == null) {
                return;
            }

            //int id, String questionSender, String question,Configuration configuration) {
            Question question = new Question(questionDTO.getQuestionSender(),questionDTO.getQuestion(),c);

            em.persist(question);
            c.addQuestions(question);

        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

    public void create(int id, String questionSender, String questionMsg,int configurationCode) throws EntityExistsException {
        try {
              
            Question q = em.find(Question.class, id);

            if (q != null) {
                throw new EntityExistsException("ERROR: Can't create new question because already exists a question with the id: " + q);
            }

            Configuration c = em.find(Configuration.class, configurationCode);

            if (c == null) {
                return;
            }

            Question question = new Question(id,questionSender,questionMsg,c);

            em.persist(question);
        } catch (EntityExistsException e) {
            throw e;
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

   

    public QuestionDTO questionToDTO(Question q) {
        return new QuestionDTO(q.getId(),q.getQuestionSender(),q.getQuestion(),q.getConfiguration().getCode());
    }

    public List<QuestionDTO> questionsToDTOs(List<Question> questions) {
        List<QuestionDTO> questionsDTO = new LinkedList<QuestionDTO>();

        for (Question q : questions) {
            questionsDTO.add(questionToDTO(q));
        }

        return questionsDTO;
    }
}
