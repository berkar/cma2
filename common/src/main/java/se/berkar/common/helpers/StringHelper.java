package se.berkar.common.helpers;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * Helper class for handling String's
 */
@SuppressWarnings({"unused", "unchecked"})
public final class StringHelper {

    private StringHelper() {
        // Empty default constructor
    }

    /**
     * Returns null or a trimmed uppercase string.
     *
     * @param string ...
     * @return String ...
     */
    public static String toUpperOrNull(String string) {
        if (null != string) {
            return string.toUpperCase().trim();
        }
        return null;
    }

    /**
     * Returns the count left characters from string str
     *
     * @param str   ...
     * @param count ...
     * @return ...
     */
    public static String left(String str, int count) {
        if ((str != null) && (str.length() > count)) {
            return str.substring(0, count);
        }
        return str;
    }

    /**
     * Chomp the string to count characters long with "..." at the end to indicate that a chomp was made.
     *
     * @param str   String to be chomp'ed
     * @param count Max size of returned string, including trailing '...'
     * @return ...
     */
    public static String chomp(String str, int count) {
        if ((str != null) && (count > 3) && (str.length() > (count - 3))) {
            return left(str, count - 3) + "...";
        } else {
            return str;
        }
    }

    /**
     * Add a specific character until the String is a specific length
     *
     * @param str The original string, if this String object is null this method will return null
     * @param ch  The character to add at the end of the string
     * @param len The length the string should be
     * @return A new string with a number of characters appended at the end so it gets the specified length
     */
    public static String addCharUntilLength(String str, char ch, long len) {
        if (str != null) {
            StringBuffer sb = new StringBuffer(str);
            for (int i = str.length(); i < len; i++) {
                sb.append(ch);
            }
            return sb.toString();
        } else {
            return null;
        }
    }

    /**
     * Inserts a specific character in the beginning of string until the String is a specific length
     *
     * @param str The original string, if this String object is null this method will return null
     * @param ch  The character to insert at the beginning of the string
     * @param len The length the string should be
     * @return A new string with a number of characters inserted at the beginning so it gets the specified length
     */
    public static String insertCharUntilLength(String str, char ch, long len) {
        if (str != null) {
            StringBuffer sb = new StringBuffer(str);
            for (int i = str.length(); i < len; i++) {
                sb.insert(0, ch);
            }
            return sb.toString();
        } else {
            return null;
        }
    }

    /**
     * If the specified postfix exists at the end of the string shorten the string at the end with the length of the postfix. It will try to remove the postfix
     * until it no longer matches the end of the string.
     *
     * @param str     The string to shorten
     * @param postfix The string will be shorten as long as the string end matches this prefix
     * @return The shorten string
     */
    public static String removeFromEnd(String str, String postfix) {
        if (str != null) {
            StringBuffer sb = new StringBuffer(str);
            while (sb.toString().endsWith(postfix)) {
                sb.setLength(sb.length() - postfix.length());
            }
            return sb.toString();
        } else {
            return null;
        }
    }

    public static String toString(Object obj) {
        return toString(obj, "");
    }

