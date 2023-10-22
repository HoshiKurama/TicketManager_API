package com.github.hoshikurama.ticketmanager.api.java.impl.registry;

import com.github.hoshikurama.ticketmanager.api.impl.TicketManager;
import com.github.hoshikurama.ticketmanager.api.java.DatabaseExtensionAdapter;
import com.github.hoshikurama.ticketmanager.api.java.registry.database.DatabaseExtensionJava;
import com.github.hoshikurama.ticketmanager.api.java.registry.database.DatabaseRegistryJava;
import com.github.hoshikurama.ticketmanager.api.registry.RegistrationResult;
import com.github.hoshikurama.ticketmanager.api.registry.RegistrationResult.Rejected;
import com.github.hoshikurama.ticketmanager.api.registry.RegistrationResult.Rejected.Reason;
import org.jetbrains.annotations.NotNull;

public class TMDatabaseRegistryJava implements DatabaseRegistryJava {

    @Override
    public @NotNull RegistrationResult register(@NotNull Class<? extends DatabaseExtensionJava> clazz) {
        final var realRegistry = TicketManager.INSTANCE.getDatabaseRegistry();

        if (realRegistry.extensionInitialized())
            return new Rejected(Reason.HAS_REGISTRATION.INSTANCE);

        try {
            final var extensionJava = clazz.getDeclaredConstructor().newInstance();
            final var extensionKotlin = new DatabaseExtensionAdapter(extensionJava);
            return realRegistry.register(extensionKotlin);
        } catch (NoSuchMethodException e) {
            return new Rejected(Reason.INVALID_CONSTRUCTOR.INSTANCE);
        } catch (Exception e) {
            return new Rejected(new Reason.EXCEPTION_OCCURRED(e));
        }
    }

    @Override
    public @NotNull RegistrationResult register(@NotNull DatabaseExtensionJava extension) {
        final var extensionKotlin = new DatabaseExtensionAdapter(extension);
        return TicketManager.INSTANCE.getDatabaseRegistry().register(extensionKotlin);
    }
}
