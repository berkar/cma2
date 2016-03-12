package se.berkar.web;

import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

/**
 * A class extending {@link javax.ws.rs.core.Application} and annotated with @ApplicationPath is the Java EE 6
 * "no XML" approach to activating JAX-RS.
 *
 * <p>
 * Resources are served relative to the servlet path specified in the {@link javax.ws.rs.ApplicationPath}
 * annotation.
 * </p>
 */
@ApplicationPath("/rest")
public class JaxRsActivator extends Application {
	/* class body intentionally left blank */

	public JaxRsActivator() {
		super();
	}

	@Override
	public Set<Class<?>> getClasses() {
		return super.getClasses();
	}

	@Override
	public Set<Object> getSingletons() {
		return super.getSingletons();
/*
		Set<Object> singletons = new HashSet<>();
		singletons.add(new JacksonConfigurator());
//		singletons.add(new JacksonJsonProvider());
//		singletons.add(new JAXBXmlRootElementProvider());
//		singletons.add(new XmlJAXBContextFinder());
		return singletons;
*/
	}

}
