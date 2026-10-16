package com.polybrain.util;

import javax.transaction.Status;
import javax.transaction.Synchronization;

/** 掛在 Hibernate transaction 上：commit 成功才執行 task，rollback 就不執行（見 HibernateUtil.runAfterCommit） */
public class AfterCommitTask implements Synchronization {
    private final Runnable task;

    public AfterCommitTask(Runnable task) {
        this.task = task;
    }

    @Override
    public void beforeCompletion() {
    }

    @Override
    public void afterCompletion(int status) {
        if (status == Status.STATUS_COMMITTED) {
            task.run();
        }
    }
}
