package se.berkar.common.helpers;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Date;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: berkar
 * Date: 2011-11-29
 * Time: 14:13
 */
public class EmptyHandler {
	public static boolean isEmpty(Object obj) {
		if (obj == null) {
			return true;
		} else if (obj instanceof String) {
			return ((String) obj).length() <= 0;
		} else if (obj instanceof Date) {
			return ((Date) obj).getTime() <= 0;
		} else if (obj.getClass().isArray()) {
			return Array.getLength(obj) <= 0;
		} else if (obj instanceof Collection) {
			return ((Collection) obj).size() <= 0;
		} else if (obj instanceof Iterator) {
			return !((Iterator) obj).hasNext();
		} else if (obj instanceof Map) {
			return ((Map) obj).entrySet().size() <= 0;
		} else if (obj instanceof Enumeration) {
			return !((Enumeration) obj).hasMoreElements();
		}
		return false; // Object not null!
	}

	public static boolean isNotEmpty(Object obj) {
		return !isEmpty(obj);
	}
}
