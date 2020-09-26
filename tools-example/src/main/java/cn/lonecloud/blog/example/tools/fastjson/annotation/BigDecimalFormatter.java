package cn.lonecloud.blog.example.tools.fastjson.annotation;

import java.lang.annotation.*;
import java.math.RoundingMode;

/**
 * @author lonecloud
 * @version v1.0
 * @Package cn.lonecloud.blog.example.tools.fastjson.annotation
 * @Description: TODO
 * @date 2020/8/101:17 下午
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface BigDecimalFormatter {
    /**
     * 保留几位小数
     * @return
     */
    int scale() default 6;

    /**
     * 四舍五入模式
     * @see RoundingMode
     * @return
     */
    RoundingMode roundingMode() default RoundingMode.HALF_UP;

}
