/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejbs;

import entities.Client;
import entities.Configuration;
import entities.Contract;
import entities.Software;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Path;

/**
 *
 * @author Iolanda
 */
@Stateless
@Path("/client_configurations")
public class ConfigurationBean {

    @PersistenceContext
    EntityManager em;
    
    public void create(int code, String description, int software_code, int contract_code, String version, String client_username) {
        try {
            Configuration configuration = em.find(Configuration.class, code);

            if (configuration != null) {
                return;
                //throw new EntityExistsException("Can't create configuration. The code already exists on database");
            }

            Software software = em.find(Software.class, software_code);

            if (software == null) {
                return;
                // throw new EntityDoesNotExistException("The software does not exists");
            }

            Contract contract = em.find(Contract.class, contract_code);
            if (contract == null) {
                return;
                // throw new EntityDoesNotExistException("The contract does not exists");
            }
            
            Client client = em.find(Client.class, client_username);
            if (client == null) {
                return;
                // throw new EntityDoesNotExistException("The client does not exists");
            }
            
            configuration = new Configuration(code, description, software, contract, version, client);

            software.addConfiguration(configuration);

            contract.addConfiguration(configuration);
            
            client.addConfiguration(configuration);

            em.persist(configuration);

        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
}
