package com.github.hoshikurama.ticketmanager.api

import com.github.hoshikurama.ticketmanager.api.ticket.ActionLocation
import net.kyori.adventure.audience.Audience
import net.kyori.adventure.text.Component
import java.util.*

interface PlatformFunctions {
    fun massNotify(permission: String, message: Component)
    fun buildPlayer(uuid: UUID): CommandSender.OnlinePlayer?
    fun getAllOnlinePlayers(): List<CommandSender.OnlinePlayer>
    fun offlinePlayerNameToUUIDOrNull(name: String): UUID?
    fun nameFromUUIDOrNull(uuid: UUID): String?
    fun teleportToTicketLocSameServer(player: CommandSender.OnlinePlayer, loc: ActionLocation.FromPlayer)
    fun teleportToTicketLocDiffServer(player: CommandSender.OnlinePlayer, loc: ActionLocation.FromPlayer)

    // Console Messages
    fun getConsoleAudience(): Audience
    fun pushInfoToConsole(message: String)
    fun pushWarningToConsole(message: String)
    fun pushErrorToConsole(message: String)

    // Tab Complete Functions:
    fun getOnlineSeenPlayerNames(sender: CommandSender.Active): List<String>
    fun getWorldNames(): List<String>
}