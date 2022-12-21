package com.example.eldar.entity;

import io.vertx.core.json.JsonObject;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class RequestContext implements Serializable {

    public HashMap<String, Object> requestHeaders = new HashMap<>();

    public HashMap<String, Object> requestParams = new HashMap<>();

    public HashMap<String, Object> requestBody = new HashMap<>();

    public HashMap<String, Object> apiParams = new HashMap<>();

    public Object getHeader(String name) {
        return requestHeaders.get(name);
    }

    public void addRequestParam(String name, Object value) {
        requestParams.put(name, value);
    }

    public void addRequestBody(JsonObject jsonObject) {
        var map = jsonObject.mapTo(Map.class);
        requestBody.putAll(map);
    }

    public void addRequestHeaders(List<Map.Entry<String, String>> entries) {
        addAllEntry(entries, requestHeaders);
    }

    public void addRequestParams(List<Map.Entry<String, String>> entries) {
        addAllEntry(entries, requestParams);
    }

    private void addAllEntry(List<Map.Entry<String, String>> entries, Map<String, Object> collection) {
        var map = entries.stream().collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        collection.putAll(map);
    }
}
