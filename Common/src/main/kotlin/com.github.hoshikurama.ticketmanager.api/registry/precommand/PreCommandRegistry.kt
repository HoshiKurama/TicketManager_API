package com.github.hoshikurama.ticketmanager.api.registry.precommand

import com.github.hoshikurama.ticketmanager.api.registry.RegistrationResult
import kotlin.reflect.KClass

interface PreCommandRegistry {
    fun register(kClass: KClass<out PreCommandExtension>): RegistrationResult
    fun register(extension: PreCommandExtension): RegistrationResult
}