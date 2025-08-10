package com.github.hoshikurama.ticketmanager.api.impl.registry

import com.github.hoshikurama.ticketmanager.api.registry.RegistrationResult
import com.github.hoshikurama.ticketmanager.api.registry.RegistrationResult.Accepted
import com.github.hoshikurama.ticketmanager.api.registry.RegistrationResult.Rejected
import com.github.hoshikurama.ticketmanager.api.registry.RegistrationResult.Rejected.Reason
import com.github.hoshikurama.ticketmanager.api.registry.messagesharing.MessageSharing
import com.github.hoshikurama.ticketmanager.api.registry.messagesharing.MessageSharingExtension
import com.github.hoshikurama.ticketmanager.api.registry.messagesharing.MessageSharingRegistry
import com.github.hoshikurama.ticketmanager.api.utilities.Result
import kotlinx.coroutines.channels.SendChannel
import kotlin.reflect.KClass
import kotlin.reflect.full.createInstance

class TMMessageSharingRegistry : MessageSharingRegistry {
    private lateinit var extension: MessageSharingExtension

    override fun register(kClass: KClass<out MessageSharingExtension>): RegistrationResult {
        if (::extension.isInitialized)
            return Rejected(Reason.HAS_REGISTRATION)

        if (!kClass.hasZeroArgConstructor())
            return Rejected(Reason.INVALID_CONSTRUCTOR)

        return when (val instance = runCatch { kClass.createInstance() }) {
            is Result.Failure -> Rejected(Reason.EXCEPTION_OCCURRED(instance.error))
            is Result.Success<MessageSharingExtension> -> {
                extension = instance.value
                Accepted
            }
        }
    }

    override fun register(extension: MessageSharingExtension): RegistrationResult {
        if (::extension.isInitialized)
            return Rejected(Reason.HAS_REGISTRATION)

        this.extension = extension
        return Accepted
    }

    @Suppress("Unused")
    suspend fun loadAndInitialize(
        teleportJoinIntermediary: SendChannel<ByteArray>,
        notificationSharingIntermediary: SendChannel<ByteArray>,
        pbeVersionIntermediary: SendChannel<ByteArray>,
    ): MessageSharing {
        return extension.load(
            teleportJoinIntermediary,
            notificationSharingIntermediary,
            pbeVersionIntermediary
        )
    }

    fun extensionInitialized() = ::extension.isInitialized
}