package com.github.hoshikurama.ticketmanager.api.java.registry.locale;

import com.github.hoshikurama.ticketmanager.api.registry.config.Config;
import com.github.hoshikurama.ticketmanager.api.registry.locale.Locale;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;
import java.util.concurrent.CompletableFuture;

public interface LocaleExtensionJava {
    @NotNull CompletableFuture<@NotNull Locale> load(@NotNull Path tmDirectory, @NotNull Config config) throws @NotNull Exception;
}