    public static String toString(Object obj, String indent) {
        StringBuffer sb = new StringBuffer();
        // Check for null values
        if (obj == null) {
            return "{null}";
        }
        Iterator iterator = null;
        // If parameter is some kind of Collection, construct an iterator for it. Otherwise do nothing
        if (obj.getClass().isArray()) {
            try {
                // If we're lucky, it is an array of objects
                // that we can iterate over with no copying
                iterator = Arrays.asList((Object[]) obj).iterator();
            } catch (ClassCastException e) {
                // Rats -- It is an array of primitives
                int length = Array.getLength(obj);
                ArrayList c = new ArrayList(length);
                for (int i = 0; i < length; i++) {
                    c.add(Array.get(obj, i));
                }
                iterator = c.iterator();
            }
        } else if (obj instanceof Collection) {
            iterator = ((Collection) obj).iterator();
        } else if (obj instanceof Iterator) {
            iterator = (Iterator) obj;
        } else if (obj instanceof Map) {
            iterator = ((Map) obj).entrySet().iterator();
        } else if (obj instanceof Enumeration) {
            List list = new ArrayList();
            for (Enumeration e = (Enumeration) obj; e.hasMoreElements(); ) {
                list.add(e.nextElement());
            }
            iterator = list.iterator();
        }
        // Take care of either created iterator or use the parameter itself
        if (iterator != null) {
            int i = 0;
            sb.append("{\r\n");
            while (iterator.hasNext()) {
                Object o = iterator.next();
                sb.append(indent).append("[").append(i).append("]=");
                // Take care of string handling recursive
                sb.append(StringHelper.toString(o, indent + "  "));
                i++;
            }
            sb.append(indent).append("}\r\n");
        } else {
            if (obj instanceof Date) {
                sb.append(DateHelper.format((Date) obj, "yyyy-MM-dd HH:mm:ss.SSS"));
            } else if (obj.getClass().getName().startsWith("java.lang")) {
                sb.append(obj.toString());
            } else {
                sb.append("{\r\n");
                sb.append(StringHelper.reflect(obj, indent));
                sb.append(indent).append("}\r\n");
            }
        }
        // Return created value
        return sb.toString();
    }

    private static String reflect(Object object, String indent) {
        StringBuffer sb = new StringBuffer();
        Method[] m = object.getClass().getMethods();
        for (Method method : m) {
            // Find and invoke public methods starting with "get" or "is"
            if (Modifier.isPublic(method.getModifiers()) && (method.getName().startsWith("get") || method.getName().startsWith("is"))) {
                if (!"getClass".equals(method.getName())) {
                    try {
                        Object value = method.invoke(object);
                        String stringValue = String.valueOf(value);
                        if (method.getName().startsWith("get")) {
                            if (value == null) {
                                //sb.append(indent + method.getName().substring(3) + "={null}\r\n");
                            } else {
                                if (value instanceof Date) {
                                    sb.append(indent).append(method.getName().substring(3)).append("=")
                                        .append(DateHelper.format((Date) value, "yyyy-MM-dd HH:mm:ss.SSS")).append("\r\n");
                                } else if (value.getClass().getName().startsWith("java.lang")) {
                                    sb.append(indent).append(method.getName().substring(3)).append("=").append(stringValue).append("\r\n");
                                } else {
                                    // Call for recursive behaviour
                                    sb.append(indent).append(method.getName().substring(3)).append("=").append(StringHelper.toString(value, indent + "  "))
                                        .append("\r\n");
                                }
                            }
                        }
                        if (method.getName().startsWith("is")) {
                            if (value == null) {
                                //sb.append(indent + method.getName().substring(2) + "={null}\r\n");
                            } else {
                                if (value.getClass().getName().startsWith("java.lang")) {
                                    sb.append(indent).append(method.getName().substring(2)).append("=").append(stringValue).append("\r\n");
                                } else {
                                    // Call for recursive behaviour
                                    sb.append(indent).append(method.getName().substring(2)).append("=").append(StringHelper.toString(value, indent + "  "))
                                        .append("\r\n");
                                }
                            }
                        }
                    } catch (Exception e) {
                        sb.append(indent).append("(Exception when reflecting ").append(method.getName()).append("! => ").append(e.getMessage()).append(")\r\n");
                    }
                }
            }
        }
        return sb.toString();
    }

    public static String getAsString(Object theObject, String theDelim) {
        if (theObject == null) {
            return ("");
        }
        boolean aFlag = false;
        StringBuffer aBuffer = new StringBuffer(128);
        if (theObject.getClass().isArray()) {
            aBuffer.append("[");
            if (Array.getLength(theObject) > 0) {
                for (Object aArg : (Object[]) theObject) {
                    if (aFlag) {
                        aBuffer.append(theDelim);
                    }
                    aBuffer.append(getAsString(aArg, theDelim));
                    aFlag = true;
                }
            }
            aBuffer.append("]");
        } else {
            aBuffer.append(theObject);
        }
        return aBuffer.toString();
    }

