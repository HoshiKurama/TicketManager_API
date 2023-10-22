package com.github.hoshikurama.ticketmanager.api.impl.registry

import com.github.hoshikurama.ticketmanager.api.utilities.Result
import com.github.hoshikurama.ticketmanager.api.registry.RegistrationResult
import com.github.hoshikurama.ticketmanager.api.registry.RegistrationResult.*
import com.github.hoshikurama.ticketmanager.api.registry.RegistrationResult.Rejected.Reason
import com.github.hoshikurama.ticketmanager.api.registry.config.Config
import com.github.hoshikurama.ticketmanager.api.registry.database.DatabaseExtension
import com.github.hoshikurama.ticketmanager.api.registry.database.AsyncDatabase
import com.github.hoshikurama.ticketmanager.api.registry.locale.Locale
import java.nio.file.Path
import kotlin.reflect.KClass
import kotlin.reflect.full.createInstance
import com.github.hoshikurama.ticketmanager.api.registry.database.DatabaseRegistry

class TMDatabaseRegistry : DatabaseRegistry {
    private lateinit var extension: DatabaseExtension

    override fun register(kClass: KClass<out DatabaseExtension>): RegistrationResult {
        if (::extension.isInitialized)
            return Rejected(Reason.HAS_REGISTRATION)

        if (!kClass.hasZeroArgConstructor())
            return Rejected(Reason.INVALID_CONSTRUCTOR)

        return when (val instance = runCatch { kClass.createInstance() }) {
            is Result.Failure -> Rejected(Reason.EXCEPTION_OCCURRED(instance.error))
            is Result.Success<DatabaseExtension> -> {
                extension = instance.value
                Accepted
            }
        }
    }

    override fun register(extension: DatabaseExtension): RegistrationResult {
        if (::extension.isInitialized)
            return Rejected(Reason.HAS_REGISTRATION)

        this.extension = extension
        return Accepted
    }

    suspend fun loadAndInitialize(tmDirectory: Path, config: Config, locale: Locale): AsyncDatabase {
        return extension.load(tmDirectory, config, locale)
            .also(AsyncDatabase::initializeDatabase)
    }

    fun extensionInitialized() = ::extension.isInitialized
}