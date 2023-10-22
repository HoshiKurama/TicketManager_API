package com.github.hoshikurama.ticketmanager.api.java.registry.locale;

import com.github.hoshikurama.ticketmanager.api.registry.RegistrationResult;
import org.jetbrains.annotations.NotNull;

public interface LocaleRegistryJava {
    @NotNull RegistrationResult register(@NotNull Class<? extends LocaleExtensionJava> clazz);

    @NotNull RegistrationResult register(@NotNull LocaleExtensionJava extension);
}