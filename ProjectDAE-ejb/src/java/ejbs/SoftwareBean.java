/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejbs;

import entities.Software;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Iolanda
 */
@Stateless
@LocalBean
public class SoftwareBean {

    @PersistenceContext
    EntityManager em;
    
    public void create(int code, String name) {
        try {
            Software software = em.find(Software.class, code);
            if (software != null) {
                return;
                //throw new EntityExistsException("Can't create student. The username already exists on database");
            }

            //software=new Software(code,name);

            em.persist(software);
            
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
}
