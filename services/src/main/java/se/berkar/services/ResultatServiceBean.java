package se.berkar.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.ws.rs.HEAD;
import javax.ws.rs.Path;

import se.berkar.CmaConfiguration;
import se.berkar.model.Resultat;
import se.berkar.model.Start;
import se.berkar.qualifiers.CmaLogger;
import se.berkar.qualifiers.CmaServiceDB;

import org.jboss.logging.Logger;

@Stateless
@Path("resultat")
public class ResultatServiceBean {

	@Inject
	@CmaLogger
	private transient Logger itsLog;

	@Inject
	@CmaServiceDB
	private EntityManager itsEntityManager;

	@Inject
	private StartServiceBean itsStartService;

	@Inject
	private CmaConfiguration itsConfiguration;

	@HEAD
	@Path("/ping")
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public boolean getStatus() {
		itsLog.trace("Status Ping received!");
		return true;
	}

	public void upload(List<Resultat> theResultatList) {

		final Map<Integer, Start> aStartMap = new HashMap<>();

		// Calculate results
		theResultatList.stream().forEach(theResultat -> {
			try {
				Start aStart = itsStartService.getByStartnummer(theResultat.getStartnumber());
				aStartMap.put(theResultat.getStartnumber(), aStart);
				if (theResultat.getFinishtime() != null) {
					theResultat.setTotaltime(theResultat.getFinishtime() - aStart.getStarttime());
				}
			} catch (Exception e) {
				itsLog.error("Error when handling startnumber: " + theResultat.getStartnumber(), e);
			}
		});

		// Sort on totaltime
		theResultatList.sort((p1, p2) -> p1.getTotaltime().compareTo(p2.getTotaltime()));

		//*********************
		// Placeringar
		//*********************

		// Placering Totalt
		final AtomicInteger aPlaceringTotal = new AtomicInteger();
		theResultatList.stream().forEach(theResult -> {
			theResult.setPlaceringTotal(aPlaceringTotal.incrementAndGet());
		});

		// Placering Herrar (gender)
		final AtomicInteger aPlaceringGenderHerrar = new AtomicInteger();
		theResultatList.stream().forEach(theResult -> {
			Start aStart = aStartMap.get(theResult.getStartnumber());
			if ("herrar".equals(aStart.getGender())) {
				theResult.setPlaceringGender(aPlaceringGenderHerrar.incrementAndGet());
			}
		});

		// Placering Damer (gender)
		final AtomicInteger aPlaceringGenderDamer = new AtomicInteger();
		theResultatList.stream().forEach(theResult -> {
			Start aStart = aStartMap.get(theResult.getStartnumber());
			if ("damer".equals(aStart.getGender())) {
				theResult.setPlaceringGender(aPlaceringGenderDamer.incrementAndGet());
			}
		});

		// Placering Elit (klass)
		final AtomicInteger aPlaceringClazzElit = new AtomicInteger();
		theResultatList.stream().forEach(theResult -> {
			Start aStart = aStartMap.get(theResult.getStartnumber());
			if ("elit".equals(aStart.getClazz().getKey())) {
				theResult.setPlaceringClazz(aPlaceringClazzElit.incrementAndGet());
			}
		});

		// Placering Motion (klass)
		final AtomicInteger aPlaceringClazzMotion = new AtomicInteger();
		theResultatList.stream().forEach(theResult -> {
			Start aStart = aStartMap.get(theResult.getStartnumber());
			if ("elit".equals(aStart.getClazz().getKey())) {
				theResult.setPlaceringClazz(aPlaceringClazzMotion.incrementAndGet());
			}
		});

		// Medalj
		theResultatList.stream().forEach(theResult -> {
			if (theResult.getTotaltime() <= 60 * 60) {
				theResult.setMedalj("gold");
			} else if (theResult.getTotaltime() <= 75 * 60) {
				theResult.setMedalj("silver");
			} else if (theResult.getTotaltime() <= 90 * 60) {
				theResult.setMedalj("bronze");
			}
		});

		// Delete all in Resultat, and add content from List
		itsEntityManager.createQuery("DELETE FROM Resultat").executeUpdate();

		// Save the result
		theResultatList.stream().forEach(theResultat -> itsEntityManager.persist(theResultat));
	}
}
