package cn.machaojin.tool;

import lombok.Data;

import static cn.machaojin.constants.StateConstant.*;

/**
 * @author Ma Chaojin
 */
@Data
public class ApiResult {

    private Integer code;
    private String message;
    private String type;
    private Object result;

    public ApiResult(Integer code, String message, Object result, String type) {
        this.code = code;
        this.message = message;
        this.result = result;
        this.type = type;
    }
    public ApiResult(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
    public ApiResult(Integer code) {
        this.code = code;
    }
    public ApiResult() {}

    public static ApiResult success(Object result) {
        return new ApiResult(SUCCESS, SUCCESS_MESSAGE, result,TYPE_SUCCESS);
    }

    public static ApiResult success() {
        return success(null);
    }

    public static ApiResult error(String message) {
        return new ApiResult(ERROR, message);
    }

    public static ApiResult error(Integer code, String message) {
        return new ApiResult(code, message);
    }

    public static ApiResult error() {
        return error(null);
    }
}
