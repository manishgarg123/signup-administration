package org.company.util;

import java.io.IOException;
import java.io.Serializable;
import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.infinispan.Cache;
import org.infinispan.manager.DefaultCacheManager;
import org.infinispan.manager.EmbeddedCacheManager;

@Named
@ApplicationScoped
public class SignupRequestCache implements Serializable {

	private static EmbeddedCacheManager manager;
	private static Cache cache;
	
	@Inject
	private Logger log;
	 
	private static SignupRequestCache signupRequestCache;
	
	public static SignupRequestCache getInstance(){
		if(signupRequestCache == null){
			signupRequestCache = new SignupRequestCache();
		}
		return signupRequestCache;
	}
	
	/**
	 * return instance of cache object.
	 * @return
	 */
	public Cache getCache(){
		initCache();
		return cache;
	}
	
	/**
	 * initialise the cache and cache manager.
	 */
	private void initCache() {
		try {
			if(manager == null){
				manager = new DefaultCacheManager(IConstant.CACHE_CONFIG_FILE);
			}
			if(cache == null){
				cache = manager.getCache(IConstant.CACHE_STORE);
			}
		}
		catch(IOException e){
			e.printStackTrace();
		}
	}
	
}
