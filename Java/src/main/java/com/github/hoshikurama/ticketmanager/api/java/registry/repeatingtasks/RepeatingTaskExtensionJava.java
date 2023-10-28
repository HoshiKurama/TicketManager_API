package com.github.hoshikurama.ticketmanager.api.java.registry.repeatingtasks;

import com.github.hoshikurama.ticketmanager.api.PlatformFunctions;
import com.github.hoshikurama.ticketmanager.api.registry.config.Config;
import com.github.hoshikurama.ticketmanager.api.registry.locale.Locale;
import com.github.hoshikurama.ticketmanager.api.registry.permission.Permission;
import org.jetbrains.annotations.NotNull;

/**
 * Represents an extension which is designed to execute some chunk of command at regular intervals.
 */
public interface RepeatingTaskExtensionJava {
    @NotNull Double getFrequencySeconds();

    void onRepeat(
            @NotNull Config config,
            @NotNull Locale locale,
            @NotNull Permission permission,
            @NotNull PlatformFunctions platformFunctions
    );
}