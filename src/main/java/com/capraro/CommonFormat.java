package com.capraro;

public class CommonFormat {

    public static String boldMarkers(String s1) {
        String s = s1;
        if (s != null && s.length() > 0) {
            s = s.replace("##", "<span style=\"font-weight: bold; vertical-align: text-top; color:red\">");
            s = s.replace("~~", "</span>");
            s = s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase();
        }
        return s;
    }

    public static String boldMarkersOnly(String s1) {
        String s = s1;
        if (s != null && s.length() > 0) {
            s = s.replace("##", "<span style=\"font-weight: bold; vertical-align: text-top; color:red\">");
            s = s.replace("~~", "</span>");
        }
        return s;
    }

}

