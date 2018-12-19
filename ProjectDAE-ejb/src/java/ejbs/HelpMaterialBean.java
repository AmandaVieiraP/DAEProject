/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejbs;

import entities.Contract;
import entities.HelpMaterial;
import entities.Software;
import entities.Template;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Path;

/**
 *
 * @author Iolanda
 */
@Stateless
@Path("/helpMaterials")
public class HelpMaterialBean {

    @PersistenceContext
    EntityManager em;

    public void create(String filename, String mimetype) {
        try {
            HelpMaterial helpMaterial = em.find(HelpMaterial.class, filename);

            if (helpMaterial != null) {
                return;
                //throw new EntityExistsException("Can't create student. The username already exists on database");
            }

            helpMaterial = new HelpMaterial(filename,mimetype);

            em.persist(helpMaterial);

        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
}
