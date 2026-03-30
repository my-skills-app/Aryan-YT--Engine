package com.grack.nanojson;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class JsonObject extends LinkedHashMap<String, Object> {
    public JsonObject() {
        super();
    }

    public JsonObject(Map<? extends String, ?> m) {
        super(m);
    }
    
    public static JsonObject of(Object obj) {
        if (obj instanceof JsonObject) return (JsonObject) obj;
        if (obj instanceof Map) return new JsonObject((Map<? extends String, ?>) obj);
        return null;
    }

    public static JsonBuilder<JsonObject> builder() {
        return new JsonBuilder<>(new JsonObject());
    }

    public JsonObject getObject(String key) {
        return getObject(key, new JsonObject());
    }

    public JsonObject getObject(String key, JsonObject defaultValue) {
        Object val = get(key);
        return val instanceof JsonObject ? (JsonObject) val : defaultValue;
    }

    public JsonArray getArray(String key) {
        Object val = get(key);
        return val instanceof JsonArray ? (JsonArray) val : new JsonArray();
    }

    public String getString(String key) {
        Object val = get(key);
        return val instanceof String ? (String) val : null;
    }

    public String getString(String key, String defaultValue) {
        Object val = get(key);
        return val instanceof String ? (String) val : defaultValue;
    }

    public Boolean getBoolean(String key) {
        return getBoolean(key, false);
    }

    public Boolean getBoolean(String key, boolean defaultValue) {
        Object val = get(key);
        return val instanceof Boolean ? (Boolean) val : defaultValue;
    }

    public Number getNumber(String key) {
        Object val = get(key);
        return val instanceof Number ? (Number) val : null;
    }

    public int getInt(String key) {
        Number n = getNumber(key);
        return n != null ? n.intValue() : 0;
    }

    public int getInt(String key, int defaultValue) {
        Number n = getNumber(key);
        return n != null ? n.intValue() : defaultValue;
    }

    public double getDouble(String key) {
        Number n = getNumber(key);
        return n != null ? n.doubleValue() : 0.0;
    }

    public long getLong(String key) {
        Number n = getNumber(key);
        return n != null ? n.longValue() : 0L;
    }

    public boolean has(String key) {
        return containsKey(key);
    }

    public boolean isNull(String key) {
        return get(key) == null;
    }

    public boolean isString(String key) {
        return get(key) instanceof String;
    }

    public boolean isObject(String key) {
        return get(key) instanceof JsonObject;
    }

    public boolean isArray(String key) {
        return get(key) instanceof JsonArray;
    }

    public boolean isNumber(String key) {
        return get(key) instanceof Number;
    }

    public boolean isBoolean(String key) {
        return get(key) instanceof Boolean;
    }

    // Fluent-style value methods for building
    public JsonObject value(String key, Object value) {
        put(key, value);
        return this;
    }
    public JsonObject value(String key, boolean value) { return value(key, (Object)value); }
    public JsonObject value(String key, int value) { return value(key, (Object)value); }
    public JsonObject value(String key, long value) { return value(key, (Object)value); }
    public JsonObject value(String key, double value) { return value(key, (Object)value); }
}
