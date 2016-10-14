package br.com.danyswork.trakttv.request;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class GsonUtils {

    public static Gson createGson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        return gsonBuilder.create();
    }

    public static JsonElement toJson(Object jsonObject) {
        if (jsonObject == null)
            return null;

        return createGson().toJsonTree(jsonObject);
    }

    public static <T> List<T> fromJsonList(String json, Class<T> t) {
        JsonElement jsonElement = fromJson(json, JsonElement.class);
        return fromJsonList(jsonElement, t);
    }

    public static <T> List<T> fromJsonList(JsonElement jsonElement, Class<T> t) {
        List<T> ret = new ArrayList<>();
        Gson gson = createGson();

        JsonArray jsonArray = jsonElement.getAsJsonArray();

        for (JsonElement item : jsonArray) {
            T obj = gson.fromJson(item, t);
            ret.add(obj);
        }

        return ret;
    }

    public static <T> T fromJson(String json, Class<T> ownerClazz) {
        Gson gson = createGson();
        return gson.fromJson(json, ownerClazz);
    }

    public static <T> T fromJson(JsonElement jsonElement, Class<T> ownerClazz) {
        Gson gson = createGson();
        return gson.fromJson(jsonElement, ownerClazz);
    }

    @SuppressWarnings("unchecked")
    private static Class<? extends Enum<?>> asClass(Type type) {
        return (Class<? extends Enum<?>>) type;
    }


    public static JsonElement toJson(String json) {
        if (TextUtils.isEmpty(json)) {
            return null;
        }

        return new JsonParser().parse(json);
    }
}
