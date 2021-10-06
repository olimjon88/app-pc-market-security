package uz.pdp.utils;

public class Utils {

    public static boolean isEmpty(String str) {
        return str == null || str.isEmpty();
    }

    public static boolean isEmpty(Object obj) {
        return obj == null;
    }

}