package com.zjx.statistics.engine;

/**
 * 计算上下文
 *
 * @author Aaron
 * @date 2020/6/30 11:33
 */
public class ComputeContext {
    /**
     * 线程私有变量
     */
    private static final ThreadLocal<ComputeContextData> tokenStorage = new ThreadLocal<>();
    /**
     * 饿汉式单例，程序启动即加载
     */
    private static ComputeContext instance = new ComputeContext();

    /**
     * 构造方法私有化
     */
    private ComputeContext() {
        // 防止反射破坏单例
        if (instance != null) {
            throw new RuntimeException("不允许非法访问!");
        }
    }

    /**
     * 返回全局实例对象
     *
     * @return
     */
    public static ComputeContext getInstance() {
        return instance;
    }

    public ComputeContextData getComputeContextData() {
        return tokenStorage.get();
    }

    public void setComputeContextData(ComputeContextData computeContextData) {
        tokenStorage.set(computeContextData);
    }

    public void remove() {
        tokenStorage.remove();
    }
}
