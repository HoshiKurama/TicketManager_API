package com.github.hoshikurama.ticketmanager.api.impl.registry

import com.github.hoshikurama.ticketmanager.api.utilities.Result
import com.github.hoshikurama.ticketmanager.api.registry.RegistrationResult
import com.github.hoshikurama.ticketmanager.api.registry.RegistrationResult.*
import com.github.hoshikurama.ticketmanager.api.registry.RegistrationResult.Rejected.Reason
import com.github.hoshikurama.ticketmanager.api.registry.config.Config
import com.github.hoshikurama.ticketmanager.api.registry.locale.Locale
import com.github.hoshikurama.ticketmanager.api.registry.locale.LocaleExtension
import com.github.hoshikurama.ticketmanager.api.registry.locale.LocaleRegistry
import java.nio.file.Path
import kotlin.reflect.KClass
import kotlin.reflect.full.createInstance

class TMLocaleRegistry : LocaleRegistry {
    private lateinit var extension: LocaleExtension


    override fun register(kClass: KClass<out LocaleExtension>): RegistrationResult {
        if (::extension.isInitialized)
            return Rejected(Reason.HAS_REGISTRATION)

        if (!kClass.hasZeroArgConstructor())
            return Rejected(Reason.INVALID_CONSTRUCTOR)

        return when (val instance = runCatch { kClass.createInstance() }) {
            is Result.Failure -> Rejected(Reason.EXCEPTION_OCCURRED(instance.error))
            is Result.Success<LocaleExtension> -> {
                extension = instance.value
                Accepted
            }
        }
    }

    override fun register(extension: LocaleExtension): RegistrationResult {
        if (::extension.isInitialized)
            return Rejected(Reason.HAS_REGISTRATION)

        this.extension = extension
        return Accepted
    }

    suspend fun load(tmDirectory: Path, config: Config): Locale {
        return extension.load(tmDirectory, config)
    }

    fun extensionInitialized() = ::extension.isInitialized
}