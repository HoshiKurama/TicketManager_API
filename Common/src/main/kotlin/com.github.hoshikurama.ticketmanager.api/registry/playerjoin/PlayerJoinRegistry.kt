package com.github.hoshikurama.ticketmanager.api.registry.playerjoin

import com.github.hoshikurama.ticketmanager.api.registry.RegistrationResult
import kotlin.reflect.KClass

/**
 * Represents a registration point for PlayerJoinExtension extensions. This should be synchronously called in your plugin's
 * enable function.
 *
 * Note: This registry is not limited in the number of registrations. Additionally, any exceptions found are caught during
 * runtime and ignored (with a stacktrace readout).
 */
interface PlayerJoinRegistry {
    /**
     * Allows registration via the KClass. This method is the simplest but requires that your primary constructor does
     * not take any arguments. Otherwise, the registry will reject your request.
     * @return whether the request was accepted or rejected (with a reason)
     */
    fun register(kClass: KClass<out PlayerJoinExtension>, runType: RunType): RegistrationResult
    /**
     * Allows registration with an existing instance. Not as clean or as simple as the KClass method, but it does offer
     * more control (like if your extension depends on other data).
     * @return whether the request was accepted or rejected (with a reason)
     */
    fun register(extension: PlayerJoinExtension, runType: RunType): RegistrationResult

    /**
     * Determines how the extension's function is called:
     * - ASYNC: Entries are launched in order of registration to run concurrently.
     * - SYNC: Entries are launched in order of registration, but each entry must complete before the next is started.
     *
     * IMPORTANT NOTE: Despite the names, BOTH run types run on the same off-thread coroutine dispatcher pool. You are
     * free to change the coroutine context or launch the appropriate BukkitScheduler task to run on the main thread.
     */
    enum class RunType {
        ASYNC, SYNC
    }
}