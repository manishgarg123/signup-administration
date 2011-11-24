package org.company.util;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.inject.Inject;

import org.infinispan.Cache;
import org.infinispan.manager.DefaultCacheManager;
import org.infinispan.manager.EmbeddedCacheManager;

public class MemberRegistrationCache {

	@Inject
	private static Logger log;
	
	static EmbeddedCacheManager manager;
	static Cache cache;
	
	/**
	 * return instance of cache object.
	 * @return
	 */
	public static Cache getCache(){
		initCache();
		return cache;
	}
	
	/**
	 * initialise the cache and cache manager.
	 */
	private static void initCache() {
		try {
			if(manager == null){
				manager = new DefaultCacheManager(IConstant.CACHE_CONFIG_FILE);
			}
			if(cache == null){
				cache = manager.getCache(IConstant.CACHE_STORE);
			}
		}
		catch(IOException e){
			log.log(Level.SEVERE, e.getMessage(), e);
			e.printStackTrace();
			manager = new DefaultCacheManager();
		}
	}
}
