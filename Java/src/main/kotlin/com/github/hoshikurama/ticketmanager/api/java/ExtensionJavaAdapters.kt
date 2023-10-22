package com.github.hoshikurama.ticketmanager.api.java

import com.github.hoshikurama.ticketmanager.api.CommandSender
import com.github.hoshikurama.ticketmanager.api.PlatformFunctions
import com.github.hoshikurama.ticketmanager.api.java.kotlinAdapters.registry.AsyncDatabaseAdapter
import com.github.hoshikurama.ticketmanager.api.java.registry.config.ConfigExtensionJava
import com.github.hoshikurama.ticketmanager.api.java.registry.database.DatabaseExtensionJava
import com.github.hoshikurama.ticketmanager.api.java.registry.locale.LocaleExtensionJava
import com.github.hoshikurama.ticketmanager.api.java.registry.permission.PermissionExtensionJava
import com.github.hoshikurama.ticketmanager.api.java.registry.playerjoin.PlayerJoinExtensionJava
import com.github.hoshikurama.ticketmanager.api.java.registry.precommand.PreCommandExtensionJava
import com.github.hoshikurama.ticketmanager.api.java.registry.repeatingtasks.RepeatingTaskExtensionJava
import com.github.hoshikurama.ticketmanager.api.registry.config.Config
import com.github.hoshikurama.ticketmanager.api.registry.config.ConfigExtension
import com.github.hoshikurama.ticketmanager.api.registry.database.AsyncDatabase
import com.github.hoshikurama.ticketmanager.api.registry.database.DatabaseExtension
import com.github.hoshikurama.ticketmanager.api.registry.locale.Locale
import com.github.hoshikurama.ticketmanager.api.registry.locale.LocaleExtension
import com.github.hoshikurama.ticketmanager.api.registry.permission.Permission
import com.github.hoshikurama.ticketmanager.api.registry.permission.PermissionExtension
import com.github.hoshikurama.ticketmanager.api.registry.playerjoin.PlayerJoinExtension
import com.github.hoshikurama.ticketmanager.api.registry.precommand.PreCommandExtension
import com.github.hoshikurama.ticketmanager.api.registry.repeatingtasks.RepeatingTaskExtension
import kotlinx.coroutines.future.asDeferred
import java.nio.file.Path
import kotlin.time.Duration.Companion.seconds

class ConfigExtensionAdapter(private val javaVersion: ConfigExtensionJava) : ConfigExtension {
    override suspend fun load(tmDirectory: Path): Config {
        return javaVersion.load(tmDirectory).asDeferred().await()
    }
}

class DatabaseExtensionAdapter(private val javaVersion: DatabaseExtensionJava) : DatabaseExtension {
    override suspend fun load(tmDirectory: Path, config: Config, locale: Locale): AsyncDatabase {
        return javaVersion.load(tmDirectory, config, locale)
            .asDeferred().await().run(::AsyncDatabaseAdapter)
    }
}

class LocaleExtensionAdapter(private val javaVersion: LocaleExtensionJava) : LocaleExtension {
    override suspend fun load(tmDirectory: Path, config: Config): Locale {
        return javaVersion.load(tmDirectory, config).asDeferred().await()
    }
}

class PermissionExtensionAdapter(private val javaVersion: PermissionExtensionJava) : PermissionExtension {
    override suspend fun load(): Permission {
        return javaVersion.load().asDeferred().await()
    }
}

// NOTE: NO DATABASE ACCESS
class PlayerJoinExtensionAdapter(private val javaVersion: PlayerJoinExtensionJava) : PlayerJoinExtension {
    override suspend fun whenPlayerJoins(
        player: CommandSender.OnlinePlayer,
        platformFunctions: PlatformFunctions,
        permission: Permission,
        database: AsyncDatabase,
        config: Config,
        locale: Locale
    ) {
        javaVersion.whenPlayerJoins(player, platformFunctions, permission, config, locale)
    }
}

// NOTE: NO DATABASE ACCESS
class RepeatingTaskExtensionAdapter(private val javaVersion: RepeatingTaskExtensionJava,
) : RepeatingTaskExtension {
    override val frequency = javaVersion.frequencySeconds.seconds

    override suspend fun onRepeat(
        config: Config,
        locale: Locale,
        database: AsyncDatabase,
        permission: Permission,
        platformFunctions: PlatformFunctions
    ) {
        javaVersion.onRepeat(config, locale, permission, platformFunctions)
    }
}

object PreCommandExtensionAdapter {
    class SyncDecider(private val javaVersion: PreCommandExtensionJava.SyncDecider) : PreCommandExtension.SyncDecider {
        override suspend fun beforeCommand(
            sender: CommandSender.Active,
            permission: Permission,
            locale: Locale
        ): PreCommandExtension.SyncDecider.Decision {
            return javaVersion.beforeCommand(sender, permission, locale)
        }
    }

    class SyncAfter(private val javaVersion: PreCommandExtensionJava.SyncAfter) : PreCommandExtension.SyncAfter {
        override suspend fun afterCommand(sender: CommandSender.Active, permission: Permission, locale: Locale) {
            javaVersion.afterCommand(sender, permission, locale)
        }
    }

    class AsyncAfter(private val javaVersion: PreCommandExtensionJava.AsyncAfter) : PreCommandExtension.AsyncAfter {
        override suspend fun afterCommand(sender: CommandSender.Active, permission: Permission, locale: Locale) {
            javaVersion.afterCommand(sender, permission, locale)
        }
    }
}