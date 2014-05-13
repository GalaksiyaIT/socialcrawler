package com.galaksiya.social.linker;

import com.hp.hpl.jena.rdf.model.Resource;

/**
 * This interface is implemented by subclasses of Linker
 * 
 * @author etmen
 * 
 */
public interface ResourceFinder {
	/**
	 * This method is used in link method of {@link Linker} that use this to
	 * find resources to link into.
	 * 
	 * @param resourceName
	 *            name of resource that will be found.
	 * @return
	 */
	public Resource find(String resourceName);
}
