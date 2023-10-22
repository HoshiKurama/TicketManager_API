package com.github.hoshikurama.ticketmanager.api.java.registry.config;

import com.github.hoshikurama.ticketmanager.api.registry.RegistrationResult;
import org.jetbrains.annotations.NotNull;

public interface ConfigRegistryJava {
    @NotNull RegistrationResult register(@NotNull Class<? extends ConfigExtensionJava> clazz);

    @NotNull RegistrationResult register(@NotNull ConfigExtensionJava extension);
}
