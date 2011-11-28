package org.company.controller;

import java.io.Serializable;
import java.security.Principal;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Produces;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * This class is used for authenticating and authorising user's login request.
 */
@Named
@SessionScoped
public class Login implements Serializable {

   @Inject
   private Logger log;

   private String userName;
   
   private String password;
   
   private String userPrincipal;
   
   /**
    * log-in user request.
    * @return	success in case login is successful
    * @throws Exception
    */
   public String login() throws Exception{
	   log.log(Level.FINE, "Login attempt for user " + userName);
	   FacesContext context = FacesContext.getCurrentInstance();
	   HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
	   boolean loggedIn = true;
	   
//	   FacesContext context = FacesContext.getCurrentInstance();
//	   context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid username or password", ""));
	   
	   
	   return "success";
   }
   
   /**
    * log-out user
    * @return
    */
   public String logout() {
	   log.log(Level.FINE, "Logout user " + userName);
       ((HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false)).invalidate();
       return "logout";
   }

   
   @PostConstruct
   public void initNewLogin() {
	   userName = "";
	   password = "";
	   userPrincipal = "";
   }
	
	public void setUserPrincipal(String uPrincipal) {
		//
	}
	public String getUserPrincipal() {
	        FacesContext context = FacesContext.getCurrentInstance();
	        ExternalContext externalContext = context.getExternalContext();
	        userPrincipal = externalContext.getUserPrincipal() != null ? externalContext.getUserPrincipal().toString() : "null";
	        return userPrincipal;
	}
	
	@Produces
	@Named
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	@Produces
	@Named
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public void getUser() {
		Principal principal = FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal();
    }

}
