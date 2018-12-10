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
            administratorBean.create("admin4", "123", "Messi", "messi@email.com", "The Boss");
            administratorBean.create("admin5", "123", "Neymar", "neymar@email.com", "Beginner admin");
            administratorBean.create("admin6", "123", "Countinho", "coutinho@email.com", "woww");
            administratorBean.create("admin7", "123", "Suarez", "suarez@email.com", "Getting old");
            administratorBean.create("admin8", "123", "Roberto Firmino", "rfirmino@email.com", "Administrator Of Things");
            administratorBean.create("admin9", "123", "David Silva", "dsilva@email.com", "Administrator Of Things");
            administratorBean.create("admin10", "123", "Gabriel Jesus", "gabijesus@email.com", "Administrator Of Things");
            administratorBean.create("admin11", "123", "Dybala", "dybala@email.com", "Administrator Of Things");
            administratorBean.create("admin12", "123", "Arturo Vidal", "vidal@email.com", "Administrator Of Things");
            administratorBean.create("admin13", "123", "Piqué", "shakira@email.com", "Administrator Of Things");
            
            clientBean.create("client1", "123", "Rua fgfg gdf", "Marvel", "Kara - 1293715");
            clientBean.create("client2", "123", "Rua qopwk", "Super Girl", "Dean - 758491453");
            clientBean.create("client3", "123", "Rua wkeios", "MrPiracy", "Lena - 1237892");
            clientBean.create("client4", "123", "Rua 20weke", "Amazon", "Harry - 8462781992");
            clientBean.create("client5", "123", "Rua omoaslp", "Deadpool", "John - 84562145");
            clientBean.create("client6", "123", "Rua fsdfsd2er", "GearBest", "Metal - 84562145");
            clientBean.create("client7", "123", "Rua Super persons", "Apple", "Frank - 84562145");
            clientBean.create("client8", "123", "Rua dkodok", "Trident", "Jen - 84562145");
            clientBean.create("client9", "123", "Rua bvi90", "Asus", "Sam - 84562145");
            clientBean.create("client10", "123", "Rua d03r8jer", "MSI", "Rock - 84562145");
            clientBean.create("client11", "123", "Rua mmaso9a0jd", "Guardian", "Raven - 84562145");
            clientBean.create("client12", "123", "Rua mdaosodp90q", "Manchester City", "Emily - 84562145");
            
            
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }
    
}
