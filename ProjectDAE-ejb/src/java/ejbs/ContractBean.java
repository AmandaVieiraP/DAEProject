/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejbs;

import entities.Contract;
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
public class ContractBean {

    @PersistenceContext
    EntityManager em;
    
    public void create(int code,int totalMaintenanceHoursPerMonth, double price, int durationInYears, String maintenanceSchedule, double adicionalPricePerHour) {
        try {
            Contract contract = em.find(Contract.class, code);
            if (contract != null) {
                return;
                //throw new EntityExistsException("Can't create student. The username already exists on database");
            }

            contract=new Contract(code,totalMaintenanceHoursPerMonth,price,durationInYears,maintenanceSchedule,adicionalPricePerHour);

            em.persist(contract);
            
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
}
