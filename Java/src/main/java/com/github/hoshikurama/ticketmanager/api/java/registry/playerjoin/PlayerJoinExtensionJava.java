package com.github.hoshikurama.ticketmanager.api.java.registry.playerjoin;

import com.github.hoshikurama.ticketmanager.api.CommandSender;
import com.github.hoshikurama.ticketmanager.api.PlatformFunctions;
import com.github.hoshikurama.ticketmanager.api.registry.config.Config;
import com.github.hoshikurama.ticketmanager.api.registry.locale.Locale;
import com.github.hoshikurama.ticketmanager.api.registry.permission.Permission;
import org.jetbrains.annotations.NotNull;

public interface PlayerJoinExtensionJava {
    void whenPlayerJoins(
            @NotNull CommandSender.OnlinePlayer player,
            @NotNull PlatformFunctions platformFunctions,
            @NotNull Permission permission,
            @NotNull Config config,
            @NotNull Locale locale
    ) throws @NotNull Exception;
}
