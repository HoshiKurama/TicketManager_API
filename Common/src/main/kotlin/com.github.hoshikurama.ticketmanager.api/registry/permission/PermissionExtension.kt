package com.github.hoshikurama.ticketmanager.api.registry.permission

fun interface PermissionExtension {
    suspend fun load(): Permission
}