/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejbs;

import dtos.AnswerDTO;
import entities.Answer;
import entities.Question;
import exceptions.EntityExistsException;
import java.util.LinkedList;
import java.util.List;
import javax.annotation.security.RolesAllowed;
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
 * @author IvoSantos
 */
@Stateless
@Path("/answers")

public class AnswerBean {

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @PersistenceContext
    EntityManager em;

    public void create(int id, String answerSender, String answerMsg, int questionCode) throws EntityExistsException {
        try {
            Answer a = em.find(Answer.class, id);

            if (a != null) {
                throw new EntityExistsException("ERROR: Can't create new Answer because already exists a Answer with the id: " + a);
            }
            Question q = em.find(Question.class, questionCode);
            if (q == null) {
                return;
                // throw new EntityDoesNotExistException("The course does not exists");
            }

            Answer answer = new Answer(id, answerSender, answerMsg, q);
            em.persist(answer);
        } catch (EntityExistsException e) {
            throw e;
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

    @POST
    @Path("create")
    @RolesAllowed({"Administrator","Client"})
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void createREST(AnswerDTO answerDTO) {
        try {

            Question q = em.find(Question.class, answerDTO.getQuestionCode());

            if (q == null) {
                return;
            }

            //int id, String questionSender, String question,Configuration configuration) {
            Answer answer = new Answer(answerDTO.getAnswerSender(), answerDTO.getAnswer(), q);

            em.persist(answer);
            q.addAnswer(answer);

        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("question/{id}")
    @RolesAllowed({"Administrator","Client"})
    public List<AnswerDTO> getAllFromOneQuestion(@PathParam("id") int code) {
        try {
            Question q = em.find(Question.class, code);

            if (q == null) {
                return null;
            }

            Query query = em.createNamedQuery("getAllAnswersByQuestion");

            query.setParameter(1, q.getId());

            List<Answer> answers = query.getResultList();

            return answersToDTOs(answers);
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

    public AnswerDTO answerToDTO(Answer a) {
        return new AnswerDTO(a.getId(), a.getAnswerSender(), a.getAnswer(), a.getQuestion().getId());
    }

    public List<AnswerDTO> answersToDTOs(List<Answer> answers) {
        List<AnswerDTO> answersDTO = new LinkedList<AnswerDTO>();

        for (Answer a : answers) {
            answersDTO.add(answerToDTO(a));
        }

        return answersDTO;
    }
}
