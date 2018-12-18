/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejbs;

import entities.Artefact;
import entities.ConfigurationSuper;
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
@Path("/configurations")
public class ConfigurationSuperBean {

    @PersistenceContext
    EntityManager em;

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Path("artefacts/{id}")
    public String getConfigurationArtefactsRepository(@PathParam("id") int configCode) {
        try {
            ConfigurationSuper configurationSuper = em.find(ConfigurationSuper.class, configCode);
            if (configurationSuper == null) {
                return null;
            }

            return configurationSuper.getArtefactsRepository().toString();

        } catch (Exception ex) {
            throw new EJBException(ex.getMessage());
        }
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Path("helpMaterials/{id}")
    public String getConfigurationHelpMaterials(@PathParam("id") int configCode) {
        try {
            ConfigurationSuper configurationSuper = em.find(ConfigurationSuper.class, configCode);
            if (configurationSuper == null) {
                return null;
            }

            return configurationSuper.getHelpMaterials().toString();

        } catch (Exception ex) {
            throw new EJBException(ex.getMessage());
        }
    }

    public void addHelpMaterialToConfiguration(int configCode, String helpMaterial) {
        try {

            ConfigurationSuper conf = em.find(ConfigurationSuper.class, configCode);

            if (conf == null || conf.getHelpMaterials().contains(helpMaterial)) {
                return;
            }

            conf.addHelpMaterial(helpMaterial);

            em.merge(conf);

        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

    public void addArtefactsToConfiguration(int configCode, String filename) {
        ConfigurationSuper conf = em.find(ConfigurationSuper.class, configCode);

        if (conf == null){
            return;
        }
        
        Artefact artefact=em.find(Artefact.class, filename);
        
        if (artefact == null){
            return;
        }
        
        if(conf.getArtefactsRepository().contains(artefact)){
            return;
        }

        conf.addArtefact(artefact);

        em.merge(conf);
        em.merge(artefact);
    }
}
