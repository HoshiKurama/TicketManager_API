package com.github.hoshikurama.ticketmanager.api.java.registry.permission;

import com.github.hoshikurama.ticketmanager.api.registry.permission.Permission;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

@FunctionalInterface
public interface PermissionExtensionJava {
    @NotNull CompletableFuture<@NotNull Permission> load() throws @NotNull Exception;
}
