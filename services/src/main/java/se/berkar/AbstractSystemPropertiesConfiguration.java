package se.berkar;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;

import se.berkar.common.helpers.ReflectionHelper;
import se.berkar.qualifiers.CmaLogger;

import org.jboss.logging.Logger;

public abstract class AbstractSystemPropertiesConfiguration {

	@Inject
	@CmaLogger
	protected Logger itsLog;

	@PostConstruct
	public void init() {
	}

	@PreDestroy
	public void sync() {
	}

	public abstract String getPrefix();

	protected String getKey(ApplicationProperty theKey) {
		return getPrefix() + "." + theKey.key();
	}

	/**
	 * Convenience method for handling of prefix + key
	 * @param theKey The key, without prefix
	 * @return Object value
	 */
	protected String getValue(ApplicationProperty theKey) {
		return System.getProperty(getKey(theKey), theKey.defaultValue());
	}

	protected void setValue(ApplicationProperty theKey, String theValue) {
		System.setProperty(getKey(theKey), theValue);
	}

	/**
	 * Will record all properties of type ApplicationProperty in the implementing class
	 * @return Map of values for each found ApplicationProperty
	 * @throws Exception
	 * @see ApplicationProperty
	 */
	public Map<String, Object> snapshot() throws Exception {
		ApplicationProperty aProperty;
		Map<String, Object> aMap = new HashMap<>();
		List<Field> aList = ReflectionHelper.getFields(this.getClass(), true);
		for (Field aField : aList) {
			if (ApplicationProperty.class.isAssignableFrom(aField.getType())) {
				aProperty = (ApplicationProperty) aField.get(this);
				Object aValue = getValue(aProperty);
				aMap.put(getKey(aProperty), aValue);
			}
		}
		return aMap;
	}
}
