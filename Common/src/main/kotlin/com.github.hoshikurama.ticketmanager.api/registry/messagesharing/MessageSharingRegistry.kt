package com.github.hoshikurama.ticketmanager.api.registry.messagesharing

import com.github.hoshikurama.ticketmanager.api.registry.RegistrationResult
import kotlin.reflect.KClass

/**
 * Represents a registration point for MessageSharing extensions.
 *
 * Note: TicketManager will accept only the first MessageSharingExtension to be successfully registered.
 * All subsequent MessageSharingExtension registration requests will be rejected with this reason.
 */
interface MessageSharingRegistry {
    /**
     * Allows registration via the KClass. This method is the simplest but requires that your primary constructor does
     * not take any arguments. Otherwise, the registry will reject your request.
     * @return whether the request was accepted or rejected (with a reason)
     */
    fun register(kClass: KClass<out MessageSharingExtension>): RegistrationResult

    /**
     * Allows registration with an existing instance. Not as clean or as simple as the KClass method, but it does offer
     * more control (like if your extension depends on other data).
     * @return whether the request was accepted or rejected (with a reason)
     */
    fun register(extension: MessageSharingExtension): RegistrationResult
}