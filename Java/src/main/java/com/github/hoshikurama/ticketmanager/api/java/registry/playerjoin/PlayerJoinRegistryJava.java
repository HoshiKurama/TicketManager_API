package com.github.hoshikurama.ticketmanager.api.java.registry.playerjoin;

import com.github.hoshikurama.ticketmanager.api.registry.RegistrationResult;
import com.github.hoshikurama.ticketmanager.api.registry.playerjoin.PlayerJoinRegistry;
import org.jetbrains.annotations.NotNull;

public interface PlayerJoinRegistryJava {
    @NotNull RegistrationResult register(
            @NotNull Class<? extends PlayerJoinExtensionJava> clazz,
            @NotNull PlayerJoinRegistry.RunType runType
    );

    @NotNull RegistrationResult register(
            @NotNull PlayerJoinExtensionJava extension,
            @NotNull PlayerJoinRegistry.RunType runType
    );
}