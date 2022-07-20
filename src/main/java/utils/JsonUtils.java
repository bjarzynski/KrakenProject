package utils;

import com.google.gson.Gson;

public class JsonUtils {
  public static String getJsonString(Object object) {
    Gson gson = new Gson();
    return gson.toJson(object);
  }
}
