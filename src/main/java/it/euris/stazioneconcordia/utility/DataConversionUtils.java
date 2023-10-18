package it.euris.stazioneconcordia.utility;


import it.euris.stazioneconcordia.data.enums.Priority;

import java.time.LocalDateTime;

public class DataConversionUtils {

    private DataConversionUtils() {

    }

    public static String numberToString(Number value) {
        return value == null ? null : value.toString();
    }

    public static Long stringToLong(String value) {
        return value == null ? null : Long.parseLong(value);
    }


    public static LocalDateTime stringToLocalDateTime(String value) {
        return value == null ? null : LocalDateTime.parse(value);
    }

    public static String localDateTimeToString(LocalDateTime value) {
        return value == null ? null : value.toString();
    }

    public static Boolean stringToBoolean(String value) {
        return value == null ? null : Boolean.valueOf(value);
    }

    public static String booleanToString(Boolean value) {
        return value == null ? null : value.toString();
    }

    public static Priority stringToPriority(String value) {
        for (Priority priorityValue : Priority.values()) {
            if (priorityValue.name().equalsIgnoreCase(value))
                return priorityValue;
        }
        return null;
    }

    public static String priorityToString(Priority value) {
        return value == null ? null : value.name();
    }



}
