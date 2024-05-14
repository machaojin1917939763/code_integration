package cn.machaojin.advice;

/**
 * @author Ma Chaojin
 * @since 2024-05-08 17:00
 */
import cn.machaojin.tool.ApiResult;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public ApiResult handleException(Exception e) {
        return ApiResult.error("后端服务异常");
    }

    @ResponseBody
    @ExceptionHandler(value = NoHandlerFoundException.class)
    public String handlerNotFound(NoHandlerFoundException e) {
        return "没有找到请求的处理器: " + e.getMessage();
    }
}