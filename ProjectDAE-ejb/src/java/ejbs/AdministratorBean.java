/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejbs;

import entities.Administrator;
import exceptions.EntityExistsException;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Amanda
 */
@Stateless
public class AdministratorBean {

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @PersistenceContext
    EntityManager em;

    public void create(String username, String password, String name, String email, String jobRole) throws EntityExistsException {
        try {
            // verificar se existe e lançar exception
            Administrator a = em.find(Administrator.class, username);
            if (a != null) {
                throw new EntityExistsException("ERROR: Can't create new administrator because already exists a administrator with the username: " + username);
            }

            Administrator administrator = new Administrator(username, password, name, email, jobRole);
            em.persist(administrator);

        } catch (EntityExistsException e) {
            throw e;
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

    /*
    public void create (String username, String password, String name, String email) {
        try {
            Administrator admin = new Administrator(username, password, name, email);
            em.persist(admin);             
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
     */
}
