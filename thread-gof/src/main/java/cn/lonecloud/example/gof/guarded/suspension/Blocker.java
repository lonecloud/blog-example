package cn.lonecloud.example.gof.guarded.suspension;

import java.util.concurrent.Callable;

/**
 * @author lonecloud
 * @version v1.0
 * @Package cn.lonecloud.example.gof.guarded.suspension
 * @Description: TODO
 * @date 2020/9/265:02 下午
 */
public interface Blocker {

    /**
     * 在保护条件成立时候立刻执行目标动作，否则阻塞当前线程，知道保护条件成立
     *
     * @param guardedAction 带保护的目标动作
     * @param
     * @return
     * @throws Exception
     */
    <T> T callWithGuard(GuardedAction<T> guardedAction) throws Exception;

    /**
     * 在执行stateOperation 所指定的操作后，决定是否唤醒本Block所暂挂的所有线程中的一个
     *
     * @param stateOperation
     * @throws Exception
     */
    void signalAfter(Callable<Boolean> stateOperation) throws Exception;

    void signal() throws InterruptedException;

    /**
     * 在执行stateOperation 所指定的操作后，决定是否唤醒本block所暂挂的所有线程
     * @param stateOperation 更改动作的操作
     * @throws Exception
     */
    void broadcastAfter(Callable<Boolean> stateOperation) throws Exception;
}
