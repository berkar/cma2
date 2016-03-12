package se.berkar.web;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;

import com.fasterxml.jackson.databind.ObjectMapper;

@Provider
@Produces(MediaType.APPLICATION_JSON)
public class JacksonContextResolver implements ContextResolver<ObjectMapper> {
//	private ObjectMapper objectMapper;

	public JacksonContextResolver() throws Exception {
//		this.objectMapper = new ObjectMapper().configure(
//				DeserializationConfig.Feature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true
//		);
	}

	public ObjectMapper getContext(Class<?> objectType) {
		return JsonObjectMapper.getInstance();
	}
}
