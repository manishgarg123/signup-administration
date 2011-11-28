package org.company.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Produces;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.company.model.SignupRequest;
import org.company.rest.SignupRequestRESTService;
import org.company.util.IConstant;
import org.company.util.SignupRequestCache;
import org.infinispan.Cache;

@Named
@SessionScoped
public class SignupRequestController implements Serializable {

   @Inject
   private Logger log;

   @Inject
   private SignupRequestRESTService signupRequestRESTService;

   private SignupRequest signupRequest;

   @Produces
   @Named
   public SignupRequest getSignupRequest() {
      return signupRequest;
   }

   public void setSignupRequest(SignupRequest signupRequest){
	   this.signupRequest = signupRequest;
   }
   
   /**
    * Registers a sign-up request into the application.
    * @throws Exception
    */
   public void register() throws Exception {
	   log.log(Level.ALL, "Register signup request for "+signupRequest.getFirstName() + " " + signupRequest.getLastName());
	   signupRequestRESTService.registerSignupRequest(signupRequest);
   }

   public String showSignupDetail() throws Exception {
	   return "success";
   }
   
   @PostConstruct
   public void initNewSignupRequest() {
	   signupRequest = new SignupRequest();
   }
   
   /**
    * Approves the sign-up request.
    * @throws Exception
    */
   public String approve() throws Exception {
	   log.log(Level.FINE, "Approving signup request for "+signupRequest.getFirstName() + " " + signupRequest.getLastName());
	   Cache cache = SignupRequestCache.getInstance().getCache();
	   List newRequestList = (ArrayList)cache.get(IConstant.NEW_REGISTRATION_CACHE);
	   if(!cache.containsKey(IConstant.APPROVED_CACHE)){
	    	 cache.put(IConstant.APPROVED_CACHE, new ArrayList());
	   }
	   List approvedRequestList = (ArrayList)cache.get(IConstant.APPROVED_CACHE);
	   List rejectedRequestList = (ArrayList)cache.get(IConstant.REJECTED_CACHE);
	   if(approvedRequestList.contains(signupRequest)){
		   newRequestList.remove(signupRequest);
	   } else if(rejectedRequestList.contains(signupRequest)){
		   rejectedRequestList.remove(signupRequest);
	   }
	   signupRequest.setStatus(IConstant.APPROVED_STATUS);
	   approvedRequestList.add(signupRequest);
	   cache.put(IConstant.NEW_REGISTRATION_CACHE, newRequestList);
	   cache.put(IConstant.APPROVED_CACHE, approvedRequestList);
	   FacesContext context = FacesContext.getCurrentInstance();
	   context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, signupRequest.getFirstName() + " " + signupRequest.getLastName() + " approved successfully", ""));
	   return "success";
   }
   
   /**
    * Rejects the sign-up request.
    * @throws Exception
    */
   public String reject() throws Exception {
	   Cache cache = SignupRequestCache.getInstance().getCache();
	   List newMemberList = (ArrayList)cache.get(IConstant.NEW_REGISTRATION_CACHE);
	   if(!cache.containsKey(IConstant.REJECTED_CACHE)){
	    	  cache.put(IConstant.REJECTED_CACHE, new ArrayList());
	      }
	   List deniedMemberList = (ArrayList)cache.get(IConstant.REJECTED_CACHE);
	   if(newMemberList.contains(signupRequest)){
		   newMemberList.remove(signupRequest);
	   }
	   deniedMemberList.add(signupRequest);
	   signupRequest.setStatus(IConstant.REJECTED_STATUS);
	   cache.put(IConstant.NEW_REGISTRATION_CACHE, newMemberList);
	   cache.put(IConstant.REJECTED_CACHE, deniedMemberList);
	   log.log(Level.FINE, "Reject member : " + signupRequest.getFirstName() + " " + signupRequest.getLastName());
   
	   FacesContext context = FacesContext.getCurrentInstance();
	   context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, signupRequest.getFirstName() + " " + signupRequest.getLastName() + " rejected successfully", ""));
	   return "success";
   }
   
   @Produces
   @Named
   public List<SignupRequest> getNewSignupRequests() {
	   // return requests from cache containing requests pending for approval
	   return (ArrayList)SignupRequestCache.getInstance().getCache().get(IConstant.NEW_REGISTRATION_CACHE);
   }
   
   @Produces
   @Named
   public List<SignupRequest> getApprovedSignupRequests() {
	   return (ArrayList)SignupRequestCache.getInstance().getCache().get(IConstant.APPROVED_CACHE);
   }
   
   @Produces
   @Named
   public List<SignupRequest> getDeniedSignupRequests() {
	   return (ArrayList)SignupRequestCache.getInstance().getCache().get(IConstant.REJECTED_CACHE);
   }
}
