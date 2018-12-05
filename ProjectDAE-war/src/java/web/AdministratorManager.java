/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web;


import ejbs.AdministratorBean;
import java.io.Serializable;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author Amanda
 */
//@Named(value = "administratorManager")
@ManagedBean(name="administratorManager")
@SessionScoped
public class AdministratorManager implements Serializable {
    // também tem propriedades de controlador 
    
    /**
     * Creates a new instance of AdministratorManager
     */
    private static final Logger logger = Logger.getLogger("web.AdministratorManager");
    
    @EJB 
    private AdministratorBean administratorBean;
    
    
    @ManagedProperty("#{userManager}")
    private UserManager userManager; 

    public AdministratorManager() {
        // acrescencar posteriormente
        
    }
    
    
    
    
    
    public UserManager getUserManager() {
        return userManager;
    }

    public void setUserManager(UserManager userManager) {
        this.userManager = userManager;
    }
    
    
                
    
}
