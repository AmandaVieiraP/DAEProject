/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejbs;

import dtos.ServiceDTO;
import entities.ConfigurationModule;
import entities.Module;
import entities.Service;
import java.util.LinkedList;
import java.util.List;
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
 * @author Iolanda
 */
@Stateless
@Path("/services")
public class ServiceBean {

    @PersistenceContext
    EntityManager em;

    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("{id}")
    public List<ServiceDTO> getServicesByModuleCode(@PathParam("id") int moduleCode) {
        try {
            Module module = em.find(Module.class, moduleCode);

            if (module == null) {
                return null;
            }

            return servicesListToServicesDTOList(module.getServices());

        } catch (Exception ex) {
            throw new EJBException(ex.getMessage());
        }
    }

    @POST
    @Path("/create/{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void createForModuleREST(@PathParam("id") int code, ServiceDTO serviceDTO) {
        try {
            ConfigurationModule m = em.find(ConfigurationModule.class, code);

            if (m == null) {
                return;
            }

            Service s = em.find(Service.class, serviceDTO.getCode());

            if (s != null) {
                return;
            }

            s = new Service(serviceDTO.getCode(), serviceDTO.getName(), serviceDTO.getDescription(), serviceDTO.getVersion());

            em.persist(s);

            s.addModule(m);
            m.addService(s);

        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

    @PUT
    @Path("/update/{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void updateModuleParameterRest(@PathParam("id") int code, ServiceDTO serviceDTO) {
        try {
            ConfigurationModule m = em.find(ConfigurationModule.class, code);

            if (m == null) {
                return;
            }

            Service s = em.find(Service.class, serviceDTO.getCode());

            if (s == null) {
                return;
            }

            s.setDescription(serviceDTO.getDescription());
            s.setName(serviceDTO.getName());
            s.setVersion(serviceDTO.getVersion());

            em.merge(s);

        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

    @DELETE
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void remove(@PathParam("id") int code) {
        try {
            Service s = em.find(Service.class, code);

            if (s == null) {
                return;
            }

            for (Module m : s.getModules()) {
                m.removeService(s);
            }

            em.remove(s);
        } catch (Exception ex) {
            throw new EJBException(ex.getMessage());
        }
    }

    private List<ServiceDTO> servicesListToServicesDTOList(List<Service> services) {
        try {
            List<ServiceDTO> servicesDTO = new LinkedList<>();

            services.forEach((s) -> {
                servicesDTO.add(serviceToServicetDTO(s));
            });

            return servicesDTO;
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

    private ServiceDTO serviceToServicetDTO(Service s) {
        try {
            return new ServiceDTO(s.getCode(),
                    s.getName(),
                    s.getDescription(),
                    s.getVersion()
            );
        } catch (Exception ex) {
            throw new EJBException(ex.getMessage());
        }
    }

    public void create(int code, String name, String description, String version) {
        try {
            Service service = em.find(Service.class, code);
            if (service != null) {
                return;
            }

            service = new Service(code, name, description, version);

            em.persist(service);

        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

    public void associateServiceToModule(int serviceCode, int moduleCode) {
        try {
            
            Service service = em.find(Service.class, serviceCode);
            Module module = em.find(Module.class, moduleCode);

            if (service == null || module == null) {
                return;
            }

            if (service.getModules().contains(module)) {
                return;
            }

            service.addModule(module);
            module.addService(service);

            em.merge(module);
            em.merge(service);
        } catch (Exception ex) {
            throw new EJBException(ex.getMessage());
        }
    }
}
