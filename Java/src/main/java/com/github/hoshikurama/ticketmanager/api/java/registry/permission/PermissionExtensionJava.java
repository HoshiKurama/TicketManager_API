package com.github.hoshikurama.ticketmanager.api.java.registry.permission;

import com.github.hoshikurama.ticketmanager.api.registry.permission.Permission;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

/**
 * Represents a builder which provides a Permission instance to TicketManager.
 * <p>
 * While this extension is registered only once, the #load() function will be called on server startup and any time
 * the plugin is reloaded.
 */
@FunctionalInterface
public interface PermissionExtensionJava {
    /**
     * Constructs the Permission instance.
     */
    @NotNull CompletableFuture<@NotNull Permission> load() throws @NotNull Exception;
}
