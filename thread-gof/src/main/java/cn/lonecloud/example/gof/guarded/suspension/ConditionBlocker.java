package cn.lonecloud.example.gof.guarded.suspension;

import java.util.concurrent.Callable;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author lonecloud
 * @version v1.0
 * @Package cn.lonecloud.example.gof.guarded.suspension
 * @Description: TODO
 * @date 2020/9/265:07 下午
 */
public class ConditionBlocker implements Blocker {

    private final Lock lock;

    private final Condition condition;

    private final boolean allowAccess2Lock;

    public ConditionBlocker(Lock lock) {
        this(lock, true);
    }

    public ConditionBlocker() {
        this(false);
    }

    public ConditionBlocker(boolean allowAccess2Lock) {
        this(new ReentrantLock(), allowAccess2Lock);
    }

    public ConditionBlocker(Lock lock, boolean allowAccess2Lock) {
        this.lock = lock;
        this.allowAccess2Lock = allowAccess2Lock;
        this.condition = lock.newCondition();
    }

    @Override
    public <T> T callWithGuard(GuardedAction<T> guardedAction) throws Exception {
        T result;
        lock.lockInterruptibly();
        try {
            final Predicate predicate = guardedAction.predicate;
            while (!predicate.evaluate()) {
                condition.await();
            }
            result = guardedAction.call();
        } finally {
            lock.unlock();
        }
        return result;
    }

    @Override
    public void signalAfter(Callable<Boolean> stateOperation) throws Exception {
        lock.lockInterruptibly();
        try {
            if (stateOperation.call()){
                condition.signal();
            }
        }finally {
            lock.unlock();
        }
    }

    @Override
    public void signal() throws InterruptedException {
        lock.lockInterruptibly();
        try {
            condition.signal();
        }finally {
            lock.unlock();
        }
    }

    @Override
    public void broadcastAfter(Callable<Boolean> stateOperation) throws Exception {
        lock.lockInterruptibly();
        try {
            if (stateOperation.call()){
                condition.signalAll();
            }
        }finally {
            lock.unlock();
        }
    }
}
