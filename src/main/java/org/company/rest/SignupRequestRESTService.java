package org.company.rest;

import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

import org.company.model.SignupRequest;
import org.company.util.IConstant;
import org.company.util.SignupRequestCache;
import org.infinispan.Cache;

/**
 * JAX-RS Example
 * 
 * This class produces a RESTful service for signup requests.
 */
@Path("/signupRequest")
@RequestScoped
public class SignupRequestRESTService {
   
   @Inject
   private Logger log;
   
   @POST
   @Consumes(MediaType.APPLICATION_JSON)
   @Path(value="/registerSignupRequest")
   public void registerSignupRequest(SignupRequest signupRequest) {
	      // add the member to cache containing requests pending for approval
	      Cache cache = SignupRequestCache.getInstance().getCache();
	      if(!cache.containsKey(IConstant.NEW_REGISTRATION_CACHE)){
	    	  // Initialise cache if required
	    	  cache.put(IConstant.NEW_REGISTRATION_CACHE, new ArrayList());
	      }
	      ArrayList newMemberList = (ArrayList)cache.get(IConstant.NEW_REGISTRATION_CACHE);
	      /*SignupRequest signupRequest = new SignupRequest();
	      signupRequest.setFirstName(firstName);
	      signupRequest.setLastName(lastName);
	      signupRequest.setEmail(email);
	      signupRequest.setCompany(company);
	      signupRequest.setPhoneNumber(phoneNumber);
	      signupRequest.setReferer(referer);*/
	      signupRequest.setDatetime(new Date());
	      signupRequest.setStatus(IConstant.NEW_STATUS);
	      newMemberList.add(signupRequest);
	      cache.put(IConstant.NEW_REGISTRATION_CACHE, newMemberList);
	      log.log(Level.ALL, "Creating new signup request for " + signupRequest.getFirstName() + " " + signupRequest.getLastName());
   }
   
}
