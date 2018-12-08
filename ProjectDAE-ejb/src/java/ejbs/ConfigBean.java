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

    @EJB
    private ContractParameterBean contractParameterBean;

    @EJB
    private ConfigurationSuperBean configurationSuperBean;

    @PostConstruct
    public void populateDB() {
        try {
            administratorBean.create("JoaoP", "123", "João Pedro", "job@email.com", "Administrator Of Things");
            administratorBean.create("123", "123", "Thiago", "fsdfb@email.com", "Administrator Of Things");

            //Criar o software 1000
            softwareBean.create(1000, "Spots", "A parking spot manager software");

            //Adicionar Versão ao software
            softwareBean.addVersionToSoftware(1000, "Version 1.0");
            softwareBean.addVersionToSoftware(1000, "Version 2.0");
            softwareBean.addVersionToSoftware(1000, "Version 2.1");

            //Adicionar Extensoes ao software
            extensionBean.create(12, "Clear Cache", "Clear Chrome Browser Cache", 1000);
            extensionBean.create(13, "Aamazon For Opera", "Deal of the day, Price Comparision, Universal Wish List", 1000);
            extensionBean.create(14, "Last Pass", "Password Manager", 1000);

            //Adicionar Modules de Software ao Software
            softwareModuleBean.create(100, "FD - Financial Data Managment", 1000);
            softwareModuleBean.create(101, "HR - Human Resources Management", 1000);

            //Criar Parametros para o contrato
            contractParameterBean.create("Type 1 - Maintenance Hours", "Total Hours of mensal support to any issue related to software fail", "12 ours / month");
            contractParameterBean.create("Type 1 - Contract Price", "Total price of the contract during the according time", "10000 €");
            contractParameterBean.create("Type 1 - Contract Duration", "Amount of years where the contract is valid", "3 years");
            contractParameterBean.create("Type 1 - Maintenance Schedule", "Maintenance horary for support of eventual problems", "From 8:00 am to 4:00 pm");
            contractParameterBean.create("Type 1 - Guarantee of Support", "Guarantee of response hover the time period of maintenance schedule", "If we miss your call we will return it during the next 10 min");

            //Criar o contrat 10
            contractBean.create(10);

            templateBean.create(1, "Template 1", 1000, 10, "Version 1.0");
            templateBean.create(2, "Template 2", 1000, 10, "Version 1.0");

            contractParameterBean.associateParameterToAContract(10, "Type 1 - Maintenance Hours");
            contractParameterBean.associateParameterToAContract(10, "Type 1 - Contract Price");
            contractParameterBean.associateParameterToAContract(10, "Type 1 - Contract Duration");
            contractParameterBean.associateParameterToAContract(10, "Type 1 - Maintenance Schedule");
            contractParameterBean.associateParameterToAContract(10, "Type 1 - Guarantee of Support");
            /* Outros Parametro Exemplos:
            custo adicional por hora de manutençao
            qualidade de serviço, 
            responsabilidades,
            disponibilidade...*/

            templateBean.associateExtensionToTemplate(12, 1);
            templateBean.associateExtensionToTemplate(13, 2);

            templateBean.associateModuleToTemplate(100, 1);
            templateBean.associateModuleToTemplate(101, 2);

            configurationSuperBean.addHelpMaterialToConfiguration(1, "First Steps in Spots Software");
            configurationSuperBean.addHelpMaterialToConfiguration(1, "How to deal with firebase exceptions");

            configurationSuperBean.addArtefactsToConfiguration(1, "script.sql");
            configurationSuperBean.addArtefactsToConfiguration(1, "spots.apk");

        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

}
