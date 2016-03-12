package se.berkar;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import se.berkar.qualifiers.CmaLogger;
import se.berkar.qualifiers.CmaServiceDB;

import org.jboss.logging.Logger;

public class Resources {

	@PersistenceContext(unitName = "cma-db")
	private EntityManager itsDatabase;

/*
	@Inject
	private CmaConfiguration itsConfiguration;
*/

	@Produces
	@CmaLogger
	private Logger createLogger(InjectionPoint injectionPoint) {
		return Logger.getLogger(injectionPoint.getMember().getDeclaringClass().getName());
	}

	@Produces
	@CmaServiceDB
	private EntityManager getIdentityDB() {
		return itsDatabase;
	}

/*
	@Produces
	private CmaConfiguration getConfiguration() {
		return itsConfiguration;
	}
*/

}
