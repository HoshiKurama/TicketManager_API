package com.github.hoshikurama.ticketmanager.api.registry.playerjoin

import com.github.hoshikurama.ticketmanager.api.registry.RegistrationResult
import kotlin.reflect.KClass

interface PlayerJoinRegistry {
    fun register(kClass: KClass<out PlayerJoinExtension>, runType: RunType): RegistrationResult
    fun register(extension: PlayerJoinExtension, runType: RunType): RegistrationResult

    enum class RunType {
        ASYNC, SYNC
    }
}