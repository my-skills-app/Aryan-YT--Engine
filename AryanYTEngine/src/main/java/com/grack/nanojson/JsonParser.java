package com.grack.nanojson;

import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONTokener;
import java.io.InputStream;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.stream.Collectors;
import java.nio.charset.StandardCharsets;

public final class JsonParser {
    private JsonParser() {
    }

    public static ParserContext<JsonObject> object() {
        return new ParserContext<>(JsonObject.class);
    }

    public static ParserContext<JsonArray> array() {
        return new ParserContext<>(JsonArray.class);
    }

    public static final class ParserContext<T> {
        private final Class<T> type;

        ParserContext(Class<T> type) {
            this.type = type;
        }

        public T from(String json) throws JsonParserException {
            try {
                if (type == JsonObject.class) {
                    return (T) convert(new JSONObject(json));
                } else if (type == JsonArray.class) {
                    return (T) convert(new JSONArray(json));
                }
                return null;
            } catch (JSONException e) {
                throw new JsonParserException("Failed to parse JSON", e);
            }
        }

        public T from(InputStream is) throws JsonParserException {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
                String json = reader.lines().collect(Collectors.joining("\n"));
                return from(json);
            } catch (Exception e) {
                throw new JsonParserException("Failed to read InputStream", e);
            }
        }

        public T from(Reader r) throws JsonParserException {
            try (BufferedReader reader = new BufferedReader(r)) {
                String json = reader.lines().collect(Collectors.joining("\n"));
                return from(json);
            } catch (Exception e) {
                throw new JsonParserException("Failed to read Reader", e);
            }
        }
        
        private static Object convert(Object val) {
            if (val instanceof JSONObject) {
                JsonObject obj = new JsonObject();
                JSONObject source = (JSONObject) val;
                Iterator<String> keys = source.keys();
                while (keys.hasNext()) {
                    String key = keys.next();
                    obj.put(key, convert(source.opt(key)));
                }
                return obj;
            } else if (val instanceof JSONArray) {
                JsonArray arr = new JsonArray();
                JSONArray source = (JSONArray) val;
                for (int i = 0; i < source.length(); i++) {
                    arr.add(convert(source.opt(i)));
                }
                return arr;
            }
            if (JSONObject.NULL.equals(val)) {
                return null;
            }
            return val;
        }
    }
}
