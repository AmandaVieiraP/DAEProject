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
    private ClientBean clientBean;

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
    private ParameterBean contractParameterBean;

    @EJB
    private ConfigurationSuperBean configurationSuperBean;
    
    @EJB
    private ConfigurationBean configurationBean;
    
    @EJB
    private ArtefactBean artefactBean;
    
    @EJB
    private HelpMaterialBean helpMaterialBean;
    
    @EJB
    private ServiceBean serviceBean;
    
    @EJB
    private ConfigurationModuleBean configurationModuleBean;
    
    @EJB
    private LicenseBean licenseBean;

    @PostConstruct
    public void populateDB() {
        try {
            //AdministratorDTO admin = new AdministratorDTO("123", "123", "Thiago", "fsdfb@email.com", "Administrator Of Things")
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

            //Criar o softwares
            softwareBean.create(1000, "Spots", "A parking spot manager software");
            softwareBean.create(1001, "Reteck", "A retail manager software");
            softwareBean.create(1002, "GitNet", "A daily planning software");

            //Adicionar Versão ao software
            softwareBean.addVersionToSoftware(1000, "Version 1.0");
            softwareBean.addVersionToSoftware(1000, "Version 2.0");
            softwareBean.addVersionToSoftware(1000, "Version 2.1");
            softwareBean.addVersionToSoftware(1001, "Version 1.0");
            softwareBean.addVersionToSoftware(1001, "Version 1.1");
            softwareBean.addVersionToSoftware(1001, "Version 1.5");
            softwareBean.addVersionToSoftware(1002, "Version 1.1");
            softwareBean.addVersionToSoftware(1002, "Version 1.7");
            softwareBean.addVersionToSoftware(1002, "Version 2.3");

            //Criar Extensoes
            extensionBean.create(12, "Clear Cache", "Clear Chrome Browser Cache", 1000,"Version 1.0");
            extensionBean.create(13, "Clear Cache", "Clear Chrome Browser Cache", 1001,"Version 3.0");
            extensionBean.create(14, "Clear Cache", "Clear Chrome Browser Cache", 1002,"Version 2.0");
            extensionBean.create(15, "Aamazon For Opera", "Deal of the day, Price Comparision, Universal Wish List", 1000,"Version 1.0");
            extensionBean.create(16, "Aamazon For Opera", "Deal of the day, Price Comparision, Universal Wish List", 1001,"Version 2.0");
            extensionBean.create(17, "Last Pass", "Password Manager", 1000,"Version 1.0");
            extensionBean.create(18, "Last Pass", "Password Manager", 1000,"Version 2.0");
            extensionBean.create(19, "Last Pass", "Password Manager", 1001,"Version 3.0");
            extensionBean.create(20, "Last Pass", "Password Manager", 1002,"Version 4.0");

            //Criar Software Modules
            softwareModuleBean.create(100, "FD - Financial Data Managment", 1000,"Version 1.1");
            softwareModuleBean.create(101, "HR - Human Resources Management", 1000,"Version 1.2");
            softwareModuleBean.create(102, "FD - Financial Data Managment", 1001,"Version 1.2");
            softwareModuleBean.create(103, "HR - Human Resources Management", 1002,"Version 1.3");
            
            //Criar Serviços
            serviceBean.create(1,"Service 1","Description of service 1","Version 1.0");
            serviceBean.create(2,"Service 2","Description of service 2","Version 1.2");
            serviceBean.create(3,"Service 3","Description of service 3","Version 1.0");
            serviceBean.create(4,"Service 4","Description of service 4","Version 1.2");
            
            //Associar serviços a modulos
            serviceBean.associateServiceToModule(1, 100);
            serviceBean.associateServiceToModule(2, 100);
            serviceBean.associateServiceToModule(3, 100);
            serviceBean.associateServiceToModule(1, 101);
            serviceBean.associateServiceToModule(2, 101);
            serviceBean.associateServiceToModule(1, 102);
            serviceBean.associateServiceToModule(3, 102);
            serviceBean.associateServiceToModule(4, 104);
            
            //Criar Parametros para o contrato
            contractParameterBean.create("Type 1 - Maintenance Hours", "Total Hours of mensal support to any issue related to software fail", "12 hours / month");
            contractParameterBean.create("Type 1 - Contract Price", "Total price of the contract during the according time", "10000 €");
            contractParameterBean.create("Type 1 - Contract Duration", "Amount of years where the contract is valid", "3 years");
            contractParameterBean.create("Type 1 - Maintenance Schedule", "Maintenance horary for support of eventual problems", "From 8:00 am to 4:00 pm");
            contractParameterBean.create("Type 1 - Guarantee of Support", "Guarantee of response hover the time period of maintenance schedule", "If we miss your call we will return it during the next 10 min");
            contractParameterBean.create("Type 1 - Adicional Maintenance Cost", "The aditional maintenance price per hour", "10 € / hour");
            contractParameterBean.create("Type 1 - Quality of Service", "The time that is acceptable to resolute problems in the database", "1 hour");
            contractParameterBean.create("Type 1 - Responsability", "The company responsability", "Not rise the price of contract during the contract period");
            contractParameterBean.create("Type 1 - Avalaibality", "The period where the software access must be avaliable", "All the time less on a specific maintenance asked by the client");

            //Criar o contrat 10
            contractBean.create(10);
            contractBean.create(11);
            contractBean.create(12);

            templateBean.create(1, "Template 1", 1000, 10, "Version 1.0");
            templateBean.create(2, "Template 2", 1000, 10, "Version 1.0");
            templateBean.create(3, "Template 1", 1001, 10, "Version 1.0");
            templateBean.create(4, "Template 2", 1001, 10, "Version 1.0");
            templateBean.create(5, "Template 1", 1002, 10, "Version 1.0");
            templateBean.create(6, "Template 2", 1002, 10, "Version 1.0");

            contractParameterBean.associateParameterToAContract(10, "Type 1 - Maintenance Hours");
            contractParameterBean.associateParameterToAContract(10, "Type 1 - Contract Price");
            contractParameterBean.associateParameterToAContract(10, "Type 1 - Contract Duration");
            contractParameterBean.associateParameterToAContract(10, "Type 1 - Maintenance Schedule");
            contractParameterBean.associateParameterToAContract(10, "Type 1 - Guarantee of Support");
            contractParameterBean.associateParameterToAContract(11, "Type 1 - Adicional Maintenance Cost");
            contractParameterBean.associateParameterToAContract(11, "Type 1 - Quality of Service");
            contractParameterBean.associateParameterToAContract(12, "Type 1 - Responsability");
            contractParameterBean.associateParameterToAContract(12, "Type 1 - Avalaibality");
            
            configurationSuperBean.associateExtensionToConfiguration(12, 1);
            configurationSuperBean.associateExtensionToConfiguration(13, 2);
            configurationSuperBean.associateExtensionToConfiguration(14, 3);
            configurationSuperBean.associateExtensionToConfiguration(15, 4);
            configurationSuperBean.associateExtensionToConfiguration(16, 5);
            configurationSuperBean.associateExtensionToConfiguration(17, 6);
            configurationSuperBean.associateExtensionToConfiguration(18, 1);
            configurationSuperBean.associateExtensionToConfiguration(19, 2);
            configurationSuperBean.associateExtensionToConfiguration(20, 3);
           
            templateBean.associateModuleToTemplate(100, 1);
            templateBean.associateModuleToTemplate(101, 2);
            templateBean.associateModuleToTemplate(102, 1);
            templateBean.associateModuleToTemplate(103, 2);
            templateBean.associateModuleToTemplate(100, 3);
            templateBean.associateModuleToTemplate(101, 4);
            templateBean.associateModuleToTemplate(102, 5);
            templateBean.associateModuleToTemplate(103, 6);
            
            helpMaterialBean.create("Error_procedure.txt", "text/plain");
            helpMaterialBean.create("User_Manual.pdf", "application/pdf");

            configurationSuperBean.addHelpMaterialToConfiguration(1, "Error_procedure.txt");
            configurationSuperBean.addHelpMaterialToConfiguration(1, "User_Manual.pdf");
            configurationSuperBean.addHelpMaterialToConfiguration(2, "Error_procedure.txt");
            configurationSuperBean.addHelpMaterialToConfiguration(2, "User_Manual.pdf");
            configurationSuperBean.addHelpMaterialToConfiguration(3, "Error_procedure.txt");
            configurationSuperBean.addHelpMaterialToConfiguration(4, "User_Manual.pdf");
            configurationSuperBean.addHelpMaterialToConfiguration(4, "Error_procedure.txt");
            configurationSuperBean.addHelpMaterialToConfiguration(5, "User_Manual.pdf");
            configurationSuperBean.addHelpMaterialToConfiguration(6, "Error_procedure.txt");
            configurationSuperBean.addHelpMaterialToConfiguration(6, "User_Manual.pdf");
            
            //Criar os artefactos//String filename, String mimetype
            artefactBean.create("database.sql", "application/sql");
            artefactBean.create("dae_esquema.png", "image/png");

            configurationSuperBean.addArtefactsToConfiguration(1, "database.sql");
            configurationSuperBean.addArtefactsToConfiguration(2, "database.sql");
            configurationSuperBean.addArtefactsToConfiguration(3, "database.sql");
            configurationSuperBean.addArtefactsToConfiguration(4, "database.sql");
            configurationSuperBean.addArtefactsToConfiguration(5, "database.sql");
            configurationSuperBean.addArtefactsToConfiguration(6, "database.sql");
            configurationSuperBean.addArtefactsToConfiguration(1, "dae_esquema.png");
            configurationSuperBean.addArtefactsToConfiguration(2, "dae_esquema.png");
            configurationSuperBean.addArtefactsToConfiguration(3, "dae_esquema.png");
            configurationSuperBean.addArtefactsToConfiguration(4, "dae_esquema.png");
            configurationSuperBean.addArtefactsToConfiguration(5, "dae_esquema.png");
            configurationSuperBean.addArtefactsToConfiguration(6, "dae_esquema.png");

            
            configurationModuleBean.create(105, "Module HR", 1000, "Version 1.1");
            configurationModuleBean.create(106, "Module HR", 1002, "Version 1.1");
            configurationModuleBean.create(107, "Module HR", 1001, "Version 1.1");
            
            //Criar configuração para associar a um cliente
            configurationBean.create(30, "Configuration 1", 1000, 10, "Version 1.0", "client1", "164.0.0.1", "189.1.1.3");
            configurationBean.create(31, "Configuration 2", 1001, 10, "Version 1.0", "client1","164.0.0.1", "189.1.1.3");
            configurationBean.create(32, "Configuration 3", 1002, 10, "Version 1.0", "client1","164.0.0.1", "189.1.1.3");
            configurationBean.create(33, "Configuration 1", 1001, 10, "Version 1.0", "client4","164.0.0.1", "189.1.1.3");
            configurationBean.create(34, "Configuration 2", 1001, 10, "Version 1.0", "client4","164.0.0.1", "189.1.1.3");
            configurationBean.create(35, "Configuration 3", 1001, 10, "Version 1.0", "client4","164.0.0.1", "189.1.1.3");
            configurationBean.create(36, "Configuration 1", 1000, 10, "Version 1.0", "client6","164.0.0.1", "189.1.1.3");
            configurationBean.create(37, "Configuration 2", 1002, 10, "Version 1.0", "client6","164.0.0.1", "189.1.1.3");
            configurationBean.create(38, "Configuration 3", 1000, 10, "Version 1.0", "client6","164.0.0.1", "189.1.1.3");
            
            configurationSuperBean.associateExtensionToConfiguration(12, 30);
            configurationSuperBean.associateExtensionToConfiguration(13, 31);
            configurationSuperBean.associateExtensionToConfiguration(14, 32);
            configurationSuperBean.associateExtensionToConfiguration(15, 33);
            configurationSuperBean.associateExtensionToConfiguration(16, 34);
            configurationSuperBean.associateExtensionToConfiguration(17, 35);
            configurationSuperBean.associateExtensionToConfiguration(18, 36);
            configurationSuperBean.associateExtensionToConfiguration(19, 30);
            configurationSuperBean.associateExtensionToConfiguration(20, 33);
            
            configurationModuleBean.associateModuleToConfiguration(105, 30);
            configurationModuleBean.associateModuleToConfiguration(105, 31);
            configurationModuleBean.associateModuleToConfiguration(105, 32);
            configurationModuleBean.associateModuleToConfiguration(106, 33);
            configurationModuleBean.associateModuleToConfiguration(106, 34);
            configurationModuleBean.associateModuleToConfiguration(106, 35);
            configurationModuleBean.associateModuleToConfiguration(106, 36);
            configurationModuleBean.associateModuleToConfiguration(107, 37);
            configurationModuleBean.associateModuleToConfiguration(107, 38);
            configurationModuleBean.associateModuleToConfiguration(106, 35);
            
            configurationSuperBean.addArtefactsToConfiguration(31, "database.sql");
            configurationSuperBean.addArtefactsToConfiguration(32, "database.sql");
            configurationSuperBean.addArtefactsToConfiguration(33, "database.sql");
            configurationSuperBean.addArtefactsToConfiguration(34, "database.sql");
            configurationSuperBean.addArtefactsToConfiguration(35, "database.sql");
            configurationSuperBean.addArtefactsToConfiguration(36, "database.sql");
            configurationSuperBean.addArtefactsToConfiguration(31, "dae_esquema.png");
            configurationSuperBean.addArtefactsToConfiguration(32, "dae_esquema.png");
            configurationSuperBean.addArtefactsToConfiguration(33, "dae_esquema.png");
            configurationSuperBean.addArtefactsToConfiguration(34, "dae_esquema.png");
            configurationSuperBean.addArtefactsToConfiguration(35, "dae_esquema.png");
            configurationSuperBean.addArtefactsToConfiguration(36, "dae_esquema.png");
            
            configurationSuperBean.addHelpMaterialToConfiguration(31, "Error_procedure.txt");
            configurationSuperBean.addHelpMaterialToConfiguration(31, "User_Manual.pdf");
            configurationSuperBean.addHelpMaterialToConfiguration(32, "Error_procedure.txt");
            configurationSuperBean.addHelpMaterialToConfiguration(32, "User_Manual.pdf");
            configurationSuperBean.addHelpMaterialToConfiguration(33, "Error_procedure.txt");
            configurationSuperBean.addHelpMaterialToConfiguration(34, "User_Manual.pdf");
            configurationSuperBean.addHelpMaterialToConfiguration(34, "Error_procedure.txt");
            configurationSuperBean.addHelpMaterialToConfiguration(35, "User_Manual.pdf");
            configurationSuperBean.addHelpMaterialToConfiguration(36, "Error_procedure.txt");
            configurationSuperBean.addHelpMaterialToConfiguration(36, "User_Manual.pdf");
            
            contractParameterBean.associateParameterToAConfiguration(33, "Type 1 - Maintenance Hours");
            contractParameterBean.associateParameterToAConfiguration(33, "Type 1 - Contract Price");
            contractParameterBean.associateParameterToAConfiguration(34, "Type 1 - Contract Duration");
            contractParameterBean.associateParameterToAConfiguration(34, "Type 1 - Maintenance Schedule");
            contractParameterBean.associateParameterToAConfiguration(33, "Type 1 - Guarantee of Support");
            
            //Criar licensas
            licenseBean.create(1, "License Nr 1", 105);
            licenseBean.create(2, "License Nr 1", 106);
            licenseBean.create(3, "License Nr 1", 107);
            licenseBean.create(4, "License Nr 2", 105);
            licenseBean.create(5, "License Nr 2", 106);
            licenseBean.create(6, "License Nr 2", 107);
            
            contractParameterBean.associateParameterToAModule(105, "Type 1 - Maintenance Schedule");
            contractParameterBean.associateParameterToAModule(106, "Type 1 - Guarantee of Support");
            contractParameterBean.associateParameterToAModule(107, "Type 1 - Guarantee of Support");
            
            serviceBean.associateServiceToModule(1, 105);
            serviceBean.associateServiceToModule(2, 106);
            serviceBean.associateServiceToModule(3, 107);
            serviceBean.associateServiceToModule(1, 106);
            serviceBean.associateServiceToModule(2, 107);
            serviceBean.associateServiceToModule(1, 107);
            serviceBean.associateServiceToModule(3, 105);
            serviceBean.associateServiceToModule(4, 105);

        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

}
