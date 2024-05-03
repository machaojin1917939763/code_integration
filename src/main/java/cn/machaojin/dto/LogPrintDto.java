package cn.machaojin.dto;

import lombok.Data;


/**
 * @author: Ma, Chaojin(C | TT - 33)
 * @description:
 * @date: 2024/1/17 16:33
 * @version: 1.0
 */
@Data
public class LogPrintDto {

    /**
     * 请求入参
     */
    private Object[] inputParams;

    /**
     * 返回参数
     */
    private Object outputParams;

    /**
     * 修改日志的路径
     */
    private String methodPath;

    /**
     * 方法执行时间
     */
    private Long executeTime;

    /**
     * 请求类型
     * post
     * get
     */
    private String methodType;

    /**
     * 请求路径
     */
    private String requestURI;

    /**
     * 报错代码位置
     */
    private String errorPosition;

    /**
     * 错误类型
     */
    private String errorType;

    /**
     * 错误原因
     */
    private String errorReason;

    /**
     * 方法执行结果
     */
    private String result;

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("{");
        if (result != null)
            builder.append("\n\tresult: '").append(result).append("',");
        if (executeTime != null)
            builder.append("\n\texecuteTime: ").append(executeTime).append("ms").append(",");
        if (methodType != null)
            builder.append("\n\tmethodType: '").append(methodType).append("',");
        if (requestURI != null)
            builder.append("\n\trequestURI: '").append(requestURI).append("',");
        if (methodPath != null)
            builder.append("\n\tmethodPath: '").append(methodPath).append("',");
        if (inputParams != null && inputParams.length > 0) {
            builder.append("\n\tinputParams: [");
            for (Object param : inputParams) {
                builder.append("\n\t\t").append(param).append(",");
            }
            builder.append("\n\t],");
        }
        if (outputParams != null)
            builder.append("\n\toutputParams: ").append(outputParams).append(",");
        if (errorReason != null)
            builder.append("\n\terrorReason: '").append(errorReason).append("',");
        if (errorType != null)
            builder.append("\n\terrorType: '").append(errorType).append("',");
        if (errorPosition != null)
            builder.append("\n\terrorPosition: '").append(errorPosition).append("',");


        builder.append("\n}");
        return builder.toString();
    }

}
