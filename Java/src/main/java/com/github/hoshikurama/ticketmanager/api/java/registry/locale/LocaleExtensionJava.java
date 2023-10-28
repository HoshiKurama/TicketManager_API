package com.github.hoshikurama.ticketmanager.api.java.registry.locale;

import com.github.hoshikurama.ticketmanager.api.registry.config.Config;
import com.github.hoshikurama.ticketmanager.api.registry.locale.Locale;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;
import java.util.concurrent.CompletableFuture;

/**
 * Represents a builder which provides a Locale instance to TicketManager.
 * <p>
 * While this extension is registered only once, the #load() function will be called on server startup and any time
 * the plugin is reloaded.
 */
@FunctionalInterface
public interface LocaleExtensionJava {
    /**
     * Constructs a Locale instance.
     * @param tmDirectory TicketManager directory
     * @param config Active config
     */
    @NotNull CompletableFuture<@NotNull Locale> load(@NotNull Path tmDirectory, @NotNull Config config) throws @NotNull Exception;
}