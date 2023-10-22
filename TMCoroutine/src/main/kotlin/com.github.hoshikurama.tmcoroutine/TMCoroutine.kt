package com.github.hoshikurama.tmcoroutine

import kotlinx.coroutines.*

@Suppress("Unused")
/**
 * Handles all Coroutine stuff for TicketManager.
 */
object TMCoroutine {
    private val permanentScope = CoroutineScope(Dispatchers.Default)

    object Global {

        fun launch(f: suspend CoroutineScope.() -> Unit) = permanentScope.launch {
            try { f() }
            catch (e: Exception) {
                e.printStackTrace()
            }
        }

        fun <T> async(f: suspend CoroutineScope.() -> T) = permanentScope.async { f() }
    }


    object Supervised {
        @Volatile private var scope = generateSupervisedScope()
        private val counter = ChanneledCounter(0UL)

        fun launch(f: suspend CoroutineScope.() -> Unit): Job = scope.launch {
            counter.increment()
            try { f() }
            catch (e: Exception) { throw e }
            finally { counter.decrement() }
        }

        fun <T> async(f: suspend CoroutineScope.() -> T): Deferred<T> = scope.async {
            counter.increment()
            try { f() }
            catch (e: Exception) { throw e }
            finally { counter.decrement() }
        }

        private fun generateSupervisedScope() =
            CoroutineScope(SupervisorJob() + Dispatchers.Default)

        suspend fun getSupervisedJobCount() = counter.get()

        suspend fun cancelTasks(reason: String) {
            scope.cancel(reason)
            scope = generateSupervisedScope()
            counter.set(0UL)
        }
    }
}