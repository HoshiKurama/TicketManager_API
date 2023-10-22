package com.github.hoshikurama.ticketmanager.api.registry.locale

import com.github.hoshikurama.ticketmanager.api.registry.RegistrationResult
import kotlin.reflect.KClass

interface LocaleRegistry {
    fun register(kClass: KClass<out LocaleExtension>): RegistrationResult
    fun register(extension: LocaleExtension): RegistrationResult
}