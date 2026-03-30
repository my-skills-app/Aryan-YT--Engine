package com.grack.nanojson;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

public class JsonBuilder<T> {
    private final T root;
    private final Stack<Object> stack = new Stack<>();

    public JsonBuilder(T root) {
        this.root = root;
        this.stack.push(root);
    }

    public JsonBuilder<T> object(String key) {
        JsonObject obj = new JsonObject();
        put(key, obj);
        stack.push(obj);
        return this;
    }

    public JsonBuilder<T> object() {
        JsonObject obj = new JsonObject();
        add(obj);
        stack.push(obj);
        return this;
    }

    public JsonBuilder<T> array(String key) {
        JsonArray arr = new JsonArray();
        put(key, arr);
        stack.push(arr);
        return this;
    }

    public JsonBuilder<T> array() {
        JsonArray arr = new JsonArray();
        add(arr);
        stack.push(arr);
        return this;
    }

    public JsonBuilder<T> value(String key, Object value) {
        put(key, value);
        return this;
    }

    public JsonBuilder<T> value(Object value) {
        add(value);
        return this;
    }

    public JsonBuilder<T> value(String key, boolean value) { return value(key, (Object)value); }
    public JsonBuilder<T> value(String key, int value) { return value(key, (Object)value); }
    public JsonBuilder<T> value(String key, long value) { return value(key, (Object)value); }
    public JsonBuilder<T> value(String key, double value) { return value(key, (Object)value); }

    public JsonBuilder<T> end() {
        if (stack.size() > 1) {
            stack.pop();
        }
        return this;
    }

    public T done() {
        return root;
    }

    private void put(String key, Object value) {
        Object current = stack.peek();
        if (current instanceof Map) {
            ((Map<String, Object>) current).put(key, value);
        }
    }

    private void add(Object value) {
        Object current = stack.peek();
        if (current instanceof List) {
            ((List<Object>) current).add(value);
        }
    }
}
