package com.github.hoshikurama.ticketmanager.api.registry.permission

import com.github.hoshikurama.ticketmanager.api.registry.RegistrationResult
import kotlin.reflect.KClass

interface PermissionRegistry {
    fun register(kClass: KClass<out PermissionExtension>): RegistrationResult
    fun register(extension: PermissionExtension): RegistrationResult
}