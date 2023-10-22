package com.github.hoshikurama.ticketmanager.api.java.impl.registry;

import com.github.hoshikurama.ticketmanager.api.impl.TicketManager;
import com.github.hoshikurama.ticketmanager.api.java.PreCommandExtensionAdapter;
import com.github.hoshikurama.ticketmanager.api.java.registry.precommand.PreCommandExtensionJava;
import com.github.hoshikurama.ticketmanager.api.java.registry.precommand.PreCommandRegistryJava;
import com.github.hoshikurama.ticketmanager.api.registry.RegistrationResult;
import com.github.hoshikurama.ticketmanager.api.registry.RegistrationResult.Rejected;
import com.github.hoshikurama.ticketmanager.api.registry.RegistrationResult.Rejected.Reason;
import com.github.hoshikurama.ticketmanager.api.registry.precommand.PreCommandExtension;
import org.jetbrains.annotations.NotNull;

public class TMPreCommandRegistryJava implements PreCommandRegistryJava {
    @Override
    public @NotNull RegistrationResult register(@NotNull Class<? extends PreCommandExtensionJava> clazz) {
        final var realRegistry = TicketManager.INSTANCE.getPreCommandRegistry();

        try {
            final PreCommandExtension extensionKotlin;
            final var extensionJava = clazz.getDeclaredConstructor().newInstance();

            if (extensionJava instanceof PreCommandExtensionJava.SyncDecider)
                extensionKotlin = new PreCommandExtensionAdapter.SyncDecider((PreCommandExtensionJava.SyncDecider) extensionJava);
            else if (extensionJava instanceof PreCommandExtensionJava.SyncAfter)
                extensionKotlin = new PreCommandExtensionAdapter.SyncAfter((PreCommandExtensionJava.SyncAfter) extensionJava);
            else extensionKotlin = new PreCommandExtensionAdapter.AsyncAfter((PreCommandExtensionJava.AsyncAfter) extensionJava);

            return realRegistry.register(extensionKotlin);
        } catch (NoSuchMethodException e) {
            return new Rejected(Reason.INVALID_CONSTRUCTOR.INSTANCE);
        } catch (Exception e) {
            return new Rejected(new Reason.EXCEPTION_OCCURRED(e));
        }
    }

    @Override
    public @NotNull RegistrationResult register(@NotNull PreCommandExtensionJava extension) {
        final PreCommandExtension extensionKotlin;

        if (extension instanceof PreCommandExtensionJava.SyncDecider)
            extensionKotlin = new PreCommandExtensionAdapter.SyncDecider((PreCommandExtensionJava.SyncDecider) extension);
        else if (extension instanceof PreCommandExtensionJava.SyncAfter)
            extensionKotlin = new PreCommandExtensionAdapter.SyncAfter((PreCommandExtensionJava.SyncAfter) extension);
        else extensionKotlin = new PreCommandExtensionAdapter.AsyncAfter((PreCommandExtensionJava.AsyncAfter) extension);

        return TicketManager.INSTANCE.getPreCommandRegistry().register(extensionKotlin);
    }
}