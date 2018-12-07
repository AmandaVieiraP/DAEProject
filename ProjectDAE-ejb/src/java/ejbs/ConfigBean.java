/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejbs;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
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
    private TemplateBean templateBean;
    
    @EJB
    private SoftwareBean softwareBean;
    
    @EJB
    private ContractBean contractBean;
    
    @PostConstruct
    public void populateDB()  {
        try {
            administratorBean.create("JoaoP", "123", "João Pedro", "job@email.com", "Administrator Of Things");
            administratorBean.create("123", "123", "Thiago", "fsdfb@email.com", "Administrator Of Things");
            
            //Criar o software 1000
            softwareBean.create(1000, "Spots");
            //Criar o contrat 10
            contractBean.create(10, 12, 200.50, 2, "8:00 am - 1:00 pm", 20.50);
            
            //templateBean.create(1, "Template 1", ContractParameters.STATE.Active, 1000, "Version 1", 10, "C:\\Template_1_Repo");
            
            //templateBean.addServiceToTemplate("Service 1", 1);
            //templateBean.addServiceToTemplate("Service 2", 1);
            //templateBean.addServiceToTemplate("Service 3", 1);
            
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }
    
}
