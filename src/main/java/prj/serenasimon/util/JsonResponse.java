package prj.serenasimon.util;

import org.json.JSONObject;

public class JsonResponse {

    public static String generalJsonResponse(String msg) {
        return new JSONObject().put("Message", msg).toString();
    }
}
