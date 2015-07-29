package com.bendani.bibliomania.generic.infrastructure;

import java.io.InputStream;

public class StringUtility {
    public static String convertStreamToString(InputStream is, String charset) {
        java.util.Scanner s = new java.util.Scanner(is, charset).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }
}
