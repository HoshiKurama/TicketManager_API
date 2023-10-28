package com.github.hoshikurama.ticketmanager.api.registry.repeatingtasks

import com.github.hoshikurama.ticketmanager.api.registry.RegistrationResult
import kotlin.reflect.KClass

/**
 * Represents a registration point for RepeatingTaskExtension extensions. This should be synchronously called in your plugin's
 * enable function.
 *
 * Note: This registry is not limited in the number of registrations. Additionally, any exceptions found are caught during
 * runtime and ignored (with a stacktrace readout).
 */
interface RepeatingTaskRegistry {
    /**
     * Allows registration via the KClass. This method is the simplest but requires that your primary constructor does
     * not take any arguments. Otherwise, the registry will reject your request.
     * @return whether the request was accepted or rejected (with a reason)
     */
    fun register(kClass: KClass<out RepeatingTaskExtension>): RegistrationResult
    /**
     * Allows registration with an existing instance. Not as clean or as simple as the KClass method, but it does offer
     * more control (like if your extension depends on other data).
     * @return whether the request was accepted or rejected (with a reason)
     */
    fun register(extension: RepeatingTaskExtension): RegistrationResult
}