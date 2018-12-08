/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejbs;

import entities.Contract;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Iolanda
 */
@Stateless
public class ContractBean {

    @PersistenceContext
    EntityManager em;

    public void create(int code) {
        try {
            Contract contract = em.find(Contract.class, code);
            if (contract != null) {
                return;
                //throw new EntityExistsException("Can't create student. The username already exists on database");
            }

            contract = new Contract(code);

            em.persist(contract);

        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

    /*public void associateParameterToContract(int contractCode, String parameterName) {
         try {
            Contract contract = em.find(Contract.class, contractCode);
            ContractParameter contractParameter = em.find(ContractParameter.class, parameterName);

            if (contract == null || contractParameter == null) {
                return;
            }
            
            if (contractParameter.getContracts().contains(contract)) {
                return;
            }
           

            //Adiciona disciplina ao estudante
            contractParameter.addContract(contract);

            //Adiciona estudante à disciplina
            contract.addParameter(contractParameter);

            em.merge(contractParameter);
            em.merge(contract);
        } catch (Exception ex) {
            throw new EJBException(ex.getMessage());
        }
    }*/
}
