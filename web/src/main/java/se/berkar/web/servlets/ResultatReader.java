package se.berkar.web.servlets;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.IllegalFormatException;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;

import se.berkar.model.Resultat;

import static java.util.stream.Collectors.toList;

public class ResultatReader {
	private AtomicInteger itsCounter = new AtomicInteger();

	public List<Resultat> fromStream(final InputStream inputStream) throws IllegalFormatException {
		BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
		return br.lines()
				.map(mapToResultat)
				.collect(toList());
	}

	public Function<String, Resultat> mapToResultat = (line) -> {
		String[] p = line.split(",");
		if (p != null && p.length == 2) {
			if ("dns".equalsIgnoreCase(p[1])) {
				Resultat aResultat = new Resultat(Integer.parseInt(p[0].trim()));
				aResultat.setTotaltime(10000);
				aResultat.setDidNotStart(true);
				aResultat.setFinishOrder(itsCounter.incrementAndGet());
				return aResultat; // DNS
			} else if ("dnf".equalsIgnoreCase(p[1])) {
				Resultat aResultat = new Resultat(Integer.parseInt(p[0].trim()));
				aResultat.setTotaltime(10001);
				aResultat.setDidNotFinish(true);
				aResultat.setFinishOrder(itsCounter.incrementAndGet());
				return aResultat; // DNF
			}
			// Finishtime in minutes and decimals thereof
			return new Resultat(Integer.parseInt(p[0].trim()), (int) Math.round(Double.parseDouble(p[1].trim()) * 60.0), itsCounter.incrementAndGet());
		}
		throw new IllegalArgumentException("Error on row: " + line);
	};

}
