package tw.edu.ntou.summerhomework.androidrssreader.common;

public class StringHelper {
    public static boolean isNullOrWhiteSpace(String value) {
        return value == null || value.isEmpty() || value.trim().isEmpty();
    }
}
