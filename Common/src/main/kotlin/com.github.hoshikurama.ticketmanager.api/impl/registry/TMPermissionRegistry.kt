package com.github.hoshikurama.ticketmanager.api.impl.registry
import com.github.hoshikurama.ticketmanager.api.utilities.Result
import com.github.hoshikurama.ticketmanager.api.registry.RegistrationResult
import com.github.hoshikurama.ticketmanager.api.registry.RegistrationResult.*
import com.github.hoshikurama.ticketmanager.api.registry.RegistrationResult.Rejected.Reason
import com.github.hoshikurama.ticketmanager.api.registry.permission.PermissionExtension
import com.github.hoshikurama.ticketmanager.api.registry.permission.PermissionRegistry
import kotlin.reflect.KClass
import kotlin.reflect.full.createInstance

class TMPermissionRegistry : PermissionRegistry {
    private lateinit var extension: PermissionExtension

    override fun register(kClass: KClass<out PermissionExtension>): RegistrationResult {
        if (::extension.isInitialized)
            return Rejected(Reason.HAS_REGISTRATION)

        if (!kClass.hasZeroArgConstructor())
            return Rejected(Reason.INVALID_CONSTRUCTOR)

        return when (val instance = runCatch { kClass.createInstance() }) {
            is Result.Failure -> Rejected(Reason.EXCEPTION_OCCURRED(instance.error))
            is Result.Success<PermissionExtension> -> {
                extension = instance.value
                Accepted
            }
        }
    }

    override fun register(extension: PermissionExtension): RegistrationResult {
        if (::extension.isInitialized)
            return Rejected(Reason.HAS_REGISTRATION)

        this.extension = extension
        return Accepted
    }

    suspend fun load() = extension.load()

    fun extensionInitialized() = ::extension.isInitialized
}