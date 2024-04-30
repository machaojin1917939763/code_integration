package cn.machaojin.aspect;

import cn.machaojin.annotation.CodeLog;
import cn.machaojin.dto.LogPrintDto;
import com.baomidou.mybatisplus.core.toolkit.SystemClock;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import java.lang.reflect.Method;
import java.util.Optional;
import java.util.function.Function;

/**
 * @author Ma Chaojin
 * @since 2024-04-30 16:21
 */
@Aspect
@Component
public class LogPrintAspect {

    /**
     * 如果要打印原始异常信息，在Apollo中配置 log.print.exception: true 就行
     */
    @Value("${log.print.exception:false}")
    private boolean printException;

    /**
     * 通过Apollo配置控制日志打印,默认开启
     */
    @Value("${stalker.log:true}")
    private boolean printLog;

    @Pointcut("@within(cn.machaojin.annotation.CodeLog) || @annotation(cn.machaojin.annotation.CodeLog)")
    public void pointCut() {
    }

    /**
     * 打印类或方法上的 {@link CodeLog} @within 和 @annotation 注解分别对注解在类上和在方法上增强。
     */
    @Around("pointCut()")
    public Object printLog(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = SystemClock.now();
        Object result = null;
        boolean flag = true;
        try {
            result = joinPoint.proceed();
            return result;
        } catch (Exception exception) {
            flag = false;
            throw exception;
        } finally {
            if (printLog) {
                boolean finalFlag = flag;
                getHttpRequest(Boolean.TRUE, joinPoint, result,null,flag, (logPrintDto) -> {
                    logPrintDto.setResult(finalFlag ? "Success" : "Error");
                    logPrintDto.setExecuteTime(SystemClock.now() - startTime);
                    return logPrintDto;
                });
            }
        }
    }


    /**
     * 当方法发生异常时，就会触发这个方法，打印异常信息
     *
     * @param joinPoint
     * @param exception
     */
    @AfterThrowing(value = "pointCut()", throwing = "exception")
    public void printError(JoinPoint joinPoint, Exception exception) {
        if (printLog) {
            getHttpRequest(Boolean.FALSE, joinPoint, null,exception,false, (logPrintDto) -> {
                StackTraceElement[] stackTraceElements = exception.getStackTrace();
                StackTraceElement firstElement = stackTraceElements[0];
                String exceptionLocation = String.format("%s.%s(%s:%d)",
                        firstElement.getClassName(),
                        firstElement.getMethodName(),
                        firstElement.getFileName(),
                        firstElement.getLineNumber());
                String exceptionType = exception.getClass().getName();
                String exceptionMessage = exception.getMessage();
                logPrintDto.setErrorType(exceptionType);
                logPrintDto.setResult("Error");
                logPrintDto.setErrorPosition(exceptionLocation);
                logPrintDto.setInputParams(joinPoint.getArgs());
                logPrintDto.setErrorReason(exceptionMessage);
                return logPrintDto;
            });
        }
    }


    /**
     * 日志打印core
     * @param type 如果方法没有切点没有发生异常就传true，反之false
     * @param joinPoint 切面
     * @param result 方法执行结果
     * @param exception 切点发生的异常
     * @param flag 切点是否发生异常
     * @param function 函数式接口
     */
    private void getHttpRequest(Boolean type, JoinPoint joinPoint, Object result,Exception exception,boolean flag, Function<LogPrintDto, LogPrintDto> function) {
        LogPrintDto logPrint = new LogPrintDto();
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Logger log = LoggerFactory.getLogger(methodSignature.getDeclaringType());
        try {
            if (type) {
                Method targetMethod = joinPoint
                        .getTarget()
                        .getClass()
                        .getDeclaredMethod(methodSignature.getName(), methodSignature.getMethod().getParameterTypes());
                CodeLog logAnnotation = Optional
                        .ofNullable(targetMethod.getAnnotation(CodeLog.class))
                        .orElse(joinPoint.getTarget().getClass().getAnnotation(CodeLog.class));
                logPrint.setMethodPath(joinPoint.getTarget().getClass().getSimpleName() + "." + targetMethod.getName());
                if (logAnnotation.input()) {
                    logPrint.setInputParams(buildInput((ProceedingJoinPoint) joinPoint));
                }
                if (logAnnotation.output()) {
                    logPrint.setOutputParams(result);
                }
            }
            ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            assert servletRequestAttributes != null;
            logPrint.setMethodType(servletRequestAttributes.getRequest().getMethod());
            logPrint.setRequestURI(servletRequestAttributes.getRequest().getRequestURI());
        } catch (Exception ignored) {
        } finally {
            logPrint = function.apply(logPrint);
            if (type) {
                if (flag){
                    log.info("{}", logPrint);
                }
            } else {
                if (!flag){
                    log.error("{}", logPrint);
                }
            }
            if (printException && exception != null) {
                exception.printStackTrace();
            }
        }

    }

    /**
     * 处理方法入参
     *
     * @param joinPoint
     * @return
     */
    private Object[] buildInput(ProceedingJoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        Object[] printArgs = new Object[args.length];
        for (int i = 0; i < args.length; i++) {
            if ((args[i] instanceof HttpServletRequest) || args[i] instanceof HttpServletResponse) {
                continue;
            }
            if (args[i] instanceof byte[]) {
                printArgs[i] = "byte array";
            } else if (args[i] instanceof MultipartFile) {
                printArgs[i] = "file";
            } else {
                printArgs[i] = args[i];
            }
        }
        return printArgs;
    }
}

