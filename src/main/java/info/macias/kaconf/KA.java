/*
    Copyright 2016-2020 Mario Macías

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
*/
package info.macias.kaconf;

/**
 * <p>Helper class to allow KAConf working wit Java final static fields. The methods
 * in this class can be also used to provide more semantic information to other types of
 * fields.</p>
 * <p>The motivation of this class is to deal with cases such as the next:</p>
 * <pre>
 *     // Defaults to 1000
 *     \@Property("service.timeout")
 *     public static final int SERVICE_TIMEOUT = 1000;
 * </pre>
 * <p>Java compiler will always inline the 1000 value in any access to the {@code SERVICE_TIMEOUT}
 * field, so even if there is a property overriding this value, the actual value will always be 1000.
 * To deal with this, it is enough to define the final static field by assigning the output
 * of a trivial function, for example:</p>
 * <pre>
 *     import static info.macias.kaconf.KA.*;
 *
 *     // ....
 *
 *     \@Property("service.timeout")
 *     public static final int SERVICE_TIMEOUT = def(1000);
 * </pre>
 *
 * <p>If no default value wants to be provided, it is also needed to specify the type of the field:</p>
 * <pre>
 *     \@Property("service.timeout")
 *     public static final int SERVICE_TIMEOUT = anInt()
 * </pre>
 */
public class KA {

    /**
     * Specifies a default value
     * @param defaultByte the default value
     * @return the default value
     */
    public static byte def(byte defaultByte) {
        return defaultByte;
    }
    /**
     * Specifies a default value
     * @param defaultChar the default value
     * @return the default value
     */
    public static char def(char defaultChar) {
        return defaultChar;
    }
    /**
     * Specifies a default value
     * @param defaultInt the default value
     * @return the default value
     */
    public static int def(int defaultInt) {
        return defaultInt;
    }
    /**
     * Specifies a default value
     * @param defaultLong the default value
     * @return the default value
     */
    public static long def(long defaultLong) {
        return defaultLong;
    }
    /**
     * Specifies a default value
     * @param defaultFloat the default value
     * @return the default value
     */
    public static float def(float defaultFloat) {
        return defaultFloat;
    }
    /**
     * Specifies a default value
     * @param defaultDouble the default value
     * @return the default value
     */
    public static double def(double defaultDouble) {
        return defaultDouble;
    }
    /**
     * Specifies a default value
     * @param defaultBoolean the default value
     * @return the default value
     */
    public static boolean def(boolean defaultBoolean) {
        return defaultBoolean;
    }
    /**
     * Specifies a default value. This method is not needed
     * even for final static fields, but is kept for lexical consistency
     *
     * @param string the default value
     * @return the default value
     */
    public static String def(String string) {
        return string;
    }

    /**
     * Avoids final static values to be inlined with a 0 byte value
     * @return a 0 byte
     */
    public static byte aByte() {
        return 0;
    }
    /**
     * Avoids final static values to be inlined with a 0 integer value
     * @return a 0 integer value
     */
    public static int anInt() {
        return 0;
    }
    /**
     * Avoids final static values to be inlined with a 0 long value
     * @return a 0 long value
     */
    public static int aLong() {
        return 0;
    }
    /**
     * Avoids final static values to be inlined with a 0 float value
     * @return a 0 float value
     */
    public static int aFloat() {
        return 0;
    }
    /**
     * Avoids final static values to be inlined with a 0 double value
     * @return a 0 double value
     */
    public static double aDouble() {
        return 0;
    }
    /**
     * Avoids final static values to be inlined with a false boolean value
     * @return false
     */
    public static boolean aBoolean() {
        return false;
    }
    /**
     * Avoids final static values to be inlined with a '\0' char value
     * @return '\0'
     */
    public static char aChar() {
        return '\0';
    }
    /**
     * Avoids final static values to be inlined with a null String value
     * @return null String value
     */
    public static String aString() {
        return null;
    }
}
