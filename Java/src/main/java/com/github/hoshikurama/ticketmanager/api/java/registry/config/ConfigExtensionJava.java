package com.github.hoshikurama.ticketmanager.api.java.registry.config;

import com.github.hoshikurama.ticketmanager.api.registry.config.Config;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;
import java.util.concurrent.CompletableFuture;

/**
 * Represents a builder which provides a Config instance to TicketManager. #load() does not assume that files exists.
 * <p>
 * While this extension is registered only once, the #load() function will be called on server startup and any time
 * the plugin is reloaded.
 */
@FunctionalInterface
public interface ConfigExtensionJava {
    /**
     * constructs a Config instance.
     * @param tmDirectory TicketManager directory
     */
    @NotNull CompletableFuture<@NotNull Config> load(@NotNull Path tmDirectory) throws @NotNull Exception;
}