    public static String normalizeCivicNo(String civicNo) {
        if (EmptyHandler.isNotEmpty(civicNo)) {
            if (civicNo.indexOf("-") == -1) {
                // Normalize civic reg no
                if (civicNo.length() == 10) {
                    // No century included, format is supposed to be YYMMDDXXXX
                    return civicNo.substring(0, 6) + "-" + civicNo.substring(6);
                }
                if (civicNo.length() == 12) {
                    // Century included, format is supposed to be YYYYMMDDXXXX
                    return civicNo.substring(0, 8) + "-" + civicNo.substring(8);
                }
            }
        }
        return civicNo;
    }

    /**
     * Create a file name that has no forbidden characters in. Characters filtered are: \:<>|"/*?
     *
     * @param theName        The filename to filter
     * @param theReplaceChar The character to use instead. Note! This has to be a legal character! It will not be checked
     * @return String
     */
    public static String createLegalFileName(String theName, String theReplaceChar) {
        if (theReplaceChar == null) {
            theReplaceChar = "_";
        }
        // Remove all forbidden characters, \ / : * ? " < > |, and replace them with theReplaceChar
        return theName.replaceAll("[\\\\:<>|\"/*?]", theReplaceChar);
    }


    /**
     * Checks if a certain string contains a certain char.
     *
     * @param str - the string.
     * @param ch  - the char.
     * @return true if str contains ch.
     */
    public static boolean containsChar(String str, char ch) {
        return str != null && str.indexOf(ch) != -1;
    }


    /**
     * Checks if a certain string contains a certain any of the chars in chars.
     *
     * @param str   - the string.
     * @param chars - the chars.
     * @return true if str contains any of chars in chars.
     */
    public static boolean containsChars(String str, String chars) {
        return containsChars(str, chars.toCharArray());
    }


    /**
     * Checks if a certain string contains a certain any of the chars in chars.
     *
     * @param str   - the string.
     * @param chars - the chars.
     * @return true if str contains any of chars in chars.
     */
    public static boolean containsChars(String str, char[] chars) {
        for (char c : chars) {
            if (containsChar(str, c)) {
                return true;
            }
        }

        return false;
    }

    public static Collection<String> tokenize(String theValue, String theTokenizerChar) {
        Collection<String> aResult = new ArrayList<String>();
        if (EmptyHandler.isNotEmpty(theValue)) {
            StringTokenizer aTokenizer = new StringTokenizer(theValue, theTokenizerChar);
            while (aTokenizer.hasMoreTokens()) {
                String aToken = aTokenizer.nextToken().trim();
                if (EmptyHandler.isNotEmpty(aToken)) {
                    aResult.add(aToken);
                }
            }
        }
        return aResult;
    }

    public static String getStacktraceAsString(Throwable theThrowable) {
        StringWriter aWriter = new StringWriter();
        theThrowable.printStackTrace(new PrintWriter(aWriter));
        return aWriter.toString();

    }

    /**
     * Strips any package specifiers from the given class name. For instance, "java.util.List" will become "List".
     *
     * @param className class name to strip
     * @return the class name without the package prefix
     */
    public static String stripPackageFromClassName(String className) {
        int pos = className.lastIndexOf('.');
        if (pos > 0) {
            return className.substring(pos + 1);
        }
        return className;
    }

    /**
     * Find the package specifier for a given class name. For instance, "java.util.List" will become "java.util".
     *
     * @param className class name to strip
     * @return the package specifier for the given <B>className</B>
     */
    public static String stripClassFromClassName(String className) {
        int pos = className.lastIndexOf('.');
        if (pos > 0) {
            return className.substring(0, pos);
        }
        return "";
    }

