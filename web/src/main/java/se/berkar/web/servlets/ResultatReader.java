package se.berkar.web.servlets;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.IllegalFormatException;
import java.util.List;
import java.util.function.Function;

import se.berkar.model.Resultat;

import static java.util.stream.Collectors.toList;

public class ResultatReader {
	public List<Resultat> fromStream(final InputStream inputStream) throws IllegalFormatException {
		BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
		return br.lines()
				.map(mapToResultat)
				.collect(toList());
	}

	public static Function<String, Resultat> mapToResultat = (line) -> {
		String[] p = line.split(",");
		return new Resultat(Integer.parseInt(p[0].trim()), Double.parseDouble(p[1].trim()));
	};

}
