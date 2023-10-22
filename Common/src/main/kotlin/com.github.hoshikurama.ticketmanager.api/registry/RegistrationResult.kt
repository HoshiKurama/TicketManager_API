package com.github.hoshikurama.ticketmanager.api.registry

@Suppress("ClassName")
sealed interface RegistrationResult {
    data object Accepted : RegistrationResult
    data class Rejected(val reason: Reason) : RegistrationResult {

        interface Reason {
            data object HAS_REGISTRATION : Reason
            data object INVALID_CONSTRUCTOR : Reason
            data class EXCEPTION_OCCURRED(val e: Exception) : Reason
        }
    }
}