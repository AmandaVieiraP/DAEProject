/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejbs;

import dtos.ConfigurationModuleDTO;
import dtos.ParameterDTO;
import entities.Configuration;
import entities.ConfigurationModule;
import entities.Contract;
import entities.Parameter;
import java.util.LinkedList;
import java.util.List;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
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
public class ParameterBean {

    @PersistenceContext
    EntityManager em;

    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("all/configurations")
    public List<ParameterDTO> getAllParametersFromConfigurations() {
        try {
            //Vai buscar todas as configurações
            List<Parameter> allParameters = new LinkedList<>();

            List<Configuration> configurations = em.createNamedQuery("getAllConfigurations").getResultList();

            for (Configuration c : configurations) {

                List<Parameter> parameterConfiguration = c.getConfigurationParameters();

                for (Parameter p : parameterConfiguration) {
                    if (!allParameters.contains(p)) {
                        allParameters.add(p);
                    }
                }

            }

            return contractParameterListToContractParameterDTOList(allParameters);

        } catch (Exception ex) {
            throw new EJBException(ex.getMessage());
        }
    }

    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("contracts/{id}")
    public List<ParameterDTO> getContractParameterByContractCode(@PathParam("id") int contract_code) {
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

    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("configurations/{id}")
    public List<ParameterDTO> getParameterByConfigurationCode(@PathParam("id") int configurationCode) {
        try {
            Configuration configuration = em.find(Configuration.class, configurationCode);

            if (configuration == null) {
                return null;
            }

            return contractParameterListToContractParameterDTOList(configuration.getConfigurationParameters());

        } catch (Exception ex) {
            throw new EJBException(ex.getMessage());
        }
    }

    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("modules/{id}")
    public List<ParameterDTO> getParameterByModuleCode(@PathParam("id") int moduleCode) {
        try {
            ConfigurationModule module = em.find(ConfigurationModule.class, moduleCode);

            if (module == null) {
                return null;
            }

            return contractParameterListToContractParameterDTOList(module.getModuleParameters());

        } catch (Exception ex) {
            throw new EJBException(ex.getMessage());
        }
    }

    @PUT
    @Path("/associateConfigurations/{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void associateExtensionRest(@PathParam("id") int code, ParameterDTO parameterDTO) {
        try {
            
            associateParameterToAConfiguration(code, parameterDTO.getName());

        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

    private List<ParameterDTO> contractParameterListToContractParameterDTOList(List<Parameter> contractParameters) {
        try {
            List<ParameterDTO> contractParametersDTO = new LinkedList<>();

            contractParameters.forEach((c) -> {
                contractParametersDTO.add(contractParameterTocontractParameterDTO(c));
            });

            return contractParametersDTO;
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

    private ParameterDTO contractParameterTocontractParameterDTO(Parameter c) {
        try {
            return new ParameterDTO(c.getName(), c.getDescription(), c.getParamValue());

        } catch (Exception ex) {
            throw new EJBException(ex.getMessage());
        }
    }

    //Change to REST if necessary
    public void create(String name, String description, String parameterValue) {
        try {
            Parameter contractParameter = em.find(Parameter.class, name);
            if (contractParameter != null) {
                return;
                //throw new EntityExistsException("Can't create student. The username already exists on database");
            }

            contractParameter = new Parameter(name, description, parameterValue);

            em.persist(contractParameter);

        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

    public void associateParameterToAContract(int contractCode, String parameterName) {
        try {
            Contract contract = em.find(Contract.class, contractCode);
            Parameter parameter = em.find(Parameter.class, parameterName);

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

    public void associateParameterToAConfiguration(int configCode, String parameterName) {
        try {
            Configuration configuration = em.find(Configuration.class, configCode);
            Parameter parameter = em.find(Parameter.class, parameterName);

            if (configuration == null || parameter == null) {
                return;
            }

            if (configuration.getConfigurationParameters().contains(parameter)) {
                return;
            }

            configuration.addParameters(parameter);
            parameter.addConfigurations(configuration);

            em.merge(parameter);
            em.merge(configuration);
        } catch (Exception ex) {
            throw new EJBException(ex.getMessage());
        }
    }

    public void associateParameterToAModule(int moduleCode, String parameterName) {
        try {
            ConfigurationModule module = em.find(ConfigurationModule.class, moduleCode);
            Parameter parameter = em.find(Parameter.class, parameterName);

            if (module == null || parameter == null) {
                return;
            }

            if (module.getModuleParameters().contains(parameter)) {
                return;
            }

            module.addParameter(parameter);
            parameter.addModule(module);

            em.merge(parameter);
            em.merge(module);
        } catch (Exception ex) {
            throw new EJBException(ex.getMessage());
        }
    }
}
