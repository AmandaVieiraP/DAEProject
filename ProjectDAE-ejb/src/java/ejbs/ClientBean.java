/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejbs;

import dtos.AdministratorDTO;
import dtos.ClientDTO;
import entities.Administrator;
import entities.Client;
import exceptions.EntityExistsException;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author Amanda
 */
@Stateless
@Path("/clients")
public class ClientBean {

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @PersistenceContext
    EntityManager em;
    
    public void create (String username, String password, String address, String companyName, String contactPerson) throws EntityExistsException {
        try {
            Client c = em.find(Client.class, username);
                      
            if (c != null) {
                throw new EntityExistsException("ERROR: Can't create new client because already exists a client with the username: " + username);
            }
            
            Client client = new Client(username, password, address, companyName, contactPerson);
            em.persist(client);
        } catch (EntityExistsException e) {
            throw e;
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    } 
  
    
    @POST
    @Path("/create")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create (ClientDTO client) throws EntityExistsException {
        try {
            Client cl = em.find(Client.class, client.getUsername());
            if (cl != null) {
                throw new EntityExistsException("ERROR: Can't create new client because already exists a client with the username: " + client.getUsername());
            }
            
            Client c = new Client(client.getUsername(), client.getPassword(), client.getAddress(), client.getCompanyName(), client.getContactPerson());
            em.persist(c);
        } catch (EntityExistsException e) {
            throw e;
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }        
    }
    
    
}
