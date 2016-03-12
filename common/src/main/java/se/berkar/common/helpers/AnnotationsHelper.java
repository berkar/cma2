package se.berkar.common.helpers;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: berkar
 * Date: 2007-nov-26
 * Time: 16:06:55
 */
public class AnnotationsHelper {

	private AnnotationsHelper() {
	}

	/**
	 * The attribute name for annotations with a single element.
	 */
	public static final String ANNOTATION_VALUE = "value";

	/**
	 * <p/>
	 * Get a single {@link Annotation} of <code>annotationType</code> from the
	 * supplied {@link Class}, traversing its interfaces and super classes
	 * if no annotation can be found on the given class itself.
	 * </p>
	 * <p/>
	 * This method explicitly handles class-level annotations which are not
	 * declared as {@link java.lang.annotation.Inherited inherited} as well as
	 * annotations on interfaces.
	 * </p>
	 *
	 * @param clazz          the class to look for annotations on
	 * @param annotationType the annotation class to look for
	 * @return the annotation of the given type found, or <code>null</code>
	 */
	public static <A extends Annotation> A findClassAnnotation(final Class<?> clazz, final Class<A> annotationType) {
		A annotation = clazz.getAnnotation(annotationType);
		if (annotation != null) {
			return annotation;
		}
		for (Class<?> ifc : clazz.getInterfaces()) {
			annotation = findClassAnnotation(ifc, annotationType);
			if (annotation != null) {
				return annotation;
			}
		}
		if (clazz.getSuperclass() == null || Object.class.equals(clazz.getSuperclass())) {
			return null;
		}
		return findClassAnnotation(clazz.getSuperclass(), annotationType);
	}

	/**
	 * Retrieve the given annotation's attributes as a Map.
	 *
	 * @param annotation the annotation to retrieve the attributes for
	 * @return the Map of annotation attributes, with attribute names as keys
	 *         and corresponding attribute values as values
	 */
	public static Map<String, Object> getAnnotationAttributes(final Annotation annotation) {
		final Map<String, Object> attrs = new HashMap<String, Object>();
		final Method[] methods = annotation.annotationType().getDeclaredMethods();
		for (final Method method : methods) {
			if (method.getParameterTypes().length == 0 && method.getReturnType() != void.class) {
				try {
					attrs.put(method.getName(), method.invoke(annotation));
				} catch (final Exception ex) {
					throw new IllegalStateException("Could not obtain annotation attribute values", ex);
				}
			}
		}
		return attrs;
	}

	public static Object getFieldSingleValue(Class<? extends Annotation> theAnnotation, Object theObject) {
		Map<String, Object> aResult = getFieldValues(theAnnotation, theObject);
		if (aResult.size() > 0) {
			// Pick the first one ...
			return aResult.values().iterator().next();
		}
		return null;
	}

	public static Map<String, Object> getFieldValues(Class<? extends Annotation> theAnnotation, Object theObject) {
		try {
			Map<String, Object> aResult = new HashMap<String, Object>();
			// Get all fields that has correct theAnnotation
			Field[] aFieldArray = theObject.getClass().getDeclaredFields();
			for (Field aField : aFieldArray) {
				if (aField.isAnnotationPresent(theAnnotation)) {
					aField.setAccessible(true);
					// Create key, strip away 'its'
					String aKey = StringHelper.stripPrefix(aField.getName(), "its");
					// Get the value
					Object aValue = aField.get(theObject);
					// Save to result
					aResult.put(aKey, aValue);
				}
			}
			return aResult;
		} catch (IllegalAccessException e) {
			throw new IllegalStateException("Could not get field values on " + theObject + "based on theAnnotation " + theAnnotation, e);
		}
	}

	public static Object getMethodSingleValue(Class<? extends Annotation> theAnnotation, Object theObject) {
		Map<String, Object> aResult = getMethodValues(theAnnotation, theObject);
		if (aResult.size() > 0) {
			// Pick the first one ...
			return aResult.values().iterator().next();
		}
		return null;
	}

	public static Map<String, Object> getMethodValues(Class<? extends Annotation> theAnnotation, Object theObject) {
		try {
			Map<String, Object> aResult = new HashMap<String, Object>();
			// Get all methods that has correct theAnnotation
			Method[] aMethodArray = theObject.getClass().getMethods();
			for (Method aMethod : aMethodArray) {
				if (aMethod.isAnnotationPresent(theAnnotation)) {
					aMethod.setAccessible(true);
					// Create key, strip away 'get'
					String aKey = StringHelper.stripPrefix(aMethod.getName(), "get");
					// Get the value
					Object aValue = aMethod.invoke(theObject);
					// Save to result
					aResult.put(aKey, aValue);
				}
			}
			return aResult;
		} catch (IllegalAccessException e) {
			throw new IllegalStateException("Could not get method values on " + theObject + "based on theAnnotation " + theAnnotation, e);
		} catch (InvocationTargetException e) {
			throw new IllegalStateException("Could not get method values on " + theObject + "based on theAnnotation " + theAnnotation, e);
		}
	}

	public static Method getMethod(Class<? extends Annotation> theAnnotation, Class<?> theClass) {
		Collection<Method> aResult = getMethods(theAnnotation, theClass);
		if (aResult.size() > 0) {
			// Pick the first one ...
			return aResult.iterator().next();
		}
		return null;
	}

	public static Collection<Method> getMethods(Class<? extends Annotation> theAnnotation, Class<?> theClass) {
		List<Method> aResult = new ArrayList<Method>();
		// Get all methods that has correct theAnnotation
		Method[] aMethodArray = theClass.getDeclaredMethods();
		for (Method aMethod : aMethodArray) {
			if (aMethod.isAnnotationPresent(theAnnotation)) {
				// Save to result
				aResult.add(aMethod);
			}
		}
		if (theClass.getSuperclass() == null || Object.class.equals(theClass.getSuperclass())) {
			// This is the root! No need to go any further
		} else {
			aResult.addAll(getMethods(theAnnotation, theClass.getSuperclass()));
		}
		return aResult;
	}

	public static Collection<Field> getFields(Class<? extends Annotation> theAnnotation, Class<?> theClass) {
		List<Field> aResult = new ArrayList<Field>();
		// Get all fields that has correct theAnnotation
		Field[] aFieldArray = theClass.getDeclaredFields();
		for (Field aField : aFieldArray) {
			if (aField.isAnnotationPresent(theAnnotation)) {
				aField.setAccessible(true);
				// Save to result
				aResult.add(aField);
			}
		}
		if (theClass.getSuperclass() == null || Object.class.equals(theClass.getSuperclass())) {
			// This is the root! No need to go any further
		} else {
			aResult.addAll(getFields(theAnnotation, theClass.getSuperclass()));
		}
		return aResult;
	}
}

