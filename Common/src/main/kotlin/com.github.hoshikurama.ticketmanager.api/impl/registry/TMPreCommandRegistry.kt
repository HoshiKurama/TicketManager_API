package com.github.hoshikurama.ticketmanager.api.impl.registry

import com.github.hoshikurama.ticketmanager.api.utilities.Result
import com.github.hoshikurama.ticketmanager.api.registry.RegistrationResult
import com.github.hoshikurama.ticketmanager.api.registry.RegistrationResult.*
import com.github.hoshikurama.ticketmanager.api.registry.RegistrationResult.Rejected.Reason
import com.github.hoshikurama.ticketmanager.api.registry.precommand.PreCommandExtension
import com.github.hoshikurama.ticketmanager.api.registry.precommand.PreCommandRegistry
import kotlin.reflect.KClass
import kotlin.reflect.full.createInstance

class TMPreCommandRegistry : PreCommandRegistry {
    private val decidersExtensions = mutableListOf<PreCommandExtension.SyncDecider>()
    private val syncAfterExtensions = mutableListOf<PreCommandExtension.SyncAfter>()
    private val asyncAfterExtensions = mutableListOf<PreCommandExtension.AsyncAfter>()

    override fun register(kClass: KClass<out PreCommandExtension>): RegistrationResult {
        if (!kClass.hasZeroArgConstructor())
            return Rejected(Reason.INVALID_CONSTRUCTOR)

        return when (val instance = runCatch { kClass.createInstance() }) {
            is Result.Failure -> Rejected(Reason.EXCEPTION_OCCURRED(instance.error))
            is Result.Success<PreCommandExtension> -> when (val type = instance.value) {
                is PreCommandExtension.SyncDecider -> decidersExtensions.add(type)
                is PreCommandExtension.SyncAfter -> syncAfterExtensions.add(type)
                is PreCommandExtension.AsyncAfter -> asyncAfterExtensions.add(type)
            }.run { Accepted }
        }
    }

    override fun register(extension: PreCommandExtension): RegistrationResult {
        when (extension) {
            is PreCommandExtension.SyncDecider -> decidersExtensions.add(extension)
            is PreCommandExtension.SyncAfter -> syncAfterExtensions.add(extension)
            is PreCommandExtension.AsyncAfter -> asyncAfterExtensions.add(extension)
        }
        return Accepted
    }

    fun getDeciders(): List<PreCommandExtension.SyncDecider> = decidersExtensions
    fun getSyncAfters(): List<PreCommandExtension.SyncAfter> = syncAfterExtensions
    fun getAsyncAfters(): List<PreCommandExtension.AsyncAfter> = asyncAfterExtensions
}
