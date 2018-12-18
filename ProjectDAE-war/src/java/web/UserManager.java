/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web;

import javax.inject.Named;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Amanda
 */
//@Named(value = "userManager")
@ManagedBean(name="userManager")
@SessionScoped
public class UserManager implements Serializable {

    /**
     * Creates a new instance of UserManager
     */
    private static final Logger logger = Logger.getLogger("web.UserManager");
    private String username;
    private String password;
    private boolean loginFlag = true;
    
    public UserManager() {
    }

    public UserManager(String username, String password) {
        this.username = username;
        this.password = password;
    }
       

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isLoginFlag() {
        return loginFlag;
    }

    public void setLoginFlag(boolean loginFlag) {
        this.loginFlag = loginFlag;
    }
    
    
    
    public String login() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        try {
            request.login(this.username, this.password);
        } catch (ServletException e) {
            logger.log(Level.WARNING, e.getMessage());
            return "login_error?faces-redirect=true";
        }
        if(isUserInRole("Administrator")){
            return "/faces/admin/admin_index?faces-redirect=true";
        }
        if(isUserInRole("Client")){
            return "/faces/client/client_index?faces-redirect=true";
        }
        
        return "login_error?faces-redirect=true";
    }
    
    public boolean isUserInRole(String role) {
        return (isSomeUserAuthenticated() && FacesContext.getCurrentInstance().getExternalContext().isUserInRole(role));
    }
    
    public boolean isSomeUserAuthenticated() {
        return FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal()!=null;
    }
    
    public String logout() {
        FacesContext context = FacesContext.getCurrentInstance();
        // remove data from beans:
        for (String bean : context.getExternalContext().getSessionMap().keySet()) {
            context.getExternalContext().getSessionMap().remove(bean);
        }
        // destroy session:
        HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
        session.invalidate();
        // using faces-redirect to initiate a new request:
        return "/index.xhtml?faces-redirect=true";
    }
    
    public String clearLogin(){
        if(isSomeUserAuthenticated()){
            logout();
        }
        return "index.xhtml?faces-redirect=true";
    }
    
    
    
}
