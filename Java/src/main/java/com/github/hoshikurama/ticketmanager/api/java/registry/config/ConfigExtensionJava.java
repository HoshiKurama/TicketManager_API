package com.github.hoshikurama.ticketmanager.api.java.registry.config;

import com.github.hoshikurama.ticketmanager.api.registry.config.Config;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;
import java.util.concurrent.CompletableFuture;

public interface ConfigExtensionJava {
    @NotNull CompletableFuture<@NotNull Config> load(@NotNull Path tmDirectory) throws @NotNull Exception;
}
