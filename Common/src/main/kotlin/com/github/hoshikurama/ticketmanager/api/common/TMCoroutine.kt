package com.github.hoshikurama.ticketmanager.api.common


import kotlinx.coroutines.*
import java.util.concurrent.ForkJoinPool

/**
 * Handles all Coroutine stuff for TicketManager. This class is immediately available
 */
object TMCoroutine {
    // Variables
    private val commonPoolDispatcher = ForkJoinPool.commonPool().asCoroutineDispatcher()
    internal val permanentScope = CoroutineScope(commonPoolDispatcher)
    @Volatile private var supervisedScope = generateSupervisedScope()

    private val supervisedScopeCounterActor = IntActor()

    // Actor Stuff

    // Functions
    @Suppress("Unused")
    fun launchGlobal(f: suspend CoroutineScope.() -> Unit): Job {
        return permanentScope.launch {
            try { f() }
            catch (e: Exception) { e.printStackTrace() }
        }
    }

    @Suppress("Unused")
    fun <T> asyncGlobal(f: suspend CoroutineScope.() -> T): Deferred<T> {
        return permanentScope.async { f() }
    }

    @Suppress("Unused")
    fun launchSupervised(f: suspend CoroutineScope.() -> Unit): Job {
        return supervisedScope.launch {
            supervisedScopeCounterActor.increment()
            try { f() }
            catch (e: Exception) { throw e }
            finally { supervisedScopeCounterActor.decrement() }
        }
    }

    @Suppress("Unused")
    suspend fun getSupervisedJobCount(): Int {
        return supervisedScopeCounterActor.get()
    }

    @Suppress("Unused")
    suspend fun cancelTasks(reason: String) {
        supervisedScope.cancel(reason)
        supervisedScope = generateSupervisedScope()
        supervisedScopeCounterActor.reset()
    }
    private fun generateSupervisedScope() = CoroutineScope(SupervisorJob() + commonPoolDispatcher)
}