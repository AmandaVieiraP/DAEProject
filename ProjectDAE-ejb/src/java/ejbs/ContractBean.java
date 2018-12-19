/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejbs;

import dtos.ContractDTO;
import dtos.SoftwareDTO;
import entities.Contract;
import entities.Software;
import java.util.LinkedList;
import java.util.List;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
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
    
    @GET
    //@RolesAllowed({"Administrator"})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("all")
    public List<ContractDTO> getAll() {
        try {
              // o EntityManager é que sabe como pegar todos os students 
              // este bean vai ao entitymanager que depois vai à BD buscar os dados 
              // o entitymanager sabe que tem que ir à entity Student pois é essa a entidade que tem a NamedQuery getAllStudents 
              // não pode haver duas entidades com NamedQuery iguais 
            List<Contract> contracts = em.createNamedQuery("getAllContracts").getResultList();
            return contractsToDTOs(contracts);
        } catch(Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    private List<ContractDTO> contractsToDTOs(List<Contract> contracts) {
       List<ContractDTO> contractsDTO = new LinkedList<ContractDTO>(); 
        
        for(Contract c : contracts) {
            contractsDTO.add(contractToDTO(c));
        }

        return contractsDTO;
    }
    
    public ContractDTO contractToDTO(Contract contract) {
        return new ContractDTO(contract.getCode());
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
