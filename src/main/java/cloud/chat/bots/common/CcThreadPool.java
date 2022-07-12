package cloud.chat.bots.common;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

/**
 * @author gx
 */
@Slf4j
public class CcThreadPool extends ThreadPoolExecutor{

    private static CcThreadPool poolExecutor = null;

    private static int corePoolSize = 10;

    private static int maximumPoolSize = Runtime.getRuntime().availableProcessors()*10;

    private static long keepAliveTime = 60L;

    private static TimeUnit unit = TimeUnit.SECONDS;

    private static BlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<>(1000);

    private static ThreadFactory threadFactory = threadFactory();

    private static RejectedExecutionHandler handler = rejectedExecutionHandler();

    private CcThreadPool() {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue,threadFactory,handler);
    }

    public synchronized static CcThreadPool instance(){
        if (poolExecutor == null){
            if(maximumPoolSize < corePoolSize){
                corePoolSize = maximumPoolSize/2;
            }
            poolExecutor = new CcThreadPool();
        }
        return poolExecutor;
    }

    private static ThreadFactory threadFactory(){
        ThreadFactory threadFactory = r -> {
            Thread thread=new Thread(r);
            thread.setName("bot");
            if(thread.isDaemon()) {
                thread.setDaemon(false);
            }
            if(Thread.NORM_PRIORITY!=thread.getPriority()) {
                thread.setPriority(Thread.NORM_PRIORITY);
            }
            return thread;
        };
        return threadFactory;
    }

    private static RejectedExecutionHandler rejectedExecutionHandler(){
        RejectedExecutionHandler rejectedExecutionHandler = (r, executor) -> {
            log.error(executor.toString());
        };
        return rejectedExecutionHandler;
    }

}
