package cn.machaojin.tool;

import lombok.Data;

import static cn.machaojin.constants.StateConstant.SUCCESS;
import static cn.machaojin.constants.StateConstant.SUCCESS_MESSAGE;

/**
 * @author Ma Chaojin
 */
@Data
public class ApiResult {

    private String code;
    private String msg;
    private Object data;

    public ApiResult(String code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }
    public ApiResult(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }
    public ApiResult(String code) {
        this.code = code;
    }
    public ApiResult() {}

    public static ApiResult success(Object data) {
        return new ApiResult(SUCCESS, SUCCESS_MESSAGE, data);
    }

    public static ApiResult success() {
        return success(null);
    }

    public static ApiResult error(String msg) {
        return new ApiResult(SUCCESS, msg);
    }

    public static ApiResult error(String code, String msg) {
        return new ApiResult(code, msg);
    }

    public static ApiResult error() {
        return error(null);
    }
}
