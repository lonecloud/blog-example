package cn.lonecloud.example.gof.guarded.suspension;

/**
 * @author lonecloud
 * @version v1.0
 * @Package cn.lonecloud.example.gof.guarded.suspension
 * @Description: TODO
 * @date 2020/9/265:00 下午
 */
@FunctionalInterface
public interface Predicate {
    /**
     * 判断是否符合条件
     * @return
     */
    boolean evaluate();
}
