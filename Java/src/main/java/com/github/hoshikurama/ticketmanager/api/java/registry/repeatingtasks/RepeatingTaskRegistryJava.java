package com.github.hoshikurama.ticketmanager.api.java.registry.repeatingtasks;

import com.github.hoshikurama.ticketmanager.api.registry.RegistrationResult;
import org.jetbrains.annotations.NotNull;

public interface RepeatingTaskRegistryJava {
    @NotNull RegistrationResult register(@NotNull Class<? extends RepeatingTaskExtensionJava> clazz);
    @NotNull RegistrationResult register(@NotNull RepeatingTaskExtensionJava extension);
}
