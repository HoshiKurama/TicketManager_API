package com.github.hoshikurama.ticketmanager.api.impl.registry

import com.github.hoshikurama.ticketmanager.api.registry.RegistrationResult
import com.github.hoshikurama.ticketmanager.api.registry.RegistrationResult.*
import com.github.hoshikurama.ticketmanager.api.registry.RegistrationResult.Rejected.Reason
import com.github.hoshikurama.ticketmanager.api.registry.repeatingtasks.RepeatingTaskExtension
import com.github.hoshikurama.ticketmanager.api.registry.repeatingtasks.RepeatingTaskRegistry
import com.github.hoshikurama.ticketmanager.api.utilities.Result
import kotlin.reflect.KClass
import kotlin.reflect.full.createInstance

class TMRepeatingTaskRegistry : RepeatingTaskRegistry {
    private val extensions = mutableListOf<RepeatingTaskExtension>()

    override fun register(kClass: KClass<out RepeatingTaskExtension>): RegistrationResult {
        if (!kClass.hasZeroArgConstructor())
            return Rejected(Reason.INVALID_CONSTRUCTOR)

        return when (val instance = runCatch { kClass.createInstance() }) {
            is Result.Failure -> Rejected(Reason.EXCEPTION_OCCURRED(instance.error))
            is Result.Success<RepeatingTaskExtension> -> {
                extensions.add(instance.value)
                Accepted
            }
        }
    }

    override fun register(extension: RepeatingTaskExtension): RegistrationResult {
        extensions.add(extension)
        return Accepted
    }

    @Suppress("Unused")
    fun getExtensions(): List<RepeatingTaskExtension> = extensions
}