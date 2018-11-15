package model.vo;

/**
 * @author Yuicon
 */
public class JsonResponse<T> {

    private String message;

    private T data;

    private boolean success;

    public static JsonResponse success(String message) {
        return build(message, null, true);
    }

    public static <T> JsonResponse success(T data) {
        return build("", data, true);
    }

    public static <T> JsonResponse success(String message, T data) {
        return build(message, data, true);
    }

    public static JsonResponse error(String message) {
        return build(message, null, false);
    }

    public static <T> JsonResponse error(String message, T data) {
        return build(message, data, false);
    }

    private static <T> JsonResponse build(String message, T data, boolean success) {
        JsonResponse jsonResponse = new JsonResponse();
        jsonResponse.message = message;
        jsonResponse.data = data;
        jsonResponse.success = success;
        return jsonResponse;
    }

}
