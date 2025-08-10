package com.github.hoshikurama.ticketmanager.api.registry.messagesharing

import kotlinx.coroutines.channels.SendChannel

/**
 * [MessageSharingExtension] contains the logic necessary to load a [MessageSharing] instance. It should also contain
 * the data necessary for forwarding incoming data to the correct intermediary channel exposed by the mailbox.
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
fun interface MessageSharingExtension {

    /**
     * Loads the [MessageSharingExtension]. The arguments are the intermediary channels TM:SE exposes for the Mailbox system.
     * This data should be passed through as-is to the correct location. Simple data processing may occur for determining
     * if TM:SE should actually run this data. This is the expected place for launching the forwarding service as a result.
     * This code will not run on the main server thread.
     */
    suspend fun load(
        teleportJoinIntermediary: SendChannel<ByteArray>,
        notificationSharingIntermediary: SendChannel<ByteArray>,
        pbeVersionIntermediary: SendChannel<ByteArray>,
    ) : MessageSharing
}