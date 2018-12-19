/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejbs;

import dtos.ExtensionDTO;
import dtos.ServiceDTO;
import entities.Contract;
import entities.Extension;
import entities.Module;
import entities.Parameter;
import entities.Service;
import entities.Software;
import java.util.LinkedList;
import java.util.List;
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
@Path("/services")
public class ServiceBean {

    @PersistenceContext
    EntityManager em;
    
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

    //Change to REST if necessary
    public void create(int code, String name, String description, String version) {
        try {
            Service service = em.find(Service.class, code);
            if (service != null) {
                return;
                //throw new EntityExistsException("Can't create student. The username already exists on database");
            }

            service = new Service(code, name, description, version);

            em.persist(service);

        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    
    public void associateServiceToModule(int serviceCode,int moduleCode) {
        try {
            Service service = em.find(Service.class,serviceCode);
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
