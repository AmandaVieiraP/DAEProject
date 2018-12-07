/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejbs;

import dtos.ContractDTO;
import entities.Contract;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author Iolanda
 */
@Stateless
@Path("/contracts")
public class ContractBean {

    @PersistenceContext
    EntityManager em;
    
    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("/{id}")
    public ContractDTO getContractByCode(@PathParam("id") int contract_code) {
        try {
            Contract contract = em.find(Contract.class, contract_code);
            if (contract == null) {
                return null;
            }
            return null;
            
            //return contractToContractDTO(contract);

        } catch (Exception ex) {
            throw new EJBException(ex.getMessage());
        }
    }

    public void create(int code, int totalMaintenanceHoursPerMonth, double price, int durationInYears, String maintenanceSchedule, double adicionalPricePerHour) {
        try {
            Contract contract = em.find(Contract.class, code);
            if (contract != null) {
                return;
                //throw new EntityExistsException("Can't create student. The username already exists on database");
            }

            //contract = new Contract(code, totalMaintenanceHoursPerMonth, price, durationInYears, maintenanceSchedule, adicionalPricePerHour);

            em.persist(contract);

        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

    /*private ContractDTO contractToContractDTO(Contract c) {
        try {
            return new ContractDTO(c.getCode(),
                    c.getTotalMaintenanceHoursPerMonth(),
                    c.getPrice(),
                    c.getDurationInYears(),
                    c.getMaintenanceSchedule(),
                    c.getAdicionalPricePerHour());
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }*/
}
