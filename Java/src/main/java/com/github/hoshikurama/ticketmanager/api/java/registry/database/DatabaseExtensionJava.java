package com.github.hoshikurama.ticketmanager.api.java.registry.database;

import com.github.hoshikurama.ticketmanager.api.registry.config.Config;
import com.github.hoshikurama.ticketmanager.api.registry.locale.Locale;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;
import java.util.concurrent.CompletableFuture;

/**
 * Represents a builder which provides an AsyncDatabase instance to TicketManager.
 * <p>
 * While this extension is registered only once, the #load() function will be called on server startup and any time
 * the plugin is reloaded.
 */
@FunctionalInterface
public interface DatabaseExtensionJava {
    /**
     * Constructs an AsyncDatabase instance.
     * @param tmDirectory TicketManager directory
     * @param config active Config instance
     * @param locale active Locale instance
     */
    @NotNull CompletableFuture<@NotNull AsyncDatabaseJava> load(
            @NotNull Path tmDirectory,
            @NotNull Config config,
            @NotNull Locale locale
    ) throws @NotNull Exception;
}
