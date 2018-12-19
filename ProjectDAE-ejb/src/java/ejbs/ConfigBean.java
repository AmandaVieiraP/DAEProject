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
    private ContractParameterBean contractParameterBean;

    @EJB
    private ConfigurationSuperBean configurationSuperBean;
    
    @EJB
    private ConfigurationBean configurationBean;
    
    @EJB
    private ArtefactBean artefactBean;
    
    @EJB
    private HelpMaterialBean helpMaterialBean;

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
            
            helpMaterialBean.create("Error_procedure.txt", "text/plain");
            helpMaterialBean.create("User_Manual.pdf", "application/pdf");

            configurationSuperBean.addHelpMaterialToConfiguration(1, "Error_procedure.txt");
            configurationSuperBean.addHelpMaterialToConfiguration(1, "User_Manual.pdf");
            
            //Criar os artefactos//String filename, String mimetype
            artefactBean.create("database.sql", "application/sql");
            artefactBean.create("dae_esquema.png", "image/png");

            configurationSuperBean.addArtefactsToConfiguration(1, "database.sql");
            configurationSuperBean.addArtefactsToConfiguration(1, "dae_esquema.png");
            
            //Criar configuração para associar a um cliente
            configurationBean.create(30, "Configuration 1", 1000, 10, "Version 1.0", "client1");
            configurationBean.create(31, "Configuration 2", 1000, 10, "Version 1.0", "client1");
            configurationBean.create(32, "Configuration 2", 1000, 10, "Version 1.0", "client1");

        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

}
