package com.github.hoshikurama.ticketmanager.api.registry.database

import com.github.hoshikurama.ticketmanager.api.registry.RegistrationResult
import kotlin.reflect.KClass

interface DatabaseRegistry {
    fun register(kClass: KClass<out DatabaseExtension>): RegistrationResult
    fun register(extension: DatabaseExtension): RegistrationResult
}