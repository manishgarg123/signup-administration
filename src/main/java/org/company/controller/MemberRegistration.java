package org.company.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.Stateful;
import javax.enterprise.event.Event;
import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;

import org.company.model.Member;
import org.company.util.IConstant;
import org.company.util.MemberRegistrationCache;
import org.infinispan.Cache;

@Stateful
@Model
public class MemberRegistration {

   @Inject
   private Logger log;

   @Inject
   private EntityManager em;

   @Inject
   private Event<Member> memberEventSrc;

   private Member newMember;

   @Produces
   @Named
   public Member getNewMember() {
      return newMember;
   }

   public void setNewMember(Member newMember){
	   this.newMember = newMember;
   }
   
   /**
    * Registers a new member into the application.
    * @throws Exception
    */
   public void register() throws Exception {
      em.persist(newMember);
      memberEventSrc.fire(newMember);
      
      // add the member to cache containing requests pending for approval
      Cache cache = MemberRegistrationCache.getCache();
      if(!cache.containsKey(IConstant.NEW_REGISTRATION_CACHE)){
    	  // Initialise cache if required
    	  cache.put(IConstant.NEW_REGISTRATION_CACHE, new ArrayList());
      }
      ArrayList newMemberList = (ArrayList)cache.get(IConstant.NEW_REGISTRATION_CACHE);
      newMemberList.add(newMember);
      cache.put(IConstant.NEW_REGISTRATION_CACHE, newMemberList);
      initNewMember();
      log.log(Level.FINE, "Registering new member" + newMember.getName());
   }

   @PostConstruct
   public void initNewMember() {
      newMember = new Member();
   }
   
   /**
    * Approves the registers member.
    * @throws Exception
    */
   public void approve() throws Exception {
	   log.log(Level.FINE, "Approve member" + newMember.getId());
	   Cache cache = MemberRegistrationCache.getCache();
	   List newMemberList = (ArrayList)cache.get(IConstant.NEW_REGISTRATION_CACHE);
	   if(!cache.containsKey(IConstant.APPROVED_CACHE)){
	    	  cache.put(IConstant.APPROVED_CACHE, new ArrayList());
	      }
	   List approvedMemberList = (ArrayList)cache.get(IConstant.APPROVED_CACHE);
	   newMemberList.remove(newMember);
	   approvedMemberList.add(newMember);
	   cache.put(IConstant.NEW_REGISTRATION_CACHE, newMemberList);
	   cache.put(IConstant.APPROVED_CACHE, approvedMemberList);
   }
   
   public void reject() throws Exception {
	   log.log(Level.FINE, "Reject member" + newMember.getId());
	   Cache cache = MemberRegistrationCache.getCache();
	   List newMemberList = (ArrayList)cache.get(IConstant.NEW_REGISTRATION_CACHE);
	   if(!cache.containsKey(IConstant.REJECTED_CACHE)){
	    	  cache.put(IConstant.REJECTED_CACHE, new ArrayList());
	      }
	   List deniedMemberList = (ArrayList)cache.get(IConstant.REJECTED_CACHE);
	   newMemberList.remove(newMember);
	   deniedMemberList.add(newMember);
	   cache.put(IConstant.NEW_REGISTRATION_CACHE, newMemberList);
	   cache.put(IConstant.REJECTED_CACHE, deniedMemberList);
   }
}