    /**
     * Strips supplied prefix(es) away and lower's the first case if the second case is lower.
     *
     * @param theValue  The value to be searched
     * @param thePrefix Vararg with prefixes to look for
     * @return The new value
     */
    public static String stripPrefix(String theValue, String... thePrefix) {
        for (String aPrefix : thePrefix) {
            if (theValue.startsWith(aPrefix)) {
                int aCount = aPrefix.length();
                if (theValue.length() == aCount) {
                    // Special case! Return empty string only
                    return "";
                } else {
                    String aValue = theValue.substring(aCount, theValue.length());
                    if (aValue.length() == 1) {
                        // Lower the remaining case
                        return aValue.toLowerCase();
                    } else {
                        // Check if the second case is low or high
                        if (aValue.substring(1, 2).toUpperCase().equals(aValue.substring(1, 2))) {
                            // Leave the casing as is accordning to JavaBeans spec.
                            return aValue;
                        } else {
                            // Lower the first case
                            return aValue.substring(0, 1).toLowerCase() + aValue.substring(1);
                        }
                    }
                }
            }
        }
        // No prefix(es) found!
        return theValue;
    }

    /**
     * Adds supplied prefix and upper's the first case of the value.
     *
     * @param theValue  The value to be searched
     * @param thePrefix The prefix to add
     * @return The new value
     */
    public static String addPrefix(String theValue, String thePrefix) {
        return thePrefix + theValue.substring(0, 1).toUpperCase() + theValue.substring(1);
    }

    public static <T> String concat(Collection<T> theData) {
        return concat(theData, ",");
    }

    public static <T> String concat(Collection<T> theData, String theSeparator) {
        StringBuilder aBuilder = new StringBuilder();
        if (EmptyHandler.isNotEmpty(theData)) {
            String aSeparator = "";
            for (T aObject : theData) {
                aBuilder.append(aSeparator).append(aObject != null ? aObject.toString() : "");
                aSeparator = theSeparator;
            }
        }
        return aBuilder.toString();
    }

    public static <T> String concat(T[] theArray) {
        return concat(theArray, ",");
    }

    public static <T> String concat(T[] theArray, String theSeparator) {
        StringBuilder aBuilder = new StringBuilder();
        if (EmptyHandler.isNotEmpty(theArray)) {
            String aSeparator = "";
            for (T aObject : theArray) {
                aBuilder.append(aSeparator).append(aObject != null ? aObject.toString() : "");
                aSeparator = theSeparator;
            }
        }
        return aBuilder.toString();
    }

    /**
     * @param theInitWord  The word to search for
     * @param theContent   The content top search
     * @param theDelimiter The word delimiter
     * @return The word after the word that is being supplied. If a '(' is discovered, every word until ')' is returned
     */
    public static String getNextWord(String theInitWord, String theContent, String theDelimiter) {
        String aNextWord = null;
        StringTokenizer aTokenizer = new StringTokenizer(theContent, theDelimiter);
        while (aTokenizer.hasMoreElements()) {
            String aElement = (String) aTokenizer.nextElement();
            if (theInitWord.equals(aElement.trim())) {
                // Found the init word
                if (aTokenizer.hasMoreElements()) {
                    aNextWord = (String) aTokenizer.nextElement();
                    if ("(".equals(aNextWord)) {
                        StringBuilder aBuilder = new StringBuilder("(");
                        // Skip until end parenthesis
                        while (aTokenizer.hasMoreElements()) {
                            String aObj = (String) aTokenizer.nextElement();
                            aBuilder.append(" ").append(aObj);
                            if (")".equals(aObj)) {
                                break;
                            }
                        }
                        aNextWord = aBuilder.toString();
                    }
                    break;
                }
            }
        }
        return aNextWord;
    }

    public static int countWord(String theWord, String theContent, String theDelimiter) {
        int aCounter = 0;
        StringTokenizer aTokenizer = new StringTokenizer(theContent, theDelimiter);
        while (aTokenizer.hasMoreElements()) {
            String aElement = (String) aTokenizer.nextElement();
            if (theWord.equals(aElement.trim())) {
                aCounter++;
            }
        }
        return aCounter;
    }

}
