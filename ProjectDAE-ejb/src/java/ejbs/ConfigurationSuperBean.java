/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejbs;

import entities.Artefact;
import entities.ConfigurationSuper;
import entities.HelpMaterial;
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
@Path("/configurations")
public class ConfigurationSuperBean {

    @PersistenceContext
    EntityManager em;

    public void addHelpMaterialToConfiguration(int configCode, String filename) {
        try {

            ConfigurationSuper conf = em.find(ConfigurationSuper.class, configCode);
            HelpMaterial helpMaterial = em.find(HelpMaterial.class, filename);

            if (conf == null) {
                return;
            }

            if (helpMaterial == null) {
                return;
            }

            if (conf.getHelpMaterials().contains(helpMaterial)) {
                return;
            }

            conf.addHelpMaterial(helpMaterial);
            helpMaterial.addConfigurations(conf);

            em.merge(conf);
            em.merge(helpMaterial);

        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

    public void addArtefactsToConfiguration(int configCode, String filename) {
        try {
            ConfigurationSuper conf = em.find(ConfigurationSuper.class, configCode);
            Artefact artefact = em.find(Artefact.class, filename);

            if (conf == null) {
                return;
            }

            if (artefact == null) {
                return;
            }

            if (conf.getArtefactsRepository().contains(artefact)) {
                return;
            }

            conf.addArtefact(artefact);
            artefact.addConfigurations(conf);

            em.merge(conf);
            em.merge(artefact);

        } catch (Exception ex) {
            throw new EJBException(ex.getMessage());
        }
    }
}
