package com.finogeeks.finochat.vertx.core.exp

import io.reactivex.Scheduler

import io.vertx.core.Vertx
import io.vertx.core.WorkerExecutor


/**
 * @author [Julien Viet](mailto:julien@julienviet.com)
 */
object RxHelper {

    /**
     * Create a scheduler for a [io.vertx.core.Vertx] object, actions are executed on the event loop.
     *
     * @param vertx the vertx object
     * @return the scheduler
     */
    fun scheduler(vertx: io.vertx.core.Vertx): Scheduler {
        return ContextScheduler(vertx, false)
    }

    /**
     * Create a scheduler for a [io.vertx.core.Context], actions are executed on the event loop of this context.
     *
     * @param context the context object
     * @return the scheduler
     */
    fun scheduler(context: io.vertx.core.Context): Scheduler {
        return ContextScheduler(context, false)
    }

    /**
     * Create a scheduler for a [io.vertx.core.Vertx] object, actions can be blocking, they are not executed
     * on Vertx event loop.
     *
     * @param vertx the vertx object
     * @return the scheduler
     */
    fun blockingScheduler(vertx: io.vertx.core.Vertx): Scheduler {
        return ContextScheduler(vertx, true)
    }

    /**
     * Create a scheduler for a [io.vertx.core.Vertx] object, actions can be blocking, they are not executed
     * on Vertx event loop.
     *
     * @param vertx   the vertx object
     * @param ordered if true then if when tasks are scheduled several times on the same context, the executions
     * for that context will be executed serially, not in parallel. if false then they will be no ordering
     * guarantees
     * @return the scheduler
     */
    fun blockingScheduler(vertx: Vertx, ordered: Boolean): Scheduler {
        return ContextScheduler(vertx, true, ordered)
    }

    /**
     * Create a scheduler for a [io.vertx.core.WorkerExecutor] object, actions are executed on the threads of this executor.
     *
     * @param executor the worker executor object
     * @return the scheduler
     */
    fun blockingScheduler(executor: WorkerExecutor): Scheduler {
        return ContextScheduler(executor, false)
    }
}
