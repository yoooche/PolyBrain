package com.polybrain.util;

import org.junit.Test;

import javax.transaction.Status;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.Assert.assertEquals;

public class AfterCommitTaskTest {

    @Test
    public void committed_runsTaskOnce() {
        AtomicInteger runs = new AtomicInteger();
        new AfterCommitTask(runs::incrementAndGet).afterCompletion(Status.STATUS_COMMITTED);
        assertEquals(1, runs.get());
    }

    @Test
    public void rolledBack_doesNotRunTask() {
        AtomicInteger runs = new AtomicInteger();
        new AfterCommitTask(runs::incrementAndGet).afterCompletion(Status.STATUS_ROLLEDBACK);
        assertEquals(0, runs.get());
    }

    @Test
    public void unknownStatus_doesNotRunTask() {
        AtomicInteger runs = new AtomicInteger();
        new AfterCommitTask(runs::incrementAndGet).afterCompletion(Status.STATUS_UNKNOWN);
        assertEquals(0, runs.get());
    }

    @Test
    public void beforeCompletion_doesNotRunTask() {
        AtomicInteger runs = new AtomicInteger();
        new AfterCommitTask(runs::incrementAndGet).beforeCompletion();
        assertEquals(0, runs.get());
    }
}
