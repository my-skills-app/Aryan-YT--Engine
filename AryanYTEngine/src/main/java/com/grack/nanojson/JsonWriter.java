package com.grack.nanojson;

import org.json.JSONObject;

public final class JsonWriter {
    public static StringWriter string() {
        return new StringWriter();
    }

    public static String string(Object obj) {
        if (obj == null) return "null";
        try {
            return convert(obj).toString();
        } catch (Exception e) {
            return obj.toString();
        }
    }

    private static Object convert(Object val) {
        try {
            if (val instanceof JsonObject) {
                org.json.JSONObject target = new org.json.JSONObject();
                JsonObject source = (JsonObject) val;
                for (java.util.Map.Entry<String, Object> entry : source.entrySet()) {
                    target.put(entry.getKey(), convert(entry.getValue()));
                }
                return target;
            } else if (val instanceof JsonArray) {
                org.json.JSONArray target = new org.json.JSONArray();
                JsonArray source = (JsonArray) val;
                for (Object item : source) {
                    target.put(convert(item));
                }
                return target;
            }
        } catch (org.json.JSONException e) {
            // Should not happen with our keys
        }
        return val;
    }

    public static final class StringWriter {
        private final JsonObject json = new JsonObject();

        public StringWriter object() { return this; }
        public StringWriter array(String key) { return this; }
        public StringWriter value(String key, Object value) { 
            json.put(key, value); 
            return this; 
        }
        public StringWriter object(String key) { return this; }
        public StringWriter end() { return this; }
        public StringWriter value(Object value) { return this; }
        public String done() { 
            return JsonWriter.string(json); 
        }
    }
}
