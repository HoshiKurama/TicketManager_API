package com.github.hoshikurama.ticketmanager.api.impl.registry

import com.github.hoshikurama.ticketmanager.api.utilities.Result
import com.github.hoshikurama.ticketmanager.api.registry.RegistrationResult
import com.github.hoshikurama.ticketmanager.api.registry.RegistrationResult.*
import com.github.hoshikurama.ticketmanager.api.registry.RegistrationResult.Rejected.Reason
import com.github.hoshikurama.ticketmanager.api.registry.config.Config
import com.github.hoshikurama.ticketmanager.api.registry.config.ConfigExtension
import java.nio.file.Path
import kotlin.reflect.KClass
import kotlin.reflect.full.createInstance
import com.github.hoshikurama.ticketmanager.api.registry.config.ConfigRegistry

class TMConfigRegistry : ConfigRegistry {
    private lateinit var extension: ConfigExtension

    override fun register(kClass: KClass<out ConfigExtension>): RegistrationResult {
        if (::extension.isInitialized)
            return Rejected(Reason.HAS_REGISTRATION)

        if (!kClass.hasZeroArgConstructor())
            return Rejected(Reason.INVALID_CONSTRUCTOR)

        return when (val instance = runCatch { kClass.createInstance() }) {
            is Result.Failure -> Rejected(Reason.EXCEPTION_OCCURRED(instance.error))
            is Result.Success<ConfigExtension> -> {
                extension = instance.value
                Accepted
            }
        }
    }

    override fun register(extension: ConfigExtension): RegistrationResult {
        if (::extension.isInitialized)
            return Rejected(Reason.HAS_REGISTRATION)

        this.extension = extension
        return Accepted
    }

    suspend fun load(tmDirectory: Path): Config = extension.load(tmDirectory)

    fun extensionInitialized() = ::extension.isInitialized
}