package com.github.hoshikurama.ticketmanager.api.registry.config

import com.github.hoshikurama.ticketmanager.api.registry.RegistrationResult
import kotlin.reflect.KClass

interface ConfigRegistry {
    fun register(kClass: KClass<out ConfigExtension>): RegistrationResult
    fun register(extension: ConfigExtension): RegistrationResult
}