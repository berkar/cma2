package se.berkar.common.helpers;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by IntelliJ IDEA. User: berkar Date: 2009-maj-06 Time: 15:12:04
 */
public class ReflectionHelper {

    public synchronized static Method getReadMethod(Object theObject, String theMethod, Class... theArgument) {
        return getReadMethod(theObject.getClass(), theMethod, theArgument);
    }

    public synchronized static Method getReadMethod(Class<?> theClass, String theMethod, Class... theArgument) {
        if (!theMethod.startsWith("get")) {
            theMethod = StringHelper.addPrefix(theMethod, "get");
        }
        return getMethod(theClass, theMethod, theArgument);
    }

    /**
     * @param theObject The object to fetch all read methods from
     * @return All methods starting with get
     */
    public synchronized static List<Method> getReadMethods(Object theObject) {
        return getReadMethods(theObject.getClass());
    }

    /**
     * @param theClass The Class to fetch all read methods from
     * @return All methods starting with get
     */
    public synchronized static List<Method> getReadMethods(Class<?> theClass) {
        List<Method> aMethodList = new ArrayList<Method>();
        Method[] aMethods = theClass.getMethods();
        if (aMethods != null && aMethods.length > 0) {
            for (Method aMethod : aMethods) {
                if (aMethod.getName().startsWith("get")) {
                    aMethodList.add(aMethod);
                }
            }
        }
        return aMethodList;
    }

    public synchronized static Method getWriteMethod(Object theObject, String theMethod, Class... theArgument) {
        return getWriteMethod(theObject.getClass(), theMethod, theArgument);
    }

    public synchronized static Method getWriteMethod(Class<?> theClass, String theMethod, Class... theArgument) {
        if (!theMethod.startsWith("set")) {
            theMethod = StringHelper.addPrefix(theMethod, "set");
        }
        return getMethod(theClass, theMethod, theArgument);
    }

    public synchronized static Method getMethod(Object theObject, String theMethod, Class... theArgument) {
        return getMethod(theObject.getClass(), theMethod, theArgument);
    }

    public synchronized static Method getMethod(Class<?> theClass, String theMethod, Class... theArgument) {
        Method aMethod = null;
        try {
            aMethod = theClass.getMethod(theMethod, theArgument);
        } catch (NoSuchMethodException e) {
            /* ignore */
        }
        return aMethod;
    }

    public synchronized static Method getDeclaredMethod(Object theObject, String theMethod, Class... theArgument) {
        return getDeclaredMethod(theObject.getClass(), theMethod, theArgument);
    }

    public synchronized static Method getDeclaredMethod(Class<?> theClass, String theMethod, Class... theArgument) {
        Method aMethod = null;
        try {
            Class<?> aClass = theClass;
            while (aClass != null) {
                // Required to use  getDeclaredMethod since I need access to protected scope also
                aMethod = aClass.getDeclaredMethod(theMethod, theArgument);
                if (aMethod != null) {
                    break;
                }
                aClass = aClass.getSuperclass();
            }

        } catch (NoSuchMethodException e) {
			/* ignore */
        }
        return aMethod;
    }

    public synchronized static Object invokeRead(Object theObject, String theMethod) {
        Method aMethod = getReadMethod(theObject, theMethod);
        return invoke(theObject, aMethod);
    }

    public synchronized static Object invokeWrite(Object theObject, String theMethod, Object... theParams) {
        List<Class<?>> aParameterTypesList = new ArrayList<Class<?>>();
        for (Object aParam : theParams) {
            aParameterTypesList.add(aParam.getClass());
        }
        Method aMethod = getWriteMethod(theObject, theMethod, aParameterTypesList.toArray(new Class[aParameterTypesList.size()]));
        return invoke(theObject, aMethod, theParams);
    }

    public synchronized static Object invoke(Object theObject, String theMethod) {
        Method aMethod = getMethod(theObject, theMethod);
        return invoke(theObject, aMethod);
    }

    public synchronized static Object invoke(Object theObject, Method theMethod, Object... theParams) {
        Object aResult = null;
        try {
            theMethod.setAccessible(true);
            aResult = theMethod.invoke(theObject, theParams);
        } catch (Exception e) {
			/* ignore */
        }
        return aResult;
    }

    public synchronized static List<Field> getFields(Class theClass) {
        return getFields(theClass, false);
    }

    public synchronized static List<Field> getFields(Class theClass, boolean theRecursive) {
        List<Field> aResult = new ArrayList<Field>();
        // Get all fields that has correct theAnnotation
        Field[] aFieldArray = theClass.getDeclaredFields();
        for (Field aField : aFieldArray) {
            aField.setAccessible(true);
            // Save to result
            aResult.add(aField);
        }
        if (theClass.getSuperclass() == null || Object.class.equals(theClass.getSuperclass())) {
            // This is the root! No need to go any further
        } else if (theRecursive) {
            aResult.addAll(getFields(theClass.getSuperclass(), theRecursive));
        }
        return aResult;
    }

    /**
     * Convenience method for checking all get'er methods for content
     *
     * @param theObject The object to be tested
     * @return true if all get|is methods are null or when collections size is 0 (zero)
     */
    public synchronized static boolean isEmpty(Object theObject, String... theExcludes) {
        List<Method> aMethodList = ReflectionHelper.getReadMethods(theObject);

        boolean aExcludeFlag = false;
        if (aMethodList != null && aMethodList.size() > 0) {
            for (Method aMethod : aMethodList) {
                if (theExcludes != null && theExcludes.length > 0) {
                    // Check against exclude list
                    for (String aExclude : theExcludes) {
                        if (aMethod.getName().equals(aExclude)) {
                            aExcludeFlag = true;
                            break;
                        }
                    }
                    if (aExcludeFlag) {
                        aExcludeFlag = false;
                        continue;
                    }
                }
                Object aValue = invoke(theObject, aMethod);
                if (aValue instanceof Collection) {
                    if (((Collection) aValue).size() > 0) {
                        return false;
                    }
                } else if (aValue != null) {
                    return false;
                }
            }
        }
        return true;
    }
}
