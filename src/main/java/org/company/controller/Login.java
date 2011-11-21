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

// The @Stateful annotation eliminates the need for manual transaction demarcation
@Stateful
// The @Model stereotype is a convenience mechanism to make this a request-scoped bean that has an
// EL name
// Read more about the @Model stereotype in this FAQ:
// http://sfwk.org/Documentation/WhatIsThePurposeOfTheModelAnnotation
//@Named
//@SessionScoped
@Model
public class Login {

   @Inject
   private Logger log;

   @Inject
   private EntityManager em;

   private String userName;
   private String password;
   private String userPrincipal;
   
   public void login() throws Exception{
	   System.out.println("login() logObj="+log);
	   System.out.println("inside login method userName="+userName+", password="+password);
	   boolean loggedIn = false;
	   

   }
   
   @PostConstruct
   public void initNewMember() {
	   System.out.println("initNewMember");
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
		System.out.println("inside getUserName method");
		log.log(Level.ALL, "inside getUserName method");
		return userName;
	}
	public void setUserName(String userName) {
		System.out.println("inside setUserName method");
		log.log(Level.ALL, "inside setUserName method");
		this.userName = userName;
	}
	
	@Produces
	@Named
	public String getPassword() {
		System.out.println("inside getPassword method");
		log.log(Level.ALL, "inside getPassword method");
		return password;
	}
	public void setPassword(String password) {
		System.out.println( "inside setPassword method");
		log.log(Level.ALL, "inside setPassword method");
		this.password = password;
	}
}
