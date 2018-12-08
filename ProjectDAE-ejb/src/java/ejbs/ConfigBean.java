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
    
    @EJB
    private ExtensionBean extensionBean;
    
    @EJB
    private SoftwareModuleBean softwareModuleBean;
   
    
    @PostConstruct
    public void populateDB()  {
        try {
            administratorBean.create("JoaoP", "123", "João Pedro", "job@email.com", "Administrator Of Things");
            administratorBean.create("123", "123", "Thiago", "fsdfb@email.com", "Administrator Of Things");
            
            //Criar o software 1000
            softwareBean.create(1000, "Spots","A parking spot manager software");
            
            //Adicionar Versão ao software
            softwareBean.addVersionToSoftware(1000,"version 1.0");
            softwareBean.addVersionToSoftware(1000,"version 2.0");
            softwareBean.addVersionToSoftware(1000,"version 3.0");
            
            //Adicionar Extensoes ao software
            extensionBean.create(12,"Clear Cache","Clear Chrome Browser Cache",1000);
            extensionBean.create(13,"Aamazon For Opera","Deal of the day, Price Comparision, Universal Wish List",1000);
            extensionBean.create(14,"Last Pass","Password Manager",1000);
            
            //Adicionar Modules de Software ao Software
            softwareModuleBean.create(100, "Module for financial data", 1000, "Spots");
            softwareModuleBean.create(101, "Module for human resources management", 1000, "Spots");
            
            //Criar o contrat 10
            contractBean.create(10);
            
            
            templateBean.create(1, "Template 1", 1000,  10, "version 1.0");
            templateBean.create(2, "Template 2", 1000, 10, "version 1.0");
            
            
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }
    
}
