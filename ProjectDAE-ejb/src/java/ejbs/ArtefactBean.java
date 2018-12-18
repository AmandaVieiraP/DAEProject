/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejbs;

import entities.Artefact;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Iolanda
 */
@Stateless
public class ArtefactBean {  
    @PersistenceContext
    EntityManager em;

    public void create(String filename,String mimetype) {
        try {
            Artefact artefact = em.find(Artefact.class, filename);
            if (artefact != null) {
                return;
                //throw new EntityExistsException("Can't create student. The username already exists on database");
            }

            artefact = new Artefact(filename,mimetype);

            em.persist(artefact);

        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

}
