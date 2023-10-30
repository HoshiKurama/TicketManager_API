package com.github.hoshikurama.ticketmanager.api.java.impl.registry;

import com.github.hoshikurama.ticketmanager.api.impl.TicketManager;
import com.github.hoshikurama.ticketmanager.api.impl.registry.TMPermissionRegistry;
import com.github.hoshikurama.ticketmanager.api.java.PermissionExtensionAdapter;
import com.github.hoshikurama.ticketmanager.api.java.registry.permission.PermissionExtensionJava;
import com.github.hoshikurama.ticketmanager.api.java.registry.permission.PermissionRegistryJava;
import com.github.hoshikurama.ticketmanager.api.registry.RegistrationResult;
import com.github.hoshikurama.ticketmanager.api.registry.RegistrationResult.Rejected;
import com.github.hoshikurama.ticketmanager.api.registry.RegistrationResult.Rejected.Reason;
import org.jetbrains.annotations.NotNull;

public class TMPermissionRegistryJava implements PermissionRegistryJava {
    @Override
    public @NotNull RegistrationResult register(@NotNull Class<? extends PermissionExtensionJava> clazz) {
        final var realRegistry = (TMPermissionRegistry) TicketManager.INSTANCE.getPermissionRegistry();

        if (realRegistry.extensionInitialized())
            return new Rejected(Reason.HAS_REGISTRATION.INSTANCE);

        try {
            final var extensionJava = clazz.getDeclaredConstructor().newInstance();
            final var extensionKotlin = new PermissionExtensionAdapter(extensionJava);
            return realRegistry.register(extensionKotlin);
        } catch (NoSuchMethodException e) {
            return new Rejected(Reason.INVALID_CONSTRUCTOR.INSTANCE);
        } catch (Exception e) {
            return new Rejected(new Reason.EXCEPTION_OCCURRED(e));
        }
    }

    @Override
    public @NotNull RegistrationResult register(@NotNull PermissionExtensionJava extension) {
        final var extensionKotlin = new PermissionExtensionAdapter(extension);
        return TicketManager.INSTANCE.getPermissionRegistry().register(extensionKotlin);
    }
}
