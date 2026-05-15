package util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * JSON serialization helper for the Bill/Payment API.
 * Mirrors AppointmentResponseBuilder — uses Gson with a fixed date format.
 */
public class BillResponseBuilder {

    private static final Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .serializeNulls()
            .create();

    /** Java Object → JSON String (for HTTP responses) */
    public static String toJson(Object object) {
        return gson.toJson(object);
    }

    /** JSON String → Java Object (for parsing request bodies) */
    public static <T> T fromJson(String json, Class<T> classOfT) {
        return gson.fromJson(json, classOfT);
    }
}
