package com.github.hoshikurama.ticketmanager.api.java.registry.precommand;

import com.github.hoshikurama.ticketmanager.api.registry.RegistrationResult;
import org.jetbrains.annotations.NotNull;

public interface PreCommandRegistryJava {
    @NotNull RegistrationResult register(@NotNull Class<? extends PreCommandExtensionJava> clazz);
    @NotNull RegistrationResult register(@NotNull PreCommandExtensionJava extension);
}