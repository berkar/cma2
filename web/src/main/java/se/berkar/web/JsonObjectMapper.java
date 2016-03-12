package se.berkar.web;

import java.io.IOException;
import java.io.InputStream;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module;

import se.berkar.common.helpers.EmptyHandler;

public class JsonObjectMapper {

	private final static ObjectMapper itsJsonMapper; // Can be reused, share globally

	// This can also be handled in a Provider, see example http://wiki.fasterxml.com/JacksonFAQJaxRs
	// But that will only be effective when Consume/Produce are defined on REST methods
	// Therefore this has to be set also for manual handling in the code through this API
	static {
		itsJsonMapper = new ObjectMapper();

		Hibernate4Module hibernateModule = new Hibernate4Module();
		hibernateModule.disable(Hibernate4Module.Feature.USE_TRANSIENT_ANNOTATION);
		itsJsonMapper.registerModule(hibernateModule);

		itsJsonMapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
		itsJsonMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
	}

	public static ObjectMapper getInstance() {
		return itsJsonMapper;
	}

	private JsonObjectMapper() {
	}

	public static <T> T read(String theJson, Class<T> theClass) throws IOException {
		return JsonObjectMapper.getInstance().readValue(theJson, theClass);
	}

	public static <T> T read(InputStream theInputStream, Class<T> theClass) throws IOException {
		return JsonObjectMapper.getInstance().readValue(theInputStream, theClass);
	}

	public static <T> T read(String thePath, InputStream theInputStream, Class<T> theClass) throws IOException {
		JsonNode aRoot = getInstance().readTree(theInputStream);
		if (EmptyHandler.isNotEmpty(thePath)) {
			return JsonObjectMapper.getInstance().readValue(aRoot.path(thePath).asText(), theClass);
		}
		// Fallback in case path is empty
		return read(theInputStream, theClass);
	}

	public static <T> String write(T theObject) throws IOException {
		return JsonObjectMapper.getInstance().writeValueAsString(theObject);
	}

}
