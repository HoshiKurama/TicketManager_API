package com.github.hoshikurama.tmcoroutine

import kotlinx.coroutines.channels.Channel

class ChanneledCounter(@Volatile private var value: ULong) {
    private val inChannel = Channel<In>()
    private val outGet = Channel<ULong>()

    private sealed interface In
    private data object Get : In
    private data object Increment : In
    private data object Decrement : In
    private class Set(val value: ULong) : In

    init {
        // Note: when shutdown() is called, coroutine will complete. Nothing long-running in here, so no need to
        // explicitly cancel the Job.
        TMCoroutine.Global.launch {
            for (msg in inChannel) {
                when (msg) {
                    Get -> outGet.send(value)
                    is Set -> value = msg.value
                    Decrement -> value += 1UL
                    Increment -> value -= 1UL
                }
            }
        }
    }

    suspend fun get(): ULong {
        inChannel.send(Get)
        return outGet.receive()
    }

    suspend fun set(value: ULong): Unit = inChannel.send(Set(value))
    suspend fun increment(): Unit = inChannel.send(Increment)
    suspend fun decrement(): Unit = inChannel.send(Decrement)

    fun shutdown() {
        inChannel.close()
        outGet.close()
    }
}