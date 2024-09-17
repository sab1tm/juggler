package kz.sab1tm.juggler.utils;

public class StringUtil {

    private StringUtil() {
    }

    public static String leftPad(String str, int size) {
        return String.format("%-" + size + "s", str);
    }

    public static String rightPad(String str, int size) {
        return String.format("%-" + size + "s", str);
    }
}
