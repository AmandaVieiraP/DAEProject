/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejbs;

import dtos.ClientDTO;
import entities.Client;
import entities.Configuration;
import exceptions.EntityDoesNotExistsException;
import exceptions.EntityExistsException;
import java.util.LinkedList;
import java.util.List;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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
 * @author Amanda
 */
@Stateless
@Path("/clients")
public class ClientBean {

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @PersistenceContext
    EntityManager em;

    public void create(String username, String email, String password, String address, String companyName, String contactPerson) throws EntityExistsException {
        try {
            Client c = em.find(Client.class, username);

            if (c != null) {
                throw new EntityExistsException("ERROR: Can't create new client because already exists a client with the username: " + username);
            }

            Client client = new Client(username, email, password, address, companyName, contactPerson);
            em.persist(client);
        } catch (EntityExistsException e) {
            throw e;
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

    @POST
    @RolesAllowed({"Administrator"})
    @Path("/create")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(ClientDTO client) throws EntityExistsException {
        try {
            Client cl = em.find(Client.class, client.getUsername());
            if (cl != null) {
                throw new EntityExistsException("ERROR: Can't create new client because already exists a client with the username: " + client.getUsername());
            }

            Client c = new Client(client.getUsername(), client.getEmail(), client.getPassword(), client.getAddress(), client.getCompanyName(), client.getContactPerson());
            em.persist(c);
        } catch (EntityExistsException e) {
            throw e;
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

    @GET
    @RolesAllowed({"Administrator","Client"})
    @Path("/{username}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public ClientDTO getClient(@PathParam("username") String username) {
        try {
            Client c = em.find(Client.class, username);
            if (c == null) {
                return null;
            }

            return clientToDTO(c);
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

    @GET
    @RolesAllowed({"Administrator"})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("all")
    public List<ClientDTO> getAll() {
        try {
            // o EntityManager é que sabe como pegar todos os students 
            // este bean vai ao entitymanager que depois vai à BD buscar os dados 
            // o entitymanager sabe que tem que ir à entity Student pois é essa a entidade que tem a NamedQuery getAllStudents 
            // não pode haver duas entidades com NamedQuery iguais 
            List<Client> clients = em.createNamedQuery("getAllClients").getResultList();
            return clientsToDTOs(clients);
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

    @DELETE
    @RolesAllowed({"Administrator"})
    @Path("{username}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void remove(@PathParam("username") String username) {
        try {
            Client client = (Client) em.find(Client.class, username);

            if (client == null) {
                throw new EntityDoesNotExistsException("ERROR: Can't delete that client because doesn't exists a client with the username: " + username);
            }
            
            em.remove(client);
        } catch (Exception ex) {
            throw new EJBException(ex.getMessage());
        }
    }

    @PUT
    @RolesAllowed({"Administrator"})
    @Path("/update")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void updateRest(ClientDTO client) throws EntityDoesNotExistsException {
        try {
            Client c = em.find(Client.class, client.getUsername());

            if (c == null) {
                throw new EntityDoesNotExistsException("ERROR: Can't update the client because doesn't exists any client with the username: " + client.getUsername());
            }

            c.setUsername(client.getUsername());
            c.setAddress(client.getAddress());
            c.setCompanyName(client.getCompanyName());
            c.setEmail(client.getEmail());
            c.setContactPerson(client.getContactPerson());

        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

    public ClientDTO clientToDTO(Client c) {
        return new ClientDTO(c.getAddress(), c.getCompanyName(), c.getEmail(), c.getContactPerson(), c.getUsername(), null);
    }

    public List<ClientDTO> clientsToDTOs(List<Client> clients) {
        List<ClientDTO> clientsDTO = new LinkedList<ClientDTO>();

        for (Client c : clients) {
            clientsDTO.add(clientToDTO(c));
        }

        return clientsDTO;
    }

}
