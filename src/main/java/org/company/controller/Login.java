package org.company.controller;

import java.awt.event.ActionEvent;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.Stateful;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Event;
import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.servlet.http.HttpSession;

import org.company.model.Member;

import com.sun.xml.internal.bind.v2.TODO;

@Stateful
@Model
/**
 * This class is used for authenticating and authorising user's login request.
 */
public class Login {

   @Inject
   private Logger log;

   private String userName;
   private String password;
   private String userPrincipal;
   
   /**
    * log-in user request.
    * @return	success in case login is successful.
    * @throws Exception
    */
   public String login() throws Exception{
	   log.log(Level.FINE, "Login attempt for user " + userName);
	   boolean loggedIn = false;
	   // TODO: authentication and authorization
	   return "success";
   }
   
   @PostConstruct
   public void initNewMember() {
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
}
