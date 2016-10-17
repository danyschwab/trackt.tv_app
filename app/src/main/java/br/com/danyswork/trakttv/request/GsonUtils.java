package br.com.danyswork.trakttv.request;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import java.util.ArrayList;
import java.util.List;

public class GsonUtils {

    private static Gson createGson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        return gsonBuilder.create();
    }

    public static <T> List<T> fromJsonList(String json, Class<T> t) {
        JsonElement jsonElement = fromJson(json, JsonElement.class);
        return fromJsonList(jsonElement, t);
    }

    private static <T> List<T> fromJsonList(JsonElement jsonElement, Class<T> t) {
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

}
