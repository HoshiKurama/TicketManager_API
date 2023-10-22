package com.github.hoshikurama.ticketmanager.api.java.impl.registry;

import com.github.hoshikurama.ticketmanager.api.impl.TicketManager;
import com.github.hoshikurama.ticketmanager.api.java.LocaleExtensionAdapter;
import com.github.hoshikurama.ticketmanager.api.java.registry.locale.LocaleExtensionJava;
import com.github.hoshikurama.ticketmanager.api.java.registry.locale.LocaleRegistryJava;
import com.github.hoshikurama.ticketmanager.api.registry.RegistrationResult;
import com.github.hoshikurama.ticketmanager.api.registry.RegistrationResult.Rejected;
import com.github.hoshikurama.ticketmanager.api.registry.RegistrationResult.Rejected.Reason;
import org.jetbrains.annotations.NotNull;

public class TMLocaleRegistryJava implements LocaleRegistryJava {
    @Override
    public @NotNull RegistrationResult register(@NotNull Class<? extends LocaleExtensionJava> clazz) {
        final var realRegistry = TicketManager.INSTANCE.getLocaleRegistry();

        if (realRegistry.extensionInitialized())
            return new Rejected(Reason.HAS_REGISTRATION.INSTANCE);

        try {
            final var extensionJava = clazz.getDeclaredConstructor().newInstance();
            final var extensionKotlin = new LocaleExtensionAdapter(extensionJava);
            return realRegistry.register(extensionKotlin);
        } catch (NoSuchMethodException e) {
            return new Rejected(Reason.INVALID_CONSTRUCTOR.INSTANCE);
        } catch (Exception e) {
            return new Rejected(new Reason.EXCEPTION_OCCURRED(e));
        }
    }

    @Override
    public @NotNull RegistrationResult register(@NotNull LocaleExtensionJava extension) {
        final var extensionKotlin = new LocaleExtensionAdapter(extension);
        return TicketManager.INSTANCE.getLocaleRegistry().register(extensionKotlin);
    }
}
