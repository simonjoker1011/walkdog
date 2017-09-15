package prj.serenasimon.util;

import java.util.HashMap;
import java.util.Map.Entry;

import org.json.JSONObject;

public class JsonResponse {

    public static String generalJsonResponse(String msg) {
        return new JSONObject().put("Message", msg).toString();

    }

    public static <T> String constructJsonResponse(HashMap<String, T> params) {
        JSONObject resp = new JSONObject();
        for (Entry<String, T> entry : params.entrySet()) {
            resp.put(entry.getKey(), entry.getValue());
        }
        return resp.toString();
    }
}
