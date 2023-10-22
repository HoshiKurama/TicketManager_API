package com.github.hoshikurama.ticketmanager.api.registry.permission

import com.github.hoshikurama.ticketmanager.api.CommandSender

interface Permission {
    fun allGroupNames(): List<String>
    fun groupNamesOf(player: CommandSender.OnlinePlayer): List<String>
    fun has(player: CommandSender.OnlinePlayer, permission: String): Boolean
}