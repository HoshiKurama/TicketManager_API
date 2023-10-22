package com.github.hoshikurama.ticketmanager.api.java.registry.database;

import com.github.hoshikurama.ticketmanager.api.registry.config.Config;
import com.github.hoshikurama.ticketmanager.api.registry.locale.Locale;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;
import java.util.concurrent.CompletableFuture;

public interface DatabaseExtensionJava {
    @NotNull CompletableFuture<@NotNull AsyncDatabaseJava> load(
            @NotNull Path tmDirectory,
            @NotNull Config config,
            @NotNull Locale locale
    ) throws @NotNull Exception;
}
