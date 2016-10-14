package br.com.danyswork.trakttv.request;

import android.net.Uri;

import java.util.HashMap;
import java.util.Map;

public class URLBuilder {

    Uri.Builder builder;

    private Map<String, String> parameters = new HashMap<>();

    public URLBuilder(String baseURL) {
        this.builder = Uri.parse(baseURL).buildUpon();
    }

    public URLBuilder addQueryParameter(String key, String value) {
        parameters.put(key, value);
        return this;
    }

    public URLBuilder addQueryParameter(String key, Long value) {
        parameters.put(key, String.valueOf(value));
        return this;
    }

    public URLBuilder addQueryParameter(String key, Integer value) {
        parameters.put(key, String.valueOf(value));
        return this;
    }

    public URLBuilder addQueryParameter(String key, Boolean value) {
        parameters.put(key, String.valueOf(value));
        return this;
    }

    public URLBuilder addPath(String newSegment) {
        this.builder.appendPath(newSegment);
        return this;
    }

    public URLBuilder addPath(Long newSegment) {
        this.builder.appendPath(String.valueOf(newSegment));
        return this;
    }

    public String build() {

        for (String key : parameters.keySet()) {
            String value = parameters.get(key);
            builder.appendQueryParameter(key, value);
        }

        return builder.build().toString();
    }
}