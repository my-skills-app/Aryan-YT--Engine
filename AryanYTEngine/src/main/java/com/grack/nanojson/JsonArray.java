package com.grack.nanojson;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

public class JsonArray extends ArrayList<Object> {
    public JsonArray() {
        super();
    }

    public JsonArray(Collection<?> c) {
        super(c);
        // Deep convert any nested maps/lists if they're not already JsonObject/JsonArray
    }
    
    public static JsonArray of(Object obj) {
        if (obj instanceof JsonArray) return (JsonArray) obj;
        if (obj instanceof Collection) return new JsonArray((Collection<?>) obj);
        return null;
    }

    public static JsonBuilder<JsonArray> builder() {
        return new JsonBuilder<>(new JsonArray());
    }

    public JsonObject getObject(int index) {
        if (index < 0 || index >= size()) return new JsonObject();
        Object val = get(index);
        return val instanceof JsonObject ? (JsonObject) val : new JsonObject();
    }

    public JsonArray getArray(int index) {
        if (index < 0 || index >= size()) return new JsonArray();
        Object val = get(index);
        return val instanceof JsonArray ? (JsonArray) val : new JsonArray();
    }

    public String getString(int index) {
        Object val = get(index);
        return val instanceof String ? (String) val : null;
    }

    public Number getNumber(int index) {
        Object val = get(index);
        return val instanceof Number ? (Number) val : null;
    }

    public boolean getBoolean(int index, boolean defaultValue) {
        Object val = get(index);
        return val instanceof Boolean ? (Boolean) val : defaultValue;
    }

    public Stream<JsonObject> streamAsJsonObjects() {
        return stream()
                .filter(JsonObject.class::isInstance)
                .map(JsonObject.class::cast);
    }
}
