package com.github.hoshikurama.ticketmanager.api.impl.registry

import com.github.hoshikurama.ticketmanager.api.utilities.Result
import com.github.hoshikurama.ticketmanager.api.registry.RegistrationResult
import com.github.hoshikurama.ticketmanager.api.registry.RegistrationResult.*
import com.github.hoshikurama.ticketmanager.api.registry.RegistrationResult.Rejected.Reason
import com.github.hoshikurama.ticketmanager.api.registry.playerjoin.PlayerJoinExtension
import com.github.hoshikurama.ticketmanager.api.registry.playerjoin.PlayerJoinRegistry
import com.github.hoshikurama.ticketmanager.api.registry.playerjoin.PlayerJoinRegistry.RunType
import kotlin.reflect.KClass
import kotlin.reflect.full.createInstance

@Suppress("Unused")
class TMPlayerJoinRegistry : PlayerJoinRegistry {
    private val syncExtensions = mutableListOf<PlayerJoinExtension>()
    private val asyncExtensions = mutableListOf<PlayerJoinExtension>()


    override fun register(kClass: KClass<out PlayerJoinExtension>, runType: RunType): RegistrationResult {
        if (!kClass.hasZeroArgConstructor())
            return Rejected(Reason.INVALID_CONSTRUCTOR)

        return when (val instance = runCatch { kClass.createInstance() }) {
            is Result.Failure -> Rejected(Reason.EXCEPTION_OCCURRED(instance.error))
            is Result.Success<PlayerJoinExtension> -> when (runType) {
                RunType.ASYNC -> asyncExtensions.add(instance.value)
                RunType.SYNC -> syncExtensions.add(instance.value)
            }.run { Accepted }
        }
    }

    override fun register(extension: PlayerJoinExtension, runType: RunType): RegistrationResult {
        when (runType) {
            RunType.ASYNC -> asyncExtensions.add(extension)
            RunType.SYNC -> syncExtensions.add(extension)
        }
        return Accepted
    }

    fun getSyncExtensions(): List<PlayerJoinExtension> = syncExtensions
    fun getAsyncExtensions(): List<PlayerJoinExtension> = asyncExtensions
}