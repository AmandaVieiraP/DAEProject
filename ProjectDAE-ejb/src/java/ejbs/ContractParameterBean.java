/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejbs;

import dtos.ContractParameterDTO;
import entities.Contract;
import entities.ContractParameter;
import java.util.LinkedList;
import java.util.List;
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
@Path("/contract_parameters")
public class ContractParameterBean {

    @PersistenceContext
    EntityManager em;

    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("contracts/{id}")
    public List<ContractParameterDTO> getContractParameterByContractCode(@PathParam("id") int contract_code) {
        try {
            Contract contract = em.find(Contract.class, contract_code);

            if (contract == null) {
                return null;
            }

            return contractParameterListToContractParameterDTOList(contract.getContractParameters());

        } catch (Exception ex) {
            throw new EJBException(ex.getMessage());
        }
    }

    private List<ContractParameterDTO> contractParameterListToContractParameterDTOList(List<ContractParameter> contractParameters) {
        try {
            List<ContractParameterDTO> contractParametersDTO = new LinkedList<>();

            contractParameters.forEach((c) -> {
                contractParametersDTO.add(contractParameterTocontractParameterDTO(c));
            });

            return contractParametersDTO;
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

    private ContractParameterDTO contractParameterTocontractParameterDTO(ContractParameter c) {
        try {
            return new ContractParameterDTO(c.getName(), c.getDescription(), c.getParamValue());

        } catch (Exception ex) {
            throw new EJBException(ex.getMessage());
        }
    }

    //Change to REST if necessary
    public void create(String name, String description, String parameterValue) {
        try {
            ContractParameter contractParameter = em.find(ContractParameter.class, name);
            if (contractParameter != null) {
                return;
                //throw new EntityExistsException("Can't create student. The username already exists on database");
            }

            contractParameter = new ContractParameter(name, description, parameterValue);

            em.persist(contractParameter);

        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

    public void associateParameterToAContract(int contractCode, String parameterName) {
        try {
            Contract contract = em.find(Contract.class, contractCode);
            ContractParameter parameter = em.find(ContractParameter.class, parameterName);

            if (contract == null || parameter == null) {
                return;
            }

            if (contract.getContractParameters().contains(parameter)) {
                return;
            }

            contract.addParameter(parameter);
            parameter.addContract(contract);

            em.merge(parameter);
            em.merge(contract);
        } catch (Exception ex) {
            throw new EJBException(ex.getMessage());
        }
    }
}
