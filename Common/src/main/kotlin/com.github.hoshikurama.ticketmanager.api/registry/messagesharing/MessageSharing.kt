package com.github.hoshikurama.ticketmanager.api.registry.messagesharing

/**
 * Defines how TicketManager internally communicates between servers, like via a proxy or direct connection.
 *
 * Rationale: TicketManager uses an internal system referred to as Mailboxes for asynchronous message passing. These
 * mailboxes leverage Kotlin coroutine channels for a variety of benefits. However, these mailboxes are defined inside
 * TM:SE due to data parsing that relies on TM:SE exclusive data. Mailboxes therefore expose an intermediary
 * ReceiveChannel<ByteArray> for MessageSharing extensions to pass data into TM:SE without needing to understand the contents
 * of the data (aside from a UUID appended at the beginning).
 *
 * The [MessageSharing] type is one half of defining this functionality. This type tells the internal mailboxes how the
 * data should be passed directly from the server to the hub acting to pass data between all servers. It also defines how
 * the extension should be unloaded.
 *
 * The [MessageSharingExtension] is the other half of defining this functionality. This type contains the load function,
 * which itself contains the intermediary [kotlinx.coroutines.channels.SendChannel]s to pass received data into for TM:SE
 * processing and actions.
 */
interface MessageSharing {

    /**
     * Defines how the ByteArray [data] should be sent to the "hub". [channelName] allows the hub to understand what data type
     * is being transmitted so it knows how to forward it, and it will be in the form "ticketmanager:datatype" similar to
     * inbound and outbound channels on the Velocity proxy.
     *
     * This function will not in a coroutine not on the main thread, so the function is free to block the thread if need be.
     */
    @Suppress("Unused")
    fun relay2Hub(data: ByteArray, channelName: String)

    /**
     * Defines how TM:SE should unload the services set up during [MessageSharingExtension.load]. For example, proxy
     * implementations (like Velocity) will simply unregister associated plugin message listeners. If [trueShutdown] is
     * false, it means TM:SE was reloaded using /tm reload. If [trueShutdown] is true, the server is shutting down. As a
     * reminder, [MessageSharingExtension] is only registered once, but [MessageSharingExtension.load] will be called during
     * startups or reloads, so design the system appropriately.
     */
    @Suppress("Unused")
    suspend fun unload(trueShutdown: Boolean)
}