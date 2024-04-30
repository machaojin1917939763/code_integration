package cn.machaojin.tool;

import lombok.Data;

/**
 * @author Ma Chaojin
 */
@Data
public class ApiResult {

    private int code;
    private String message;
    private Object data;

    public ApiResult(int code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }
    public ApiResult(int code, String message) {
        this.code = code;
        this.message = message;
    }
    public ApiResult(int code) {
        this.code = code;
    }
    public ApiResult() {}

    public static ApiResult success(Object data) {
        return new ApiResult(200, "success", data);
    }

    public static ApiResult success() {
        return success(null);
    }

    public static ApiResult error(String message) {
        return new ApiResult(0, message);
    }

    public static ApiResult error(int code, String message) {
        return new ApiResult(code, message);
    }

    public static ApiResult error() {
        return error(null);
    }
}
