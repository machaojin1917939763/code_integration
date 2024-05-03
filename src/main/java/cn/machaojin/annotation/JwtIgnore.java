package cn.machaojin.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author Ma Chaojin
 * @since 2024-04-30 16:01
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface JwtIgnore {
    boolean value() default true;
}
