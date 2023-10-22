package com.github.hoshikurama.ticketmanager.api.java.registry.precommand;

import com.github.hoshikurama.ticketmanager.api.CommandSender;
import com.github.hoshikurama.ticketmanager.api.registry.locale.Locale;
import com.github.hoshikurama.ticketmanager.api.registry.permission.Permission;
import com.github.hoshikurama.ticketmanager.api.registry.precommand.PreCommandExtension.SyncDecider.Decision;
import org.jetbrains.annotations.NotNull;

public sealed interface PreCommandExtensionJava {

    non-sealed interface SyncDecider extends PreCommandExtensionJava {
        @NotNull Decision beforeCommand(
                @NotNull CommandSender.Active sender,
                @NotNull Permission permission,
                @NotNull Locale locale
        );
    }

    non-sealed interface SyncAfter extends PreCommandExtensionJava {
        void afterCommand(
                @NotNull CommandSender.Active sender,
                @NotNull Permission permission,
                @NotNull Locale locale
        );
    }

    non-sealed interface AsyncAfter extends PreCommandExtensionJava {
        void afterCommand(
                @NotNull CommandSender.Active sender,
                @NotNull Permission permission,
                @NotNull Locale locale
        );
    }
}
