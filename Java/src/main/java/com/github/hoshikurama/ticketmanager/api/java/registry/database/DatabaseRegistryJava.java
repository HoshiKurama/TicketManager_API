package com.github.hoshikurama.ticketmanager.api.java.registry.database;

import com.github.hoshikurama.ticketmanager.api.registry.RegistrationResult;
import org.jetbrains.annotations.NotNull;

public interface DatabaseRegistryJava {
    @NotNull RegistrationResult register(@NotNull Class<? extends DatabaseExtensionJava> clazz);

    @NotNull RegistrationResult register(@NotNull DatabaseExtensionJava extension);
}
