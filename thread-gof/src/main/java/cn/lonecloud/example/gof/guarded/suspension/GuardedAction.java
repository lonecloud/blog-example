package cn.lonecloud.example.gof.guarded.suspension;

import java.util.concurrent.Callable;

/**
 * @author lonecloud
 * @version v1.0
 * @Package cn.lonecloud.example.gof.guarded.suspension
 * @Description: TODO
 * @date 2020/9/265:01 下午
 */
public abstract class GuardedAction<T> implements Callable<T> {

    protected final Predicate predicate;

    public GuardedAction(Predicate predicate) {
        this.predicate = predicate;
    }
}
