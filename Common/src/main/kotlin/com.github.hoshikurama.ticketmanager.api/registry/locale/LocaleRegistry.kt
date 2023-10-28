package com.github.hoshikurama.ticketmanager.api.registry.locale

import com.github.hoshikurama.ticketmanager.api.registry.RegistrationResult
import kotlin.reflect.KClass

/**
 * Represents a registration point for Locale extensions. This should be synchronously called in your plugin's
 * enable function.
 *
 * Note: TicketManager will accept only the first LocaleExtension to be successfully registered. All subsequent
 * LocaleExtension registration requests will be rejected with this reason.
 */
interface LocaleRegistry {
    /**
     * Allows registration via the KClass. This method is the simplest but requires that your primary constructor does
     * not take any arguments. Otherwise, the registry will reject your request.
     * @return whether the request was accepted or rejected (with a reason)
     */
    fun register(kClass: KClass<out LocaleExtension>): RegistrationResult

    /**
     * Allows registration with an existing instance. Not as clean or as simple as the KClass method, but it does offer
     * more control (like if your extension depends on other data).
     * @return whether the request was accepted or rejected (with a reason)
     */
    fun register(extension: LocaleExtension): RegistrationResult
}