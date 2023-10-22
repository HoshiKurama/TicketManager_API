package com.github.hoshikurama.ticketmanager.api.registry.repeatingtasks

import com.github.hoshikurama.ticketmanager.api.registry.RegistrationResult
import kotlin.reflect.KClass

interface RepeatingTaskRegistry {
    fun register(kClass: KClass<out RepeatingTaskExtension>): RegistrationResult
    fun register(extension: RepeatingTaskExtension): RegistrationResult
}