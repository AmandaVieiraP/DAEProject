/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejbs;

import dtos.ConfigurationDTO;
import dtos.HelpMaterialDTO;
import dtos.LicenseDTO;
import entities.ConfigurationModule;
import entities.ConfigurationSuper;
import entities.Contract;
import entities.HelpMaterial;
import entities.License;
import entities.Software;
import entities.Template;
import java.util.LinkedList;
import java.util.List;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author Iolanda
 */
@Stateless
@Path("/licenses")
public class LicenseBean {

    @PersistenceContext
    EntityManager em;
    
    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("{id}")
    public List<LicenseDTO> getConfigurationModuleLicense(@PathParam("id") int moduleCode) {
        try {
            ConfigurationModule configurationModule = em.find(ConfigurationModule.class, moduleCode);
            if (configurationModule == null) {
                return null;
            }

            return licensesListToLicensesDTOList(configurationModule.getLicenses());

        } catch (Exception ex) {
            throw new EJBException(ex.getMessage());
        }
    }
    
    @POST
    @Path("/create/{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void createREST(@PathParam("id") int moduleCode, LicenseDTO licenseDTO) {
        try {
            License license = em.find(License.class, licenseDTO.getCode());
            
            if(license!=null)
                return;
            
            ConfigurationModule configurationModule = em.find(ConfigurationModule.class, moduleCode);
            if (configurationModule == null) {
                return;
            }
            
            license = new License(licenseDTO.getCode(), licenseDTO.getLicenceValue(),configurationModule);
            
            configurationModule.addLicense(license);
            
            em.persist(license);

        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }


    public void create(int code, String value, int moduleCode) {
        try {
            License license = em.find(License.class, code);

            if (license != null) {
                return;
            }
            
            ConfigurationModule module=em.find(ConfigurationModule.class, moduleCode);

            if (module == null) {
                return;
            }
            
            license = new License(code,value,module);
            
            module.addLicense(license);

            em.persist(license);

        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

    private List<LicenseDTO> licensesListToLicensesDTOList(List<License> licenses) {
        try {
            List<LicenseDTO> licensesDTO = new LinkedList<>();

            for (License l : licenses) {
                licensesDTO.add(licenseToLicenseDTO(l));
            }

            return licensesDTO;
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

    private LicenseDTO licenseToLicenseDTO(License l) {
        try {
            return new LicenseDTO(l.getCode(),l.getLicenceValue(),l.getConfigurationModule().getCode());
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
}
