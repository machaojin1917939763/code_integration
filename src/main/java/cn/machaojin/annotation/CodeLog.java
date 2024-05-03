package cn.machaojin.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Ma Chaojin
 * @since 2024-04-30 16:24
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface CodeLog {

    /**
     * 入参打印
     *
     * @return 打印结果中是否包含入参，{@link Boolean#TRUE} 打印，{@link Boolean#FALSE} 不打印
     */
    boolean input() default true;

    /**
     * 出参打印
     *
     * @return 打印结果中是否包含出参，{@link Boolean#TRUE} 打印，{@link Boolean#FALSE} 不打印
     */
    boolean output() default true;
}
