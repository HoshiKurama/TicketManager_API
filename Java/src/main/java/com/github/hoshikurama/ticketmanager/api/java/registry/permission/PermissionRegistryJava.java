package com.github.hoshikurama.ticketmanager.api.java.registry.permission;

import com.github.hoshikurama.ticketmanager.api.registry.RegistrationResult;
import org.jetbrains.annotations.NotNull;

public interface PermissionRegistryJava {
    @NotNull RegistrationResult register(@NotNull Class<? extends PermissionExtensionJava> clazz);
    @NotNull RegistrationResult register(@NotNull PermissionExtensionJava extension);
}
