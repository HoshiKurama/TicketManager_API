package com.github.hoshikurama.ticketmanager.api.java.registry.precommand;

import com.github.hoshikurama.ticketmanager.api.CommandSender;
import com.github.hoshikurama.ticketmanager.api.registry.locale.Locale;
import com.github.hoshikurama.ticketmanager.api.registry.permission.Permission;
import com.github.hoshikurama.ticketmanager.api.registry.precommand.PreCommandExtension.SyncDecider.Decision;
import org.jetbrains.annotations.NotNull;
/**
 * Represents an Extension which is run before a TicketManager command begins execution. The three types are outlined
 * below.
 * <p>
 * Note: all types run on the internal off-thread coroutine dispatcher pool.
 */
public sealed interface PreCommandExtensionJava {

    /**
     * SyncDeciders run first. They are executed in order of registration time and have the unique ability to block
     * the command from running. All SyncDeciders must agree to run the command, and the first to block execution will
     * short-circuit.
     */
    @FunctionalInterface
    non-sealed interface SyncDecider extends PreCommandExtensionJava {
        @NotNull Decision beforeCommand(
                @NotNull CommandSender.Active sender,
                @NotNull Permission permission,
                @NotNull Locale locale
        );
    }

    /**
     * SyncAfters are launched in order of registration, but each entry must complete before the next is started. Note
     * that this type executes alongside command execution.
     */
    @FunctionalInterface
    non-sealed interface SyncAfter extends PreCommandExtensionJava {
        void afterCommand(
                @NotNull CommandSender.Active sender,
                @NotNull Permission permission,
                @NotNull Locale locale
        );
    }

    /**
     * AsyncAfters Entries are launched in order of registration to run concurrently. Note that this type executes
     * alongside command execution.
     */
    @FunctionalInterface
    non-sealed interface AsyncAfter extends PreCommandExtensionJava {
        void afterCommand(
                @NotNull CommandSender.Active sender,
                @NotNull Permission permission,
                @NotNull Locale locale
        );
    }
}
