/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejbs;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.LocalBean;
import javax.ejb.Startup;

/**
 *
 * @author Amanda
 */
@Singleton
@Startup
public class ConfigBean {

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    
    @EJB
    private AdministratorBean administratorBean;
    @EJB 
    private ClientBean clientBean;
    
    @PostConstruct
    public void populateDB()  {
        try {
          //  AdministratorDTO admin = new AdministratorDTO("123", "123", "Thiago", "fsdfb@email.com", "Administrator Of Things")
            //administratorBean.create("JoaoP", "123", "João Pedro", "job@email.com", "Administrator Of Things");
            administratorBean.create("admin", "123", "Thiago Santos", "oiji@email.com", "Administrator Of Things");
            administratorBean.create("admin1", "123", "Pedro G.", "nhb@email.com", "Administrator Of Things");
            administratorBean.create("admin2", "123", "João Pedro", "ryeb@email.com", "Administrator Of Things");
            administratorBean.create("admin3", "123", "Arlindo", "fasb@email.com", "Administrator Of Things");
            
            clientBean.create("client1", "123", "Rua dash", "Company jjij", "John - 84562145");
            
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }
    
}
