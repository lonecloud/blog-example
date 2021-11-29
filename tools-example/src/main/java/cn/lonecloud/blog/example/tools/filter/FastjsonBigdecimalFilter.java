package cn.lonecloud.blog.example.tools.filter;

import cn.lonecloud.blog.example.tools.fastjson.annotation.BigDecimalFormatter;
import com.alibaba.fastjson.serializer.ValueFilter;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * @author lonecloud
 * @version v1.0
 * @Package cn.lonecloud.blog.example.tools.filter
 * @Description: TODO
 * @date 2020/8/101:15 下午
 */
public class FastjsonBigdecimalFilter  implements ValueFilter {
    @Override
    public Object process(Object o, String name, Object value) {
        //判断值是否正常
        if (Objects.nonNull(o)&&Objects.nonNull(value)&& value instanceof BigDecimal){
            //针对性处理BigDecimal
            Class<?> aClass = o.getClass();
            try {
                Field field = aClass.getDeclaredField(name);
                field.setAccessible(true);
                BigDecimalFormatter annotation = field.getAnnotation(BigDecimalFormatter.class);
                if (annotation!=null){
                    BigDecimal v2=(BigDecimal) value;
                    return v2.setScale(annotation.scale(),annotation.roundingMode());
                }
            } catch (NoSuchFieldException e) {
                return value;
            }

        }
        return null;
    }
}
