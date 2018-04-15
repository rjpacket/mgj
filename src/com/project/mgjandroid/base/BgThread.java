package com.project.mgjandroid.base;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import android.os.Process;

/**
 * 后台线程
 *
 * @author yava
 */
public abstract class BgThread extends Thread {

    private static ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(2);

    public BgThread() {
        int cpuNums = Runtime.getRuntime().availableProcessors();
        if (cpuNums >= 4) {
            cpuNums = 4;//控制最大线程池数
        }
        executor.setCorePoolSize(cpuNums);
        executor.setMaximumPoolSize(cpuNums);
    }

    @Override
    public synchronized void start() {
        executor.execute(this);
    }

    @Override
    public final void run() {
        Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND);
        doTask();
    }

    public abstract void doTask();

